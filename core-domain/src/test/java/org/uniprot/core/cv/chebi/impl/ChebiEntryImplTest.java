package org.uniprot.core.cv.chebi.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.ChebiEntry;

class ChebiEntryImplTest {
    private ChebiEntry impl =
            new ChebiEntryImpl(
                    "id",
                    "name",
                    "inchiKey",
                    List.of(
                            new ChebiEntryImpl(
                                    "relatedId", "relatedName", "relatedInchiKey", null, null)),
                    List.of("synonym1"));

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ChebiEntry obj = new ChebiEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ChebiEntry obj = ChebiEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals(
                "ChebiEntryImpl{id='id', inchiKey='inchiKey', name='name', relatedIds=[ChebiEntryImpl{id='relatedId', inchiKey='relatedInchiKey', name='relatedName', relatedIds=null, synonyms=null}], synonyms=[synonym1]}",
                impl.toString());
    }
}
