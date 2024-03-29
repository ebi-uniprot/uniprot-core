package org.uniprot.core.flatfile.parser.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniProtEntryIterator;
import org.uniprot.core.flatfile.parser.UniProtParserHelper;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;

class UniProtParserIT {
    @Test
    void testParse() {
        String filename = "/entryIT/Q32K04.dat";
        String entryStr = readEntryFromFile(filename);
        UniProtKBEntry entry = UniProtParserHelper.parse(entryStr);
        assertNotNull(entry);
        assertEquals("Q32K04", entry.getPrimaryAccession().getValue());
    }

    @Test
    void testParseQ6P2Q9() {
        String filename = "/entryIT/Q6P2Q9.dat";
        String entryStr = readEntryFromFile(filename);
        UniProtKBEntry entry = UniProtParserHelper.parse(entryStr);
        assertNotNull(entry);
        System.out.println(UniProtFlatfileWriter.write(entry));
        assertEquals("Q6P2Q9", entry.getPrimaryAccession().getValue());
    }

    @Test
    void testParseWithIgnore() {
        String filename = "/entryIT/A0A176EY13.txl";
        String entryStr = readEntryFromFile(filename);
        UniProtKBEntry entry = UniProtParserHelper.parse(entryStr);
        assertNotNull(entry);
        assertEquals("A0A176EY13", entry.getPrimaryAccession().getValue());
    }

    @Test
    void testParseWithCatalyticActivity() {
        String filename = "/entryIT/P0DTC1.dat";
        String entryStr = readEntryFromFile(filename);
        UniProtKBEntry entry = UniProtParserHelper.parse(entryStr);
        assertNotNull(entry);
        assertEquals("P0DTC1", entry.getPrimaryAccession().getValue());
    }

    @Test
    void testParseFile() {
        String filename = "src/test/resources/entryIT/A8EZU1_D6RDV7.dat";
        UniProtEntryIterator iterator = UniProtParserHelper.parseFile(filename, "", "", "", "");
        assertTrue(iterator.hasNext());
        UniProtKBEntry entry = iterator.next();
        assertNotNull(entry);
        Set<String> accs = new TreeSet<>(Arrays.asList(new String[] {"A8EZU1", "D6RDV7"}));
        Set<String> expected = new TreeSet<>();
        expected.add(entry.getPrimaryAccession().getValue());

        assertTrue(iterator.hasNext());
        entry = iterator.next();
        assertNotNull(entry);
        expected.add(entry.getPrimaryAccession().getValue());
        assertEquals(accs, expected);
        assertFalse(iterator.hasNext());
    }

    private String readEntryFromFile(String filename) {
        URL url = getClass().getResource(filename);
        CharSource charSource = Resources.asCharSource(url, Charset.defaultCharset());
        try {
            return charSource.read();
        } catch (IOException e) {
            System.out.println("io exceptions.");
            return "";
        }
    }
}
