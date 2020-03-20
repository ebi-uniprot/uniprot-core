package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.os.OsLineObject;

class OsLineParserTest {
    @Test
    void test() {
        String osLines = "OS   Solanum melongena (Eggplant) (Aubergine).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Solanum melongena (Eggplant) (Aubergine)", obj.getOrganismSpecies());
    }

    @Test
    void testTwoLine() {
        String osLines =
                "OS   Rous (strain Schmidt-Ruppin A) (Avian leukosis\n" + "OS   virus-RSA).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)",
                obj.getOrganismSpecies());
    }

    @Test
    void testThreeLine() {
        String osLines =
                "OS   Rous (strain Schmidt-Ruppin A)\n"
                        + "OS   (Avian leukosis\n"
                        + "OS   virus-RSA).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Rous (strain Schmidt-Ruppin A) (Avian leukosis virus-RSA)",
                obj.getOrganismSpecies());
    }

    @Test
    void testWithSlash() {
        String osLines = "OS   African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "African swine fever virus (isolate Pig/Kenya/KEN-50/1950) (ASFV)",
                obj.getOrganismSpecies());
    }

    @Test
    void testWithDot() {
        String osLines = "OS   Salmonella enterica subsp. enterica serovar Heidelberg str.\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals(
                "Salmonella enterica subsp. enterica serovar Heidelberg str",
                obj.getOrganismSpecies());
    }

    @Test
    void testWithDot2() {
        String osLines = "OS   Aeromonas sp. E6(2011).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Aeromonas sp. E6(2011)", obj.getOrganismSpecies());
    }

    @Test
    void testVirus() {
        String osLines = "OS   Frog virus 3 (isolate Goorha) (FV-3).\n";
        UniprotKBLineParser<OsLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOsLineParser();
        OsLineObject obj = parser.parse(osLines);
        assertEquals("Frog virus 3 (isolate Goorha) (FV-3)", obj.getOrganismSpecies());
    }
}
