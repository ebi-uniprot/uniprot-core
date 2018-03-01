package uk.ac.ebi.uniprot.parser;

import com.google.common.util.concurrent.Uninterruptibles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.EntryBufferReader2;
import uk.ac.ebi.uniprot.parser.impl.EntryBufferReader;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wudong on 15/04/2014.
 */
public class UniProtEntryIterator implements Iterator<UniProtEntry>{

    private static Logger logger = LoggerFactory.getLogger(UniProtEntryIterator.class);
    private static Logger logger_error = LoggerFactory.getLogger(UniProtEntryIterator.class.getName() + ".error");

    public final int NUMBER_OF_THREAD;

    final List<ParsingTask> workers = new ArrayList<ParsingTask>();
    private Thread spliter;

    final private BlockingQueue<UniProtEntry> entriesQueue;
    final private BlockingQueue<String> ffQueue;

    private CountDownLatch parsingJobCountDownLatch;

    public UniProtEntryIterator() {
        this(0, 1000, 50000);
    }

    public UniProtEntryIterator(int number_of_thread, int entryQueuesize, int ffQueueSize) {
        NUMBER_OF_THREAD = number_of_thread;
        entriesQueue = new ArrayBlockingQueue<>(entryQueuesize);
        ffQueue = new ArrayBlockingQueue<>(ffQueueSize);
    }

    //count of the entries that has been produced and consumed.
    private AtomicLong entryCounter = new AtomicLong();

    public class EntryStringEmitter implements Runnable {

        final private EntryReader entryReader;

        public EntryStringEmitter(EntryReader entryReader) {
            this.entryReader = entryReader;
        }

        @Override
        public void run() {
            long counter = 0;
            long start_time = System.nanoTime();
            long check_point = start_time;
            try {
                String next = entryReader.next();
                while (next != null) {
                    boolean offer = ffQueue.offer(next);
                    if (offer) {
                        counter++;
                        entryCounter.getAndIncrement();
                        next = entryReader.next();
                    } else { //the queue is full.
                        Thread.sleep(1);
                        logger.trace("Target queue is FULL, wait a bit");
                    }

                    long l = System.nanoTime();
                    //report every 10 minitues
                    if (TimeUnit.NANOSECONDS.toMinutes(l - check_point) > 5) {
                        logger.debug("The total number of flat file entry has been scanned : {}. Using time:  {} minutes",
                                counter, TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - start_time));
                        check_point = l;
                    }
                }
            } catch (Exception e) {
                logger_error.error("Exception in splitting FF", e);
            }

            logger.debug("FF scanning finished.");
            logger.debug("Total flat file to be parsed: {} ", counter);
            logger.debug("Total time used: {} ", TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - start_time));

            while (!ffQueue.isEmpty()) {
                Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
                logger.debug("Waiting the FF queue to be emptied.");
            }

            logger.debug("FF queue cleaned, all flat file has be parsed.");

            for (ParsingTask task : workers) {
                task.finish();
            }

            //waiting all parsing job to finish.
            try {
                parsingJobCountDownLatch.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
              
            }

