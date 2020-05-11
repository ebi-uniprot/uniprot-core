package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStrain;

class TaxonomyStrainImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        TaxonomyStrain obj = new TaxonomyStrainImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        TaxonomyStrain impl = new TaxonomyStrainImpl("name", Collections.singletonList("syno"));
        TaxonomyStrain obj = TaxonomyStrainBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
