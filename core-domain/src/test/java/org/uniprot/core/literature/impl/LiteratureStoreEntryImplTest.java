package org.uniprot.core.literature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.literature.LiteratureStoreEntry;

import java.util.Collections;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
class LiteratureStoreEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        LiteratureStoreEntry obj = new LiteratureStoreEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        LiteratureStoreEntry impl = new LiteratureStoreEntryImpl(null, Collections.emptyList());
        LiteratureStoreEntry obj = LiteratureStoreEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringMethod() {
        assertNotNull(ObjectsForTests.createCompleteLiteratureStoreEntry().toString());
    }
}
