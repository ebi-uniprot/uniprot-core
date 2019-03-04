package uk.ac.ebi.uniprot.flatfile.parser.impl;

import org.slf4j.Logger;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created 28/02/19
 *
 * @author Edd
 */
public class EntryIteratorMain {
    private static final Logger LOGGER = getLogger(EntryIteratorMain.class);

    public static void main(String[] args) throws FileNotFoundException {
        LOGGER.info("received arguments length: "+ args.length);
        LOGGER.info("received arguments: "+ Arrays.toString(args));
        if (args.length != 9) {
            LOGGER.error("Provide the correct arguments: FILE THREAD_NUM ENTRY_QUEUE_SIZE FF_QUEUE_SIZE COUNT KEYWORD_FILE DISEASE_FILE GO_FILE SUBCELLULAR_LOCATION_FILE");
            System.exit(1);
        }
        String file = args[0];
        int numThreads = Integer.parseInt(args[1]);
        int entryQueueSize = Integer.parseInt(args[2]);
        int ffQueueSize = Integer.parseInt(args[3]);
        int maxCount = Integer.parseInt(args[4]);
        String keywordFile = args[5];
        String diseaseFile = args[6];
        String goFile = args[7];
        String subcellularLocationFile = args[8];

        LOGGER.info("==========");
        DefaultUniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(numThreads, entryQueueSize, ffQueueSize);
        entryIterator.setIgnoreWrong(true);
        entryIterator.setInput(file, keywordFile, diseaseFile, goFile, subcellularLocationFile);
        long count = 0;
        long start = System.currentTimeMillis();
        while ((maxCount < 1 || count < maxCount) && entryIterator.hasNext()) {
            UniProtEntry entry = entryIterator.next();
            if (count % 5000 == 0) {
                long duration = System.currentTimeMillis() - start;
                double entriesPerSec = (double) (count * 1000) / duration;
                long totalMemory = Runtime.getRuntime().totalMemory();
                long freeMemory = Runtime.getRuntime().freeMemory();
                double mbUsed = (double) (totalMemory - freeMemory) / (1000 * 1000);

                LOGGER.info("==========");
                LOGGER.info("Entries read = {}", count);
                LOGGER.info("Rate (entries/sec) = {}", entriesPerSec);
                LOGGER.info("Used memory (mb) = {}", mbUsed);
            }
            count++;
        }

        LOGGER.info("==========");
        LOGGER.info("Finished iterating over file, {}", file);
        LOGGER.info("Entries read = {}", count);
        long duration = System.currentTimeMillis() - start;
        double durationInSecs = (double) duration / 1000;
        double entriesPerSec = (double) count / durationInSecs;
        LOGGER.info("Duration (ms) = {}", duration);
        LOGGER.info("Rate (entries/sec) = {}", entriesPerSec);
        System.exit(0);
    }
}
