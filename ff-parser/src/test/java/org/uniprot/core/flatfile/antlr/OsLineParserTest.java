package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.os.OsLineObject;

class OsLineParserTest {
    @Test
    void test() {
        String osLines = "OS   Solanum melongena (Eggplant) (Aubergine).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Solanum melongena (Eggplant) (Aubergine)", obj.organism_species);
    }

    @Test
    void testTwoLine() {
        String osLines =
                "OS   Rous (strain Schmidt-Ruppin A) (Avian leukosis\n" + "OS   virus-RSA).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)", obj.organism_species);
    }

    @Test
    void testThreeLine() {
        String osLines =
                "OS   Rous (strain Schmidt-Ruppin A)\n"
                        + "OS   (Avian leukosis\n"
                        + "OS   virus-RSA).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)", obj.organism_species);
    }

    @Test
    void testWithSlash() {
        String osLines = "OS   African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV)",
                obj.organism_species);
    }

    @Test
    void testWithDot() {
        String osLines = "OS   Salmonella enterica subsp. enterica serovar Heidelberg str.\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Salmonella enterica subsp. enterica serovar Heidelberg str", obj.organism_species);
    }

    @Test
    void testWithDot2() {
        String osLines = "OS   Aeromonas sp. E6(2011).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Aeromonas sp. E6(2011)", obj.organism_species);
    }

    @Test
    void testVirus() {
        String osLines = "OS   Frog virus 3 (isolate Goorha) (FV-3).\n";
        UniprotLineParser<OsLineObject> parser =
                new DefaultUniprotLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Frog virus 3 (isolate Goorha) (FV-3)", obj.organism_species);
    }
}
