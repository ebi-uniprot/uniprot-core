package org.uniprot.core.parser.fasta;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FastaUtilsTest {
    @Test
    void parseSequenceOneLineSimple() {
        String input = getSequence(10);
        String result = FastaUtils.parseSequence(input);
        assertEquals(input, result);
    }

    @Test
    void parseSequenceOneLineFullDoNotAddLineBreak() {
        String input = getSequence(60);
        String result = FastaUtils.parseSequence(input);
        assertEquals(input, result);
    }

    @Test
    void parseSequenceTwoLines() {
        String input = getSequence(61);
        String result = FastaUtils.parseSequence(input);
        String expect = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "A";
        assertEquals(expect, result);
    }

    @Test
    void parseSequenceTwoLinesFull() {
        String input = getSequence(120);
        String result = FastaUtils.parseSequence(input);
        String expect = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        assertEquals(expect, result);
    }

    @Test
    void parseSequenceThreeLines() {
        String input = getSequence(121);
        String result = FastaUtils.parseSequence(input);
        String expect = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "A";
        assertEquals(expect, result);
    }

    private String getSequence(int length){
        String s = "A";
        return s.repeat(length);
    }
}