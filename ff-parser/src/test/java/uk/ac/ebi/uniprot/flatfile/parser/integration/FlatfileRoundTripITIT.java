package uk.ac.ebi.uniprot.flatfile.parser.integration;

import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FlatfileWriter;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.UniProtFlatfileWriter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObjectConverter;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FlatfileRoundTripITIT {
    @Test
    public void testA0A0A0MSM0() {
        String filename = "/entryIT/A0A0A0MSM0.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    @Test
    public void testP87498() {
        String filename = "/entryIT/P87498.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    
    
    @Test
    public void testD6RDV7() {
        String filename = "/entryIT/D6RDV7.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    @Test
    public void testQ15758() {
        String filename = "/entryIT/Q15758.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    @Test
    public void testQ15758Txl() {
        String filename = "/entryIT/Q15758.txl";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, false);
    }

    @Test
    public void testQ3SYC2() {
        String filename = "/entryIT/Q3SYC2.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    @Test
    public void testQ3SYC2Txl() {
        String filename = "/entryIT/Q3SYC2.txl";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, false);
    }

    @Test
    public void testQ63HN8() {
        String filename = "/entryIT/Q63HN8.dat";
        String entryStr = readEntryFromFile(filename);

        testEntry(entryStr, true);
    }

    @Test
    public void testQ9NYP9() {
        String filename = "/entryIT/Q9NYP9.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP14592() {
        String filename = "/entryIT/P14592.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP50930() {
        String filename = "/entryIT/P50930.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ9S9U6() {
        String filename = "/entryIT/Q9S9U6.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP84716() {
        String filename = "/entryIT/P84716.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testA0A176EY13Txl() {

        String filename = "/entryIT/A0A176EY13.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA0A1Q9NR16Txl() {

        String filename = "/entryIT/A0A1Q9NR16.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA0A1Z5JZG6Txl() {

        String filename = "/entryIT/A0A1Z5JZG6.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA0A2D8TK40Txl() {

        String filename = "/entryIT/A0A2D8TK40.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA0A2E5TRM0Txl() {

        String filename = "/entryIT/A0A2E5TRM0.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA9N0W4Txl() {

        String filename = "/entryIT/A9N0W4.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testB3E7G1Txl() {

        String filename = "/entryIT/B3E7G1.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testH0ZNI7Txl() {

        String filename = "/entryIT/H0ZNI7.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testP55423Txl() {

        String filename = "/entryIT/P55423.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ1MS15Txl() {

        String filename = "/entryIT/Q1MS15.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ66GB9Txl() {

        String filename = "/entryIT/Q66GB9.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }


    @Test
    public void testS7U3X4Txl() {

        String filename = "/entryIT/S7U3X4.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testA0A0E2CV74Dat() {

        String filename = "/entryIT/A0A0E2CV74.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testA0A196N885Dat() {

        String filename = "/entryIT/A0A196N885.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testA8EZU1Dat() {

        String filename = "/entryIT/A8EZU1.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ32K04Dat() {

        String filename = "/entryIT/Q32K04.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }


    @Test
    public void testQ8DPW5Dat() {

        String filename = "/entryIT/Q8DPW5.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testA4K2U9Dat() {

        String filename = "/entryIT/A4K2U9.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP15711Dat() {

        String filename = "/entryIT/P15711.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ13362Dat() {

        String filename = "/entryIT/Q13362.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP04403Dat() {
        String filename = "/entryIT/P04403.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP0DMV6Dat() {
        String filename = "/entryIT/P0DMV6.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testA8AP56Dat() {
        String filename = "/entryIT/A8AP56.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }


    @Test
    public void testQ80WC7Dat() {
        String filename = "/entryIT/Q80WC7.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testO05204Dat() {
        String filename = "/entryIT/O05204.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ54R84Dat() {
        String filename = "/entryIT/Q54R84.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP35626Dat() {
        String filename = "/entryIT/P35626.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ9T4R0Dat() {
        String filename = "/entryIT/Q9T4R0.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ04664Dat() {
        String filename = "/entryIT/Q04664.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ9G0S9Dat() {
        String filename = "/entryIT/Q9G0S9.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ96SD1Dat() {
        String filename = "/entryIT/Q96SD1.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP03395Dat() {
        String filename = "/entryIT/P03395.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP55301Dat() {
        String filename = "/entryIT/P55301.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testQ64524Dat() {
        String filename = "/entryIT/Q64524.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, true);
    }

    @Test
    public void testP13813Dat() {
        String filename = "/entryIT/P13813.dat";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ9CQV8Dat() {
        String filename = "/entryIT/Q9CQV8.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testP09230Dat() {
        String filename = "/entryIT/P09230.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testP0CH48Dat() {
        String filename = "/entryIT/P0CH48.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testP09919Dat() {
        String filename = "/entryIT/P09919.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Disabled("dash linewrap issue")
    @Test
    public void testP58928Dat() {
        String filename = "/entryIT/P58928.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ9FK72Dat() {
        String filename = "/entryIT/Q9FK72.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ00731Dat() {
        String filename = "/entryIT/Q00731.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testP54757Dat() {
        String filename = "/entryIT/P54757.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ93NG3Dat() {
        String filename = "/entryIT/Q93NG3.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Disabled
    @Test
    public void testQ9SKK4Dat() {
        String filename = "/entryIT/Q9SKK4.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Disabled("dash linewrap issue")
    @Test
    public void testO93383Dat() {
        String filename = "/entryIT/O93383.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ9U299DatBPCP() {
        String filename = "/entryIT/Q9U299.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    @Test
    public void testQ401N2DatCarbohydFeatureId() {
        String filename = "/entryIT/Q401N2.txl";
        String entryStr = readEntryFromFile(filename);
        // System.out.println(entryStr);
        testEntry(entryStr, false);
    }

    private void testEntry(String entryToParse, boolean ispublic) {
        UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
        EntryObject parse = entryParser.parse(entryToParse);
        assertNotNull(parse);

        EntryObjectConverter entryObjectConverter = new EntryObjectConverter("", "", "", "",  true);
        UniProtEntry converted = entryObjectConverter.convert(parse);
        FlatfileWriter<UniProtEntry> writer = new UniProtFlatfileWriter();

        assertNotNull(converted);
        String convertedEntryStr = writer.write(converted, ispublic);
       // 	System.out.println(convertedEntryStr);

        String diff = compareFF(entryToParse, convertedEntryStr + "\n");
        System.out.println(diff);

        EntryObject parse2 = entryParser.parse(convertedEntryStr);
        UniProtEntry converted2 = entryObjectConverter.convert(parse2);

        List<UniProtDBCrossReference> originalDBXrefs = converted.getDatabaseCrossReferences();
        List<UniProtDBCrossReference> convertedDBXrefs = converted2.getDatabaseCrossReferences();
        assertEquals(originalDBXrefs.size(), convertedDBXrefs.size());
        for (int i = 0; i < originalDBXrefs.size(); i++) {
            UniProtDBCrossReference origXref = originalDBXrefs.get(i);
            UniProtDBCrossReference convertedXref = convertedDBXrefs.get(i);
            assertEquals("DBXref at index " + i + " differs", origXref, convertedXref);
        }

        assertEquals(converted, converted2);


//		assertTrue(diff.isEmpty());
//		assertEquals(entryToParse, convertedEntryStr +"\n");

    }


    private String compareFF(String expected, String returned) {
        String[] expected2 = expected.split("\n");
        String[] returned2 = returned.split("\n");
        if (expected2.length != returned2.length) {
            System.out.println("number of line is different: " + expected2.length + "\t" + returned2.length);
        }
        int j = 0;
        String value = "";
        Set<String> drReturned = new TreeSet<>();
        Set<String> drExpected = new TreeSet<>();
        int returnLength = returned2.length;
        for (int i = 0; i < expected2.length; i++) {
            if (expected2[i].startsWith("DR   ")) {
                drReturned.add(returned2[j]);
                drExpected.add(expected2[i]);

            } else if (j >= returnLength) {
                System.err.println(expected2[i]);
            } else if (!expected2[i].equals(returned2[j])) {
                boolean found = false;
                int found1 = find(returned2, expected2[i], j);
                if (found1 != -1) {
                    found = true;
                    j = found1;
                }
                if (!found) {
                    value += "Expected:" + expected2[i] + "\n";
                    value += "Parsed==:" + returned2[j] + "\n";

                    System.err.println("Expected:" + expected2[i]);
                    System.err.println("Parsed==:" + returned2[j]);
                }

                if (j >= returned2.length)
                    break;

            }
            j++;
        }

        drReturned.removeAll(drExpected);
        if (drReturned.size() != 0) {
            System.err.println(drReturned.toString());
        }
        return value;

    }

    private int find(String[] returned2, String value, int j) {
        int start = j - 6;
        if (start < 0)
            start = 0;
        int end = j + 6;
        if (end >= returned2.length) {
            end = returned2.length - 1;
        }
        for (int i = 0; i < returned2.length; i++) {
            if (value.equals(returned2[i]))
                return i;
        }
        return -1;
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
