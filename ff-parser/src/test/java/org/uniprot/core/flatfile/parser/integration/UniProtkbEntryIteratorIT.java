package org.uniprot.core.flatfile.parser.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtEntryIterator;
import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtkbEntry;

class UniProtkbEntryIteratorIT {
    @Test
    void testSingleTxl() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/A9N0W4.txl";
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            assertNotNull(entry);
            assertFalse(iterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleTxlRoundTrip() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/A9N0W4.txl";
            String entryStr = readFile(filename);
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            FlatfileWriter<UniProtkbEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr = writer.write(entry, false);
            assertEquals(entryStr, convertedEntryStr + "\n");
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleDat() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/Q32K04.dat";
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            assertNotNull(entry);
            assertFalse(iterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testSingleDatRoundTrip() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/Q32K04.dat";
            String entryStr = readFile(filename);
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            FlatfileWriter<UniProtkbEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr = writer.write(entry, true);
            System.out.println(convertedEntryStr);
            assertEquals(entryStr, convertedEntryStr + "\n");
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testMultiDat() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat";
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();

            assertNotNull(entry);
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry2 = iterator.next();
            assertNotNull(entry2);
            assertFalse(iterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Disabled
    void testMultiDatRoundTrip() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat.gz";
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            assertNotNull(entry);
            String filename1 = "src/test/resources/entryIT/A8EZU1.dat";
            String entryStr1 = readFile(filename1);
            FlatfileWriter<UniProtkbEntry> writer = new UniProtFlatfileWriter();
            String convertedEntryStr1 = writer.write(entry, true);
            assertEquals(entryStr1, convertedEntryStr1 + "\n");

            assertTrue(iterator.hasNext());
            UniProtkbEntry entry2 = iterator.next();
            assertNotNull(entry2);
            String filename2 = "src/test/resources/entryIT/D6RDV7.dat";
            String entryStr2 = readFile(filename2);
            String convertedEntryStr2 = writer.write(entry2, true);
            assertEquals(entryStr2, convertedEntryStr2 + "\n");
            assertFalse(iterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    @Test
    void testMultiDatGz() {
        try {
            DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
            String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat.gz";
            iterator.setInput(filename, "", "", "", "");
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry = iterator.next();
            assertNotNull(entry);
            assertTrue(iterator.hasNext());
            UniProtkbEntry entry2 = iterator.next();
            assertNotNull(entry2);
            assertFalse(iterator.hasNext());
        } catch (Exception e) {
            fail("test failed");
        }
    }

    private String readFile(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}
