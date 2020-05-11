package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;

import java.util.Arrays;
import java.util.List;

class RgLineParserTest {
    @Test
    void test() {
        String rgLines = "RG   The mouse genome sequencing consortium;\n";
        UniprotKBLineParser<RgLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRgLineParser();
        RgLineObject obj = parser.parse(rgLines);
        verify(obj, Arrays.asList(new String[] {"The mouse genome sequencing consortium"}));
    }

    private void verify(RgLineObject obj, List<String> groups) {
        assertEquals(groups, obj.reference_groups);
    }

    @Test
    void test2() {
        String rgLines =
                "RG   The mouse genome sequencing consortium;\n"
                        + "RG   The something else consortium;\n";
        UniprotKBLineParser<RgLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createRgLineParser();
        RgLineObject obj = parser.parse(rgLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "The mouse genome sequencing consortium",
                            "The something else consortium"
                        }));
    }
}
