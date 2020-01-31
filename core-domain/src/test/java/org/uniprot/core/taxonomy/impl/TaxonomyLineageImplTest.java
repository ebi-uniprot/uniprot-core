package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;

class TaxonomyLineageImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        TaxonomyLineage obj = new TaxonomyLineageImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        TaxonomyLineage impl = new TaxonomyLineageImpl(87L, "sciName", TaxonomyRank.FAMILY, false);
        TaxonomyLineage obj = TaxonomyLineageBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
