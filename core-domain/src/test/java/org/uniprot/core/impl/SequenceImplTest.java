package org.uniprot.core.impl;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SequenceImplTest {
    private static Sequence sequence;
    private static String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";

    @BeforeAll
    static void setup() {
        sequence = new SequenceImpl(value);
    }

    @Test
    void testGetLength() {
        assertEquals(40, sequence.getLength());
    }

    @Test
    void testGetMolecularWeight() {
        assertEquals(4544, sequence.getMolWeight());
    }

    @Test
    void testGetCRC64() {
        assertEquals("0C69420967F56414", sequence.getCrc64());
    }

    @Test
    void testGetMD5() {
        assertEquals("CFA0179DAE1A227203E07C673627B28F", sequence.getMd5());
    }

    @Test
    void testGetValue() {
        assertEquals(value, sequence.getValue());
    }

    @Test
    void testSubSequence() {
        int start = 5;
        int end = 20;
        Sequence subSeq = sequence.subSequence(start, end);
        assertEquals("STPSRRSSRRGRVTP", subSeq.getValue());
    }
}
