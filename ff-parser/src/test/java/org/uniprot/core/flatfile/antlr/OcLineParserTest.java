package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.oc.OcLineObject;

class OcLineParserTest {
    @Test
    void test() {
        String ocLines = "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi.\n";
        UniprotLineParser<OcLineObject> parser =
                new DefaultUniprotLineParserFactory().createOcLineParser();
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
        UniprotLineParser<OcLineObject> parser =
                new DefaultUniprotLineParserFactory().createOcLineParser();
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

        UniprotLineParser<OcLineObject> parser =
                new DefaultUniprotLineParserFactory().createOcLineParser();
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
        UniprotLineParser<OcLineObject> parser =
                new DefaultUniprotLineParserFactory().createOcLineParser();
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
