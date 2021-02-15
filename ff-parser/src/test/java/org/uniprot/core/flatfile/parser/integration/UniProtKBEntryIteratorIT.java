package org.uniprot.core.flatfile.parser.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtEntryIterator;
import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

@Disabled
@Slf4j
class UniProtKBEntryIteratorIT {
    private DefaultUniProtEntryIterator entryIterator;

    @BeforeEach
    void setUp() {
        entryIterator = new DefaultUniProtEntryIterator(2, 5, 10);
        entryIterator.setIgnoreWrong(true);
    }

    @Test
    void testSingleTxl() {
        try {
            String filename = "src/test/resources/entryIT/A9N0W4.txl";
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            assertNotNull(entry);
            assertFalse(entryIterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleTxlRoundTrip() {
        try {
            String filename = "src/test/resources/entryIT/A9N0W4.txl";
            String entryStr = readFile(filename);
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            FlatfileWriter<UniProtKBEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr = writer.write(entry, false);
            assertEquals(entryStr, convertedEntryStr + "\n");
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleDat() {
        try {
            String filename = "src/test/resources/entryIT/Q32K04.dat";
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            assertNotNull(entry);
            assertFalse(entryIterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleDatRoundTrip() {
        try {
            String filename = "src/test/resources/entryIT/Q32K04.dat";
            String entryStr = readFile(filename);
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            FlatfileWriter<UniProtKBEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr = writer.write(entry, true);
            log.debug(convertedEntryStr);
            assertEquals(entryStr, convertedEntryStr + "\n");
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Disabled
    @Test
    void testMultiDat() {
        try {
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat";
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();

            assertNotNull(entry);
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry2 = entryIterator.next();
            assertNotNull(entry2);
            assertFalse(entryIterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Disabled
    void testMultiDatRoundTrip() {
        try {
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat.gz";
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            assertNotNull(entry);
            String filename1 = "src/test/resources/entryIT/A8EZU1.dat";
            String entryStr1 = readFile(filename1);
            FlatfileWriter<UniProtKBEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr1 = writer.write(entry, true);
            assertEquals(entryStr1, convertedEntryStr1 + "\n");

            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry2 = entryIterator.next();
            assertNotNull(entry2);
            String filename2 = "src/test/resources/entryIT/D6RDV7.dat";
            String entryStr2 = readFile(filename2);
            String convertedEntryStr2 = writer.write(entry2, true);
            assertEquals(entryStr2, convertedEntryStr2 + "\n");
            assertFalse(entryIterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testMultiDatGz() {
        try {
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat.gz";
            entryIterator.setInput(filename, "", "", "", "");
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry = entryIterator.next();
            assertNotNull(entry);
            assertTrue(entryIterator.hasNext());
            UniProtKBEntry entry2 = entryIterator.next();
            assertNotNull(entry2);
            assertFalse(entryIterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void parseErrorForAllEntriesDoesNotCauseHanging() {
        entryIterator.setInput(
                "src/test/resources/entryIT/ERROR_ERROR.dat", null, null, null, null);
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry =
                    entryIterator
                            .next(); // the entry should be null because there was a parse error
            assertThat(entry, is(nullValue()));
        }
    }

    @Test
    void parseErrorInOnlyOneEntryDoesNotCauseHanging() {
        entryIterator.setInput(
                "src/test/resources/entryIT/A8EZU1_ERROR_D6RDV7.dat", null, null, null, null);
        List<String> accessions = new ArrayList<>();
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry = entryIterator.next();
            accessions.add(entry.getPrimaryAccession().getValue());
        }

        assertThat(accessions, containsInAnyOrder("A8EZU1", "D6RDV7"));
    }

    private String readFile(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
