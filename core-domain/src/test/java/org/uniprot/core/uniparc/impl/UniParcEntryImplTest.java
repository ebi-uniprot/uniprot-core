package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniparc.UniParcEntry;

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
                        new SequenceBuilder("seq").build(),
                        sequenceFeatures(),
                        taxonomies(),
                        "reason");
        UniParcEntry obj = UniParcEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canGetTaxonomiesFromDbCrossRef_whenTaxonomiesNotPresent() {
        UniParcEntry obj =
                new UniParcEntryBuilder()
                        .uniParcCrossReferencesSet(uniParcDBCrossReferences())
                        .build();
        assertNotNull(obj.getTaxonomies());
        assertFalse(obj.getUniParcCrossReferences().isEmpty());
    }
}
