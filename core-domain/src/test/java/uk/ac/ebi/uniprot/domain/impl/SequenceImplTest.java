package uk.ac.ebi.uniprot.domain.impl;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.TestHelper;

import static org.junit.Assert.assertEquals;

public class SequenceImplTest {
    private static Sequence sequence;
    private static String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";

    @BeforeAll
    public static void setup() {
        sequence = new SequenceImpl(value);
    }

    @Test
    public void testGetLength() {
        assertEquals(40, sequence.getLength());

    }

    @Test
    public void testGetMolecularWeight() {
        assertEquals(4544, sequence.getMolWeight());
    }

    @Test
    public void testGetCRC64() {
        assertEquals("0C69420967F56414", sequence.getCrc64());
    }

    @Test
    public void testGetMD5() {
        assertEquals("CFA0179DAE1A227203E07C673627B28F", sequence.getMd5());
    }

    @Test
    public void testGetValue() {
        assertEquals(value, sequence.getValue());
    }

    @Test
    public void testSubSequence() {
        int start = 5;
        int end = 20;
        Sequence subSeq = sequence.subSequence(start, end);
        assertEquals("STPSRRSSRRGRVTP", subSeq.getValue());
    }

    @Test
    public void testToJson() {
        TestHelper.verifyJson(sequence);
    }
}
