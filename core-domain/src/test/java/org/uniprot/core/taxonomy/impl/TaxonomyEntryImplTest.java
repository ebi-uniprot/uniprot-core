package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyEntryBuilder;

class TaxonomyEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        TaxonomyEntry obj = new TaxonomyEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        TaxonomyEntry impl =
                new TaxonomyEntryImpl(
                        3L,
                        "sciName",
                        "commonName",
                        Collections.singletonList("syn"),
                        "mnemo",
                        4L,
                        TaxonomyRank.NO_RANK,
                        false,
                        true,
                        Collections.singletonList("other"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        null,
                        Collections.singletonList("links"),
                        new TaxonomyStatisticsImpl(),
                        new TaxonomyInactiveReasonImpl());
        TaxonomyEntry obj = new TaxonomyEntryBuilder().from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
