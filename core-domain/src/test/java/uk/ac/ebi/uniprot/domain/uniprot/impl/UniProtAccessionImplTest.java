package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import static org.junit.Assert.*;

public class UniProtAccessionImplTest {

    @Test
    public void testUniProtAccessionImpl() {
        String val = "someVal";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        TestHelper.verifyJson(accession);
    }

    @Test
    public void testValidAccessionSimple() {
        String val = "P12345";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
        TestHelper.verifyJson(accession);
    }

    @Test
    public void testValidAccessionLong() {
        String val = "A0A024R2Q3";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
        TestHelper.verifyJson(accession);
    }

    @Test
    public void testValidAccessionLongIsoform() {
        String val = "A0A024R2Q3-2";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
        TestHelper.verifyJson(accession);
    }

    @Test
    public void testValidAccessionSimpleIsoform() {
        String val = "P12345-3";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    public void testValidAccessionNotValid() {
        String val = "A131";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertFalse(accession.isValidAccession());
    }
}
