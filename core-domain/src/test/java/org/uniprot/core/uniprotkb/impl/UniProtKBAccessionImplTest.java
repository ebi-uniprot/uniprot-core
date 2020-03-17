package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

class UniProtKBAccessionImplTest {

    @Test
    void testUniProtAccessionImpl() {
        String val = "someVal";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
    }

    @Test
    void testValidAccessionSimple() {
        String val = "P12345";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLong() {
        String val = "A0A024R2Q3";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLongIsoform() {
        String val = "A0A024R2Q3-2";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionSimpleIsoform() {
        String val = "P12345-3";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionNotValid() {
        String val = "A131";
        UniProtKBAccession accession = new UniProtKBAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertFalse(accession.isValidAccession());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBAccession obj = new UniProtKBAccessionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtKBAccession impl = new UniProtKBAccessionImpl("VAL");
        UniProtKBAccession obj = UniProtKBAccessionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
