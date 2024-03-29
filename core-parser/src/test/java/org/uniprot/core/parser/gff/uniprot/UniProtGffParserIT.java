package org.uniprot.core.parser.gff.uniprot;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

/**
 * Created 05/02/19
 *
 * @author Edd
 */
class UniProtGffParserIT {
    private static final Logger LOGGER = getLogger(UniProtGffParserIT.class);
    private static final String TEST_FILE_PATH = "/uniprotkb/entry/";

    @Test
    void validateOutputForQ8NDV7() {
        String entryPath = TEST_FILE_PATH + "Q8NDV7.dat";
        String gffPath = TEST_FILE_PATH + "Q8NDV7.gff";
        UniProtKBEntry entry = readEntry(entryPath);
        String entryGff = UniProtGffParser.convert(entry);

        String gffAsString =
                readEntryFromFile(gffPath)
                        .orElseThrow(
                                () -> new IllegalStateException("Could not read file: " + gffPath));
        LOGGER.info(entryGff);
        verify(entryGff, gffAsString);
    }

    @Test
    void validateOutputForP21802() {
        String entryPath = TEST_FILE_PATH + "P21802.dat";
        String gffPath = TEST_FILE_PATH + "P21802.gff";
        UniProtKBEntry entry = readEntry(entryPath);
        String entryGff = UniProtGffParser.convert(entry);

        String gffAsString =
                readEntryFromFile(gffPath)
                        .orElseThrow(
                                () -> new IllegalStateException("Could not read file: " + gffPath));
        LOGGER.info(entryGff);
        verify(entryGff, gffAsString);
    }

    @Test
    void validateOutputForQ8IWB6() {
        String entryPath = TEST_FILE_PATH + "Q8IWB6.dat";
        String gffPath = TEST_FILE_PATH + "Q8IWB6.gff";
        UniProtKBEntry entry = readEntry(entryPath);
        String entryGff = UniProtGffParser.convert(entry);

        String gffAsString =
                readEntryFromFile(gffPath)
                        .orElseThrow(
                                () -> new IllegalStateException("Could not read file: " + gffPath));
        LOGGER.info(entryGff);
        verify(entryGff, gffAsString);
    }

    private void verify(String entryGff, String gffAsString) {
        String[] givenGff = entryGff.split("\n");
        String[] expectedGff = gffAsString.split("\n");
        for (int i = 0; i < Math.min(givenGff.length, expectedGff.length); i++) {
            if (!givenGff[i].strip().equals(expectedGff[i].strip())) {
                LOGGER.warn("");
                LOGGER.warn("Given:    " + givenGff[i]);
                LOGGER.warn("Expected: " + expectedGff[i]);
                fail();
            }
        }
    }

    private UniProtKBEntry readEntry(String path) {
        UniprotKBLineParser<EntryObject> entryParser =
                new DefaultUniprotKBLineParserFactory().createEntryParser();
        String entryAsString =
                readEntryFromFile(path)
                        .orElseThrow(
                                () -> new IllegalStateException("Could not read file: " + path));
        EntryObject parse = entryParser.parse(entryAsString);
        assertNotNull(parse);
        EntryObjectConverter entryObjectConverter =
                new EntryObjectConverter(new SupportingDataMapImpl(), true);
        return entryObjectConverter.convert(parse);
    }

    private Optional<String> readEntryFromFile(String fileName) {
        URL resource = getClass().getResource(fileName);
        Stream<String> lines = null;
        try {
            Path path = Paths.get(resource.toURI());
            lines = Files.lines(path);
            return Optional.of(lines.collect(Collectors.joining("\n")));
        } catch (URISyntaxException | IOException ex) {
            LOGGER.error("Could not read file", ex);
        } finally {
            if (lines != null) {
                lines.close();
            }
        }
        return Optional.empty();
    }
}
