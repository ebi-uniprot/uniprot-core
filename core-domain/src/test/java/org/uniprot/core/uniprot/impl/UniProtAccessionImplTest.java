package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

class UniProtAccessionImplTest {

    @Test
    void testUniProtAccessionImpl() {
        String val = "someVal";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
    }

    @Test
    void testValidAccessionSimple() {
        String val = "P12345";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLong() {
        String val = "A0A024R2Q3";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLongIsoform() {
        String val = "A0A024R2Q3-2";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionSimpleIsoform() {
        String val = "P12345-3";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionNotValid() {
        String val = "A131";
        UniProtAccession accession = new UniProtAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertFalse(accession.isValidAccession());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtAccession obj = new UniProtAccessionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtAccession impl = new UniProtAccessionImpl("VAL");
        UniProtAccession obj = UniProtAccessionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
