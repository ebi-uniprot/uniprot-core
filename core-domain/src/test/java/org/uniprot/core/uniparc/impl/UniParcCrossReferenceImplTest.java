package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniParcCrossReferenceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcCrossReference obj = new UniParcCrossReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniParcCrossReference impl =
                new UniParcCrossReferenceImpl(
                        UniParcDatabase.SWISSPROT,
                        "id",
                        Collections.emptyList(),
                        2,
                        -3,
                        true,
                        LocalDate.now(),
                        LocalDate.now(),
                        "geneName",
                        "proteinName",
                        new OrganismBuilder().taxonId(10L).scientificName("sName").build(),
                        "chain",
                        "ncbiGi",
                        List.of(new ProteomeBuilder().id("proteomeId").component("component").build()));
        UniParcCrossReference obj = UniParcCrossReferenceBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
