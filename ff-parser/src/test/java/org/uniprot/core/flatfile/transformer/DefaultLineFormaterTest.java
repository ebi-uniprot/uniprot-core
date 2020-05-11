package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.DefaultLineFormater;

class DefaultLineFormaterTest {
    @Test
    void testkeyword() {
        String expected =
                "KW   Activator {ECO:00000001}; Complete proteome {ECO:00000001};\n"
                        + "KW   Reference proteome; Transcription {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006};\n"
                        + "KW   Transcription regulation.\n";
        String lines =
                "Activator {ECO:00000001}; Complete proteome {ECO:00000001};\n"
                        + "Reference proteome; Transcription {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006};\n"
                        + "Transcription regulation.\n";
        verify(expected, lines, "KW   ");
    }

    void verify(String expected, String lines, String prefix) {
        DefaultLineFormater formater = new DefaultLineFormater(prefix);

        String formated = formater.format(lines);
        assertEquals(expected, formated);
    }

    @Test
    void testDR() {
        String expected =
                "DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.\n"
                        + "DR   RefSeq; YP_031579.1; NC_005946.1.\n"
                        + "DR   ProteinModelPortal; Q6GZX4; -.\n";
        String lines =
                "EMBL; AY548484; AAT09660.1; -; Genomic_DNA.\n"
                        + "RefSeq; YP_031579.1; NC_005946.1.\n"
                        + "ProteinModelPortal; Q6GZX4; -.\n";
        verify(expected, lines, "DR   ");
    }

    @Test
    void testGene() {
        String expected =
                "GN   Name=GeneA {ECO:0000006|PubMed:20858735}; Synonyms=Syn1"
                        + " {ECO:0000006|PubMed:20858735,\n"
                        + "GN   ECO:0000005|PubMed:208587235}, Syn2 {ECO:0000005|PubMed:208587235};\n";
        String lines =
                "Name=GeneA {ECO:0000006|PubMed:20858735}; Synonyms=Syn1"
                        + " {ECO:0000006|PubMed:20858735,\n"
                        + "ECO:0000005|PubMed:208587235}, Syn2 {ECO:0000005|PubMed:208587235};\n";
        verify(expected, lines, "GN   ");
    }

    @Test
    void testOG() {
        String expected =
                "OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and\n"
                        + "OG   Plasmid IncFII R1-19 (R1 drd-19).\n";
        String lines =
                "Plasmid R6-5, Plasmid IncFII R100 (NR1), and\n"
                        + "Plasmid IncFII R1-19 (R1 drd-19).\n";
        verify(expected, lines, "OG   ");
    }
}
