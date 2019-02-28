package uk.ac.ebi.uniprot.flatfile.parser.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FlatfileWriter;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EntryBufferedReader;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObjectConverterFactory;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

public class FlatfileRoundTripMain {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlatfileRoundTripMain.class);
    private boolean isPublic = false;
    private UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
    private EntryObjectConverterFactory.EntryObjectConverter entryObjectConverter = new EntryObjectConverterFactory().createEntryObjectConverter("", "", "", "", true);
    private FlatfileWriter<UniProtEntry> ffWriter = new UniProtFlatfileWriter();

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            LOGGER.error("Please set arguments.");
            System.exit(1);
        }
        FlatfileRoundTripMain test = new FlatfileRoundTripMain();
        test.roundtrip(args[0]);
    }

    private void roundtrip(String filename) throws IOException {
        try (EntryBufferedReader reader = new EntryBufferedReader(filename)) {
            String entry = null;
            int failedCount = 0;
            int totalCount = 0;
            while ((entry = reader.next()) != null) {
                if (entry.length() > 0) {
                    if (!testEntry(entry)) {
                        failedCount++;
                    }
                    totalCount++;

                }
                if (totalCount % 5000 == 0) {
                    LOGGER.info("parsed entries:" + totalCount + " failed: " + failedCount);
                }
            }
            LOGGER.info("total parsed entries:" + totalCount + " failed: " + failedCount);
        }
    }

    private boolean testEntry(String entryStr) {
        EntryObject parse = entryParser.parse(entryStr);
        UniProtEntry converted = entryObjectConverter.convert(parse);
        assertNotNull(converted);
        String convertedEntryStr = ffWriter.write(converted, isPublic);
        EntryObject parse2 = null;

        try {
            parse2 = entryParser.parse(convertedEntryStr);
        } catch (Exception e) {
            LOGGER.error(entryStr, e);
            LOGGER.error(convertedEntryStr);
            throw e;
        }

        UniProtEntry converted2 = entryObjectConverter.convert(parse2);
        boolean b = converted2.equals(converted);
        if (!b) {
            LOGGER.info(entryStr);
            LOGGER.info(converted.getPrimaryAccession().getValue());
        }
        return b;
    }
}
