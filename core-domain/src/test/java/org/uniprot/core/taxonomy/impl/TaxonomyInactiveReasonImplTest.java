package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;
import org.uniprot.core.taxonomy.builder.TaxonomyInactiveReasonBuilder;

class TaxonomyInactiveReasonImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        TaxonomyInactiveReason obj = new TaxonomyInactiveReasonImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        TaxonomyInactiveReason impl =
                new TaxonomyInactiveReasonImpl(TaxonomyInactiveReasonType.DELETED, 765L);
        TaxonomyInactiveReason obj = TaxonomyInactiveReasonBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
