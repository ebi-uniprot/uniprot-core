package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rt.RTLineBuilder;

import java.util.List;

class RTLineBuilderTest {
    private final RTLineBuilder builder = new RTLineBuilder();

    @Test
    void test1() {
        String title =
                "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus"
                        + " (family Iridoviridae).";
        List<String> lines = builder.buildLine(title, true, true);
        assertEquals(2, lines.size());
        String expected = "RT   Ranavirus (family Iridoviridae).\";";
        assertEquals(expected, lines.get(1));
    }

    @Test
    void test2() {
        String title =
                "Comparative genomic analyses of frog virus 3, type species of the genus Ranavirus"
                        + " (family Iridoviridae).";
        List<String> lines = builder.buildLine(title, false, true);
        assertEquals(1, lines.size());
        String expected =
                "\"Comparative genomic analyses of frog virus 3, type species of the genus"
                        + " Ranavirus (family Iridoviridae).\";";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void testLineWrapDash() {
        String title =
                "Characterization and organization of the genes encoding the A-, B- and C-chains"
                        + " of human complement subcomponent C1q. The complete derived amino acid"
                        + " sequence of human C1q.";
        List<String> lines = builder.buildLine(title, true, true);
        assertEquals(
                "RT   \"Characterization and organization of the genes encoding the A-, B- and C-",
                lines.get(0));
    }
}
