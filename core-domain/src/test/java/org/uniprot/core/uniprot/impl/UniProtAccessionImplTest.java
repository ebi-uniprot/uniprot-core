package org.uniprot.core.uniprot.impl;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

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