package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rp.RPLineBuilder;

class RPLineBuilderTest {
    private final RPLineBuilder builder = new RPLineBuilder();

    @Test
    void test1() {
        List<String> css = new ArrayList<>();
        css.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");

        List<String> lines = builder.buildLine(css, true, true);
        String expected = "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].";
        assertEquals(1, lines.size());
        assertEquals(expected, lines.get(0));
    }

    @Test
    void test2() {
        List<String> css = new ArrayList<>();
        css.add("NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA]");
        List<String> lines = builder.buildLine(css, false, true);
        String expected = "NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].";
        assertEquals(1, lines.size());
        assertEquals(expected, lines.get(0));
    }
}
