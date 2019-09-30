package org.uniprot.core.flatfile.parser.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.flatfile.writer.FlatfileWriter;
import org.uniprot.core.flatfile.writer.impl.UniProtFlatfileWriter;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;

class FlatfileRoundTripIT {
    private static final Logger LOGGER = getLogger(FlatfileRoundTripIT.class);

    @ParameterizedTest
    @CsvSource({
        "/entryIT/A0A0A0MSM0.dat, true",
        "/entryIT/D6RDV7.dat, true",
        "/entryIT/Q15758.dat, true",
        "/entryIT/Q15758.txl, false",
        "/entryIT/Q3SYC2.dat, true",
        "/entryIT/Q3SYC2.txl, false",
        "/entryIT/Q63HN8.dat, true",
        "/entryIT/Q9NYP9.dat, true",
        "/entryIT/P14592.dat, true",
        "/entryIT/P50930.dat, true",
        "/entryIT/Q9S9U6.dat, true",
        "/entryIT/P84716.dat, true",
        "/entryIT/A0A176EY13.txl, false",
        "/entryIT/A0A1Q9NR16.txl, false",
        "/entryIT/A0A1Z5JZG6.txl, false",
        "/entryIT/A0A2D8TK40.txl, false",
        "/entryIT/A0A2E5TRM0.txl, false",
        "/entryIT/A9N0W4.txl, false",
        "/entryIT/B3E7G1.txl, false",
        "/entryIT/H0ZNI7.txl, false",
        "/entryIT/P55423.txl, false",
        "/entryIT/Q1MS15.txl, false",
        "/entryIT/Q66GB9.txl, false",
        "/entryIT/S7U3X4.txl, false",
        "/entryIT/A0A0E2CV74.dat, true",
        "/entryIT/A0A196N885.dat, true",
        "/entryIT/A8EZU1.dat, true",
        "/entryIT/Q32K04.dat, true",
        "/entryIT/Q8DPW5.dat, true",
        "/entryIT/A4K2U9.dat, true",
        "/entryIT/P15711.dat, true",
        "/entryIT/Q13362.dat, true",
        "/entryIT/P04403.dat, true",
        "/entryIT/P0DMV6.dat, true",
        "/entryIT/A8AP56.dat, true",
        "/entryIT/Q80WC7.dat, true",
        "/entryIT/O05204.dat, true",
        "/entryIT/Q54R84.dat, true",
        "/entryIT/P35626.dat, true",
        "/entryIT/Q9T4R0.dat, true",
        "/entryIT/Q04664.dat, true",
        "/entryIT/Q9G0S9.dat, true",
        "/entryIT/Q96SD1.dat, true",
        "/entryIT/P03395.dat, true",
        "/entryIT/P55301.dat, true",
        "/entryIT/Q64524.dat, true",
        "/entryIT/P13813.dat, false",
        "/entryIT/Q9CQV8.txl, false",
        "/entryIT/P09230.txl, false",
        "/entryIT/P0CH48.txl, false",
        "/entryIT/P09919.txl, false",
        //                "/entryIT/P58928.txl, false", // line dash issue
        "/entryIT/Q9FK72.txl, false",
        "/entryIT/Q00731.txl, false",
        "/entryIT/P54757.txl, false",
        "/entryIT/Q93NG3.txl, false",
        //                "/entryIT/Q9SKK4.txl, false", // disabled
        //                "/entryIT/O93383.txl, false", // line dash issue
        "/entryIT/Q9U299.txl, false",
        "/entryIT/Q401N2.txl, false",
        "/entryIT/P87498.dat, true"
    })
    void roundTripTest(String fileName, boolean isPublic) {
        testFile(fileName, isPublic);
    }

    private void testFile(String file, boolean isPublic) {
        String entryStr = readEntryFromFile(file);
        testEntry(entryStr, isPublic);
    }

    private void testEntry(String entryToParse, boolean isPublic) {
        UniprotLineParser<EntryObject> entryParser =
                new DefaultUniprotLineParserFactory().createEntryParser();
        EntryObject parse = entryParser.parse(entryToParse);
        assertNotNull(parse);

        EntryObjectConverter entryObjectConverter =
                new EntryObjectConverter(new SupportingDataMapImpl(), true);
        UniProtEntry converted = entryObjectConverter.convert(parse);
        FlatfileWriter<UniProtEntry> writer = new UniProtFlatfileWriter();

        assertNotNull(converted);
        String convertedEntryStr = writer.write(converted, isPublic);

        String diff = compareFF(entryToParse, convertedEntryStr + "\n");
        LOGGER.info(diff);

        EntryObject parse2 = entryParser.parse(convertedEntryStr);
        UniProtEntry converted2 = entryObjectConverter.convert(parse2);

        List<UniProtDBCrossReference> originalDBXrefs = converted.getDatabaseCrossReferences();
        List<UniProtDBCrossReference> convertedDBXrefs = converted2.getDatabaseCrossReferences();
        assertEquals(originalDBXrefs.size(), convertedDBXrefs.size());
        for (int i = 0; i < originalDBXrefs.size(); i++) {
            UniProtDBCrossReference origXref = originalDBXrefs.get(i);
            UniProtDBCrossReference convertedXref = convertedDBXrefs.get(i);
            assertEquals(origXref, convertedXref, "DBXref at index " + i + " differs");
        }

        assertEquals(converted, converted2);
    }

    private String compareFF(String expected, String returned) {
        String[] expected2 = expected.split("\n");
        String[] returned2 = returned.split("\n");
        if (expected2.length != returned2.length) {
            LOGGER.info(
                    "number of line is different: " + expected2.length + "\t" + returned2.length);
        }
        int j = 0;
        String value = "";
        Set<String> drReturned = new TreeSet<>();
        Set<String> drExpected = new TreeSet<>();
        int returnLength = returned2.length;
        for (String s : expected2) {
            if (s.startsWith("DR   ")) {
                drReturned.add(returned2[j]);
                drExpected.add(s);

            } else if (j >= returnLength) {
                LOGGER.error(s);
            } else if (!s.equals(returned2[j])) {
                boolean found = false;
                int found1 = find(returned2, s, j);
                if (found1 != -1) {
                    found = true;
                    j = found1;
                }
                if (!found) {
                    value += "Expected:" + s + "\n";
                    value += "Parsed==:" + returned2[j] + "\n";

                    LOGGER.error("Expected:" + s);
                    LOGGER.error("Parsed==:" + returned2[j]);
                }

                if (j >= returned2.length) break;
            }
            j++;
        }

        drReturned.removeAll(drExpected);
        if (drReturned.size() != 0) {
            LOGGER.error(drReturned.toString());
        }
        return value;
    }

    private int find(String[] returned2, String value, int j) {
        for (int i = 0; i < returned2.length; i++) {
            if (value.equals(returned2[i])) return i;
        }
        return -1;
    }

    private String readEntryFromFile(String filename) {
        URL url = getClass().getResource(filename);
        CharSource charSource = Resources.asCharSource(url, Charset.defaultCharset());
        try {
            return charSource.read();
        } catch (IOException e) {
            LOGGER.error("IO exception.", e);
            return "";
        }
    }
}
