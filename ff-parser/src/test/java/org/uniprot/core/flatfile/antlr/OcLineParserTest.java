package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineObject;

import java.util.Arrays;
import java.util.List;

class OcLineParserTest {
    @Test
    void test() {
        String ocLines = "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi.\n";
        UniprotKBLineParser<OcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOcLineParser();
        OcLineObject obj = parser.parse(ocLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Eukaryota",
                            "Metazoa",
                            "Chordata",
                            "Craniata",
                            "Vertebrata",
                            "Euteleostomi"
                        }));
    }

    private void verify(OcLineObject obj, List<String> nodes) {
        assertEquals(nodes, obj.nodes);
    }

    @Test
    void test2() {
        String ocLines =
                "OC   Eukaryota; Metazoa; Chordata; Craniata;\n"
                        + "OC   Vertebrata; Euteleostomi.\n";
        ;
        UniprotKBLineParser<OcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOcLineParser();
        OcLineObject obj = parser.parse(ocLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Eukaryota",
                            "Metazoa",
                            "Chordata",
                            "Craniata",
                            "Vertebrata",
                            "Euteleostomi"
                        }));
    }

    @Test
    void test3() {
        String ocLines = "OC   Viruses; dsDNA viruses, no RNA stage; Iridoviridae; Ranavirus.\n";

        UniprotKBLineParser<OcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOcLineParser();
        OcLineObject obj = parser.parse(ocLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Viruses", "dsDNA viruses, no RNA stage", "Iridoviridae", "Ranavirus"
                        }));
    }

    @Test
    void test4() {
        String ocLines =
                "OC   Bacteria; Bacteroidetes; Bacteroidetes Order II. Incertae sedis;\n"
                        + "OC   Rhodothermaceae; Salinibacter.\n";
        UniprotKBLineParser<OcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createOcLineParser();
        OcLineObject obj = parser.parse(ocLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Bacteria",
                            "Bacteroidetes",
                            "Bacteroidetes Order II. Incertae sedis",
                            "Rhodothermaceae",
                            "Salinibacter"
                        }));
    }
}
