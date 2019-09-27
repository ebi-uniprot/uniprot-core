package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ac.AcLineObject;

class AcLineParserTest {
    @Test
    void validOneLineOneAcc() {
        String ac_one_line = "AC   Q6GZX4;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        AcLineObject obj = parser.parse(ac_one_line);
        assertEquals("Q6GZX4", obj.primaryAcc);
        assertTrue(obj.secondaryAcc.isEmpty());
    }

    @Test
    void inValidOneLineOneAcc() {
        String ac_one_line = "AC   Q6GDDZX4;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        assertThrows(RuntimeException.class, () -> parser.parse(ac_one_line));
    }

    @Test
    void validOneLineMoreAcc() {
        String ac_one_line = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        AcLineObject obj = parser.parse(ac_one_line);
        assertEquals("Q6GZX4", obj.primaryAcc);
        List<String> expected = Arrays.asList(new String[] {"Q6GZX5", "Q6GZX6"});
        assertEquals(expected, obj.secondaryAcc);
    }

    @Test
    void validMultiLineMoreAcc() {
        String ac_one_line =
                "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
                        + "AC   Q6GZX7; Q6GZX8; Q6GZX9;\n"
                        + "AC   Q6GZX0;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        AcLineObject obj = parser.parse(ac_one_line);
        assertEquals("Q6GZX4", obj.primaryAcc);
        List<String> expected =
                Arrays.asList(
                        new String[] {"Q6GZX5", "Q6GZX6", "Q6GZX7", "Q6GZX8", "Q6GZX9", "Q6GZX0"});
        assertEquals(expected, obj.secondaryAcc);
    }

    @Test
    void validOneLineMoreNewAcc() {
        String ac_one_line = "AC   A0A000A000; Q6GZX5; Q6GZX6;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        AcLineObject obj = parser.parse(ac_one_line);
        assertEquals("A0A000A000", obj.primaryAcc);
        List<String> expected = Arrays.asList(new String[] {"Q6GZX5", "Q6GZX6"});
        assertEquals(expected, obj.secondaryAcc);
    }

    @Test
    void validOneLineMoreIsoformAcc() {
        String ac_one_line = "AC   A0A000A000-1; Q6GZX5-11; Q6GZX6-10;\n";
        UniprotLineParser<AcLineObject> parser =
                new DefaultUniprotLineParserFactory().createAcLineParser();
        AcLineObject obj = parser.parse(ac_one_line);
        assertEquals("A0A000A000-1", obj.primaryAcc);
        List<String> expected = Arrays.asList(new String[] {"Q6GZX5-11", "Q6GZX6-10"});
        assertEquals(expected, obj.secondaryAcc);
    }
}
