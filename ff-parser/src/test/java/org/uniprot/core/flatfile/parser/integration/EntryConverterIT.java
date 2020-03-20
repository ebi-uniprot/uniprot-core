package org.uniprot.core.flatfile.parser.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;

class EntryConverterIT {
    @Test
    void testA0A0A0MSM0() {
        String filename = "/entryIT/A0A0A0MSM0.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testD6RDV7() {
        String filename = "/entryIT/D6RDV7.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testQ15758() {
        String filename = "/entryIT/Q15758.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testQ3SYC2() {
        String filename = "/entryIT/Q3SYC2.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testQ63HN8() {
        String filename = "/entryIT/Q63HN8.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testQ9NYP9() {
        String filename = "/entryIT/Q9NYP9.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr);
    }

    @Test
    void testP05067() {
        String filename = "/entryIT/P05067.dat";
        String entryStr = readEntryFromFile(filename);
        System.out.println(entryStr);
        testEntry(entryStr);
    }

    private void testEntry(String entryToParse) {
        UniprotKBLineParser<EntryObject> entryParser =
                new DefaultUniprotKBLineParserFactory().createEntryParser();
        EntryObject parse = entryParser.parse(entryToParse);
        assertNotNull(parse);

        EntryObjectConverter entryObjectConverter =
                new EntryObjectConverter(new SupportingDataMapImpl(), true);
        UniProtKBEntry convert = entryObjectConverter.convert(parse);
        assertNotNull(convert);
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
