package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtkbAccession;

class UniProtkbAccessionImplTest {

    @Test
    void testUniProtAccessionImpl() {
        String val = "someVal";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
    }

    @Test
    void testValidAccessionSimple() {
        String val = "P12345";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLong() {
        String val = "A0A024R2Q3";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionLongIsoform() {
        String val = "A0A024R2Q3-2";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionSimpleIsoform() {
        String val = "P12345-3";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertTrue(accession.isValidAccession());
    }

    @Test
    void testValidAccessionNotValid() {
        String val = "A131";
        UniProtkbAccession accession = new UniProtkbAccessionImpl(val);
        assertNotNull(accession);
        assertEquals(val, accession.getValue());
        assertFalse(accession.isValidAccession());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtkbAccession obj = new UniProtkbAccessionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtkbAccession impl = new UniProtkbAccessionImpl("VAL");
        UniProtkbAccession obj = UniProtkbAccessionBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
