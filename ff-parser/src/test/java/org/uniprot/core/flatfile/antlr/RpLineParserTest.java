package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineObject;

class RpLineParserTest {
    @Test
    void test() {
        String rgLines = "RP   NUCLEOTIDE SEQUENCE [MRNA].\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        RpLineObject obj = parser.parse(rgLines);
        verify(obj, Arrays.asList(new String[] {"NUCLEOTIDE SEQUENCE [MRNA]"}));
    }

    private void verify(RpLineObject obj, List<String> scopes) {
        assertEquals(scopes, obj.scopes);
    }

    @Test
    void test2() {
        String rgLines = "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        RpLineObject obj = parser.parse(rgLines);
        verify(obj, Arrays.asList(new String[] {"NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]"}));
    }

    @Test
    void test3() {
        String rgLines = "RP   NUCLEOTIDE sequence [MRNA].\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        assertThrows(RuntimeException.class, () -> parser.parse(rgLines));
    }

    @Test
    void test4() {
        String rgLines = "RP   NUCLEOTIDE SEQUENCE [MRNA]\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        assertThrows(RuntimeException.class, () -> parser.parse(rgLines));
    }

    @Test
    void testMulti() {
        String rgLines =
                "RP   NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C), FUNCTION, INTERACTION\n"
                        + "RP   WITH PKC-3, SUBCELLULAR LOCATION, TISSUE SPECIFICITY, DEVELOPMENTAL\n"
                        + "RP   STAGE, AND MUTAGENESIS OF PHE-175 AND PHE-221.\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        RpLineObject obj = parser.parse(rgLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)",
                            "FUNCTION",
                            "INTERACTION WITH PKC-3",
                            "SUBCELLULAR LOCATION",
                            "TISSUE SPECIFICITY",
                            "DEVELOPMENTAL STAGE",
                            "MUTAGENESIS OF PHE-175 AND PHE-221"
                        }));
    }

    @Test
    void testMulti2() {
        String rgLines =
                "RP   X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 22-480, AND DISULFIDE BONDS.\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        RpLineObject obj = parser.parse(rgLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 22-480", "DISULFIDE BONDS"
                        }));
    }

    @Test
    void testWithComma() {
        String rgLines =
                "RP   NUCLEOTIDE SEQUENCE [MRNA], INTERACTION WITH PTDINS(4,5)P2;\n"
                        + "RP   PTDINS(3,4,5)P3 AND INS(1,3,4,5)P4, TISSUE SPECIFICITY, AND\n"
                        + "RP   MUTAGENESIS OF ARG-151 AND ARG-275.\n";
        UniprotKBLineParser<RpLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRpLineParser();
        RpLineObject obj = parser.parse(rgLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "NUCLEOTIDE SEQUENCE [MRNA]",
                            "INTERACTION WITH PTDINS(4,5)P2; PTDINS(3,4,5)P3 AND INS(1,3,4,5)P4",
                            "TISSUE SPECIFICITY",
                            "MUTAGENESIS OF ARG-151 AND ARG-275"
                        }));
    }
}
