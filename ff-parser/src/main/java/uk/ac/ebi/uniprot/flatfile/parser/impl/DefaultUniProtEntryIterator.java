package uk.ac.ebi.uniprot.flatfile.parser.impl;

import com.google.common.util.concurrent.Uninterruptibles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.flatfile.parser.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
public class DefaultUniProtEntryIterator implements UniProtEntryIterator {

    private static Logger logger = LoggerFactory.getLogger(DefaultUniProtEntryIterator.class);
    private static Logger loggerError = LoggerFactory
            .getLogger(DefaultUniProtEntryIterator.class.getName() + ".error");

    private final int numberOfThreads;
    private final List<ParsingTask> workers = new ArrayList<>();
    private final BlockingQueue<UniProtEntry> entriesQueue;
    private final BlockingQueue<String> ffQueue;

    private CountDownLatch parsingJobCountDownLatch;
    private SupportingDataMap supportingDataMap;

    // count of the entries that has been produced and consumed.
    private AtomicLong entryCounter = new AtomicLong();

    private boolean ignoreWrong = false;

    public DefaultUniProtEntryIterator() {
        this(1, 5000, 50000);
    }

    public DefaultUniProtEntryIterator(int numberOfThreads, int entryQueuesize, int ffQueueSize) {
        this.numberOfThreads = numberOfThreads;
        entriesQueue = new ArrayBlockingQueue<>(entryQueuesize);
        ffQueue = new ArrayBlockingQueue<>(ffQueueSize);
    }

    public void setIgnoreWrong(boolean ignoreWrong) {
        this.ignoreWrong = ignoreWrong;
    }

    @Override
    public void setInput(String fileName, String keywordFile, String diseaseFile, String accessionGoPubmedFile, String subcellularLocationFile) {
        logger.info("Started loading SupportingDataMap");
        supportingDataMap = new SupportingDataMapImpl(keywordFile, diseaseFile, accessionGoPubmedFile, subcellularLocationFile);
        logger.info("finished loading SupportingDataMap");
        try {
            setInput2(fileName);
        } catch (FileNotFoundException e) {
            throw new UniProtParserException(e);
        }
    }