            logger.debug("The FF scanning thread is now finished.");
        }
    }

    public class ParsingTask extends Thread {
        private final BlockingQueue<String> ffQueue;
        private final BlockingQueue<UniProtEntry> queue;
        private final CountDownLatch countDown;
        private final AtomicBoolean not_finished = new AtomicBoolean(false);
        private UniprotLineParser<EntryObject> parser;
        private EntryObjectConverter converter;

        public ParsingTask(BlockingQueue<String> ffQueue, BlockingQueue<UniProtEntry> queue, CountDownLatch countDown) {
            this.ffQueue = ffQueue;
            this.queue = queue;
            this.countDown = countDown;

            this.parser = new DefaultUniprotLineParserFactory().createEntryParser();
            this.converter = new EntryObjectConverter(false);
        }

        public void finish() {
            not_finished.compareAndSet(false, true);
            logger.debug("The parsing task {} is signaled to finish.", this.getName());
        }

        @Override
        public void run() {

            long start_time = System.nanoTime();
            long checkPoint = start_time;
            long counter = 0;
            long failed = 0;

            while (!not_finished.get()) {
                String poll = ffQueue.poll();

                if (poll != null) {
                    try {
                        EntryObject parse = parser.parse(poll);
                        UniProtEntry convert = converter.convert(parse);
                        //using put to block current thread if wait is necessary.
                        queue.put(convert);
                        counter++;

                        long l = System.nanoTime();

                        //report every 10 minitues
                        if (TimeUnit.NANOSECONDS.toMinutes(l - checkPoint) > 5) {
                            logger.debug("Number of FF has been parsed by this worker : {}. Using time:  {} minutes",
                                    counter, TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - start_time));
                            checkPoint = l;
                        }
                    } catch (Exception e) {
                        entryCounter.getAndDecrement();
                        failed++;
                        logger_error.error("Error while parsing FF", e);
                        logger_error.trace("The FF canot be parsed: \n{}", poll);
                    }
                } else {
                    logger.trace("FF String queue is empty, wait a bit.");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        //
                    }
                }
            }

            logger.debug("Total FF parsed {} by this worker, Using time:  {} minutes", counter,
                    TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - start_time));

            if (failed > 0) {
                logger.warn("Failed FF parsing in the worker: {}", failed);
            }

            countDown.countDown();
        }
    }

    private EntryReader createEntryReader(String fileName)throws FileNotFoundException {
        if(fileName.endsWith(".gz")){
            try{
            return new EntryBufferReader(fileName);
            }catch(IOException e ){
                return  new EntryBufferReader2(fileName);
            }
        }else{
           return  new EntryBufferReader2(fileName);
        }
    }
    public void setInput(String fileName) throws FileNotFoundException {
        EntryReader entryBufferReader2 = createEntryReader(fileName);

        this.spliter = new Thread(new EntryStringEmitter(entryBufferReader2));
        this.spliter.setName("Entry Scanner Thread");

        //using the best effort to scan the file.
        this.spliter.setPriority(Thread.MAX_PRIORITY);

        this.spliter.start();

        int thread_count = Runtime.getRuntime().availableProcessors();
        logger.debug("Available cores in the machine {}", thread_count);

        if (this.NUMBER_OF_THREAD != 0) {
            thread_count = this.NUMBER_OF_THREAD;
        }

        logger.info("Using threads {}", thread_count);

        this.parsingJobCountDownLatch = new CountDownLatch(thread_count);

        for (int i = 0; i < thread_count; i++) {
            ParsingTask parsingTask = new ParsingTask(ffQueue, entriesQueue, this.parsingJobCountDownLatch);
            parsingTask.setName("Parsing Worker No. " + (i + 1));
            this.workers.add(parsingTask);
            parsingTask.start();
        }

        //wait until the entryCounter > 1, so hasNext won't return false.
        while (entryCounter.get() == 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public boolean hasNext() {
        if (this.entryCounter.get() > 0) return true;
        else {
            //if their are paring job still running, wait and check again.
            logger.trace("Checking hasNext: the entry queue is emptied.");
            while (this.parsingJobCountDownLatch.getCount() > 0) {
                logger.trace("Checking hasNext: the parsing jobs have not finished, wait a bit.");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());

                }
                if (this.entryCounter.get() > 0) return true;
            }

            logger.trace("Checking hasNext: all parsing jobs have finished.");

            //if there re no job running, check last.
            return this.entryCounter.get() > 0;
        }
    }

    public UniProtEntry next() {
        try {
            UniProtEntry poll = this.entriesQueue.take();

            if (poll != null) {
                entryCounter.getAndDecrement();
            } else {
                logger.trace("Next: entry query is empty.");
            }

            return poll;
        } catch (InterruptedException e) {
            logger.debug("Get entry from queue is interrupted.");
            return null;
        }
    }

    /**
     * This exposes the EntryQueue directly.
     *
     * @return
     */
    public Queue<UniProtEntry> getEntryQueue() {
        return this.entriesQueue;
    }

	@Override
	public void remove() {
		logger.trace("remove is not implemented");
	}
}



