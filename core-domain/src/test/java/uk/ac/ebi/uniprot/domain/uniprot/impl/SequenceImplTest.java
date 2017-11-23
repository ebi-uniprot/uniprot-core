package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.common.Sequence;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SequenceImplTest {

    private Sequence sequence;
    private String value = "MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE";
    @Before 
    public void setup(){
        sequence = new SequenceImpl(value);
    }
    @Test
    public void testGetLength() {
        assertEquals(40, sequence.getLength());
      
    }

    @Test
    public void testGetMolecularWeight() {
        assertEquals(4544, sequence.getMolecularWeight());
    }

    @Test
    public void testGetCRC64() {
        assertEquals("0C69420967F56414", sequence.getCRC64());
    }

    @Test
    public void testGetMD5() {
        assertEquals("CFA0179DAE1A227203E07C673627B28F", sequence.getMD5());
    }

    @Test
    public void testGetValue() {
        assertEquals(value, sequence.getValue());
    }

    @Test
    public void testSubSequence() {
        int start =5;
        int end =20;
        Sequence subSeq = sequence.subSequence(start, end);
        assertEquals("STPSRRSSRRGRVTP", subSeq.getValue());
    }

}
