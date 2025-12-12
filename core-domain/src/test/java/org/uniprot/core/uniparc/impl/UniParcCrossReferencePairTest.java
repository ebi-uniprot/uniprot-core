package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UniParcCrossReferencePairTest {

    @Test
    void testCreatePair() {
        String key = "sample";
        List<UniParcCrossReference> values = List.of(new UniParcCrossReferenceBuilder().build());
        UniParcCrossReferencePair pair = new UniParcCrossReferencePair(key, values);
        Assertions.assertNotNull(pair);
        Assertions.assertEquals("sample", pair.getKey());
        Assertions.assertEquals(values, pair.getValue());
    }

    @Test
    void testEqualsAndHashCode() {
        UniParcCrossReference xref1 =
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
                        List.of(new ProteomeIdComponentBuilder().proteomeId("proteomeId").component("component").build())
                );
        String key = "Sample";
        UniParcCrossReferencePair pair1 = new UniParcCrossReferencePair(key, List.of(xref1));
        UniParcCrossReferencePair pair2 = new UniParcCrossReferencePair(key, List.of(xref1));
        assertTrue(pair1.equals(pair2) && pair2.equals(pair1));
        assertEquals(pair1.hashCode(), pair2.hashCode());
    }

}
