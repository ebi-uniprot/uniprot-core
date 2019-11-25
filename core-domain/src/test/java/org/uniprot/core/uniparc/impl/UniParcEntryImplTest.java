package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.SequenceImpl;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.builder.UniParcEntryBuilder;

class UniParcEntryImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcEntry obj = new UniParcEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniParcEntry impl =
                new UniParcEntryImpl(
                        new UniParcIdImpl("id"),
                        uniParcDBCrossReferences(),
                        new SequenceImpl("seq"),
                        sequenceFeatures(),
                        taxonomies(),
                        "reason");
        UniParcEntry obj = new UniParcEntryBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canGetTaxonomiesFromDbCrossRef_whenTaxonomiesNotPresent() {
        UniParcEntry obj =
                new UniParcEntryBuilder()
                        .databaseCrossReferences(uniParcDBCrossReferences())
                        .build();
        assertNotNull(obj.getTaxonomies());
        assertFalse(obj.getDbXReferences().isEmpty());
    }
}