    public boolean hasNext() {
        if (this.entryCounter.get() > 0)
            return true;
        else {
            // if there are parsing jobs still running, wait and check again.
            logger.trace("Checking hasNext: the entry queue is emptied.");
            while (this.parsingJobCountDownLatch.getCount() > 0) {
                logger.trace("Checking hasNext: the parsing jobs have not finished, wait a bit.");
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                if (this.entryCounter.get() > 0)
                    return true;
            }

            logger.trace("Checking hasNext: all parsing jobs have finished.");

            // if there re no job running, check last.
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
            Thread.currentThread().interrupt();
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

    private EntryReader createEntryReader(String fileName) throws FileNotFoundException {
        if (fileName.endsWith(".gz")) {
            try {
                return new EntryBufferedReader(fileName);
            } catch (IOException e) {
                return new EntryBufferedReader2(fileName);
            }
        } else {
            return new EntryBufferedReader2(fileName);
        }
    }

    private void setInput2(String fileName) throws FileNotFoundException {
        EntryReader entryBufferReader2 = createEntryReader(fileName);

        Thread splitter = new Thread(new EntryStringEmitter(entryBufferReader2));
        splitter.setName("Entry Scanner Thread");

        // using the best effort to scan the file.
        splitter.setPriority(Thread.MAX_PRIORITY);

        splitter.start();

        int threadCount = Runtime.getRuntime().availableProcessors();
        logger.debug("Available cores in the machine {}", threadCount);

        if (this.numberOfThreads != 0) {
            threadCount = this.numberOfThreads;
        }

        logger.info("Using threads {}", threadCount);

        this.parsingJobCountDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            ParsingTask parsingTask = new ParsingTask(ffQueue, entriesQueue, this.parsingJobCountDownLatch);
            parsingTask.setName("Parsing Worker No. " + (i + 1));
            this.workers.add(parsingTask);
            parsingTask.start();
        }

        // wait until the entryCounter > 1, so hasNext won't return false.
        while (entryCounter.get() == 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public class EntryStringEmitter implements Runnable {
        private final EntryReader entryReader;

        EntryStringEmitter(EntryReader entryReader) {
            this.entryReader = entryReader;
        }

        @Override
        public void run() {
            long counter = 0;
            long startTime = System.nanoTime();
            long checkPoint = startTime;
            try {
                String next = entryReader.next();
                while (next != null) {
                    boolean offer = ffQueue.offer(next);
                    if (offer) {
                        counter++;
                        entryCounter.getAndIncrement();
                        next = entryReader.next();
                    } else { // the queue is full.
                        Thread.sleep(1);
                        logger.trace("Target queue is FULL, wait a bit");
                    }

                    long l = System.nanoTime();
                    // report every 10 minitues
                    if (TimeUnit.NANOSECONDS.toMinutes(l - checkPoint) > 5) {
                        logger.debug(
                                "The total number of flat file entry has been scanned : {}. Using time:  {} minutes",
                                counter, TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - startTime));
                        checkPoint = l;
                    }
                }
            } catch (Exception e) {
                loggerError.error("Exception in splitting FF", e);
            }

            logger.debug("Flat-file scanning finished.");
            logger.debug("Total flat-file to be parsed: {} ", counter);
            logger.debug("Total time used: {} ", TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - startTime));

            while (!ffQueue.isEmpty()) {
                Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
                logger.debug("Waiting the FF queue to be emptied.");
            }

            logger.debug("FF queue cleaned, full flat-file has be parsed.");


            for (ParsingTask task : workers) {
                task.finish();
            }

            // waiting all parsing job to finish.
            try {
                parsingJobCountDownLatch.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                Thread.currentThread().interrupt();
            }

            logger.debug("The flat-file scanning thread is now finished.");
        }
    }

    public class ParsingTask extends Thread {
        private final BlockingQueue<String> ffQueue;
        private final BlockingQueue<UniProtEntry> queue;
        private final CountDownLatch countDown;
        private final AtomicBoolean notFinished = new AtomicBoolean(false);
        private UniProtParser parser;

        ParsingTask(BlockingQueue<String> ffQueue, BlockingQueue<UniProtEntry> queue, CountDownLatch countDown) {
            this.ffQueue = ffQueue;
            this.queue = queue;
            this.countDown = countDown;
            this.parser = new DefaultUniProtParser(supportingDataMap, ignoreWrong);
        }

        void finish() {
            notFinished.compareAndSet(false, true);
            logger.debug("The parsing task {} is signaled to finish.", this.getName());
        }

        @Override
        public void run() {
            long startTime = System.nanoTime();
            long checkPoint = startTime;
            long counter = 0;
            long failed = 0;

            while (!notFinished.get()) {
                String poll = ffQueue.poll();

                if (poll != null) {
                    try {
                        UniProtEntry convert = parser.parse(poll);
                        // using put to block current thread if wait is necessary.
                        queue.put(convert);
                        counter++;

                        long l = System.nanoTime();

                        // report every 10 minitues
                        if (TimeUnit.NANOSECONDS.toMinutes(l - checkPoint) > 5) {
                            logger.debug("Number of FF has been parsed by this worker : {}. Using time:  {} minutes",
                                         counter, TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - startTime));
                            checkPoint = l;
                        }
                    } catch (Exception e) {
                        entryCounter.getAndDecrement();
                        failed++;
                        loggerError.error("Error while parsing FF", e);
                        loggerError.trace("The FF canot be parsed: \n{}", poll);
                    }
                } else {
                    logger.trace("FF String queue is empty, wait a bit.");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            logger.debug("Total FF parsed {} by this worker, Using time:  {} minutes", counter,
                         TimeUnit.NANOSECONDS.toMinutes(System.nanoTime() - startTime));

            if (failed > 0) {
                logger.warn("Failed FF parsing in the worker: {}", failed);
            }

            countDown.countDown();
        }
    }
}
