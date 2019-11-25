package org.uniprot.core.taxonomy.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.builder.TaxonomyStrainBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyStrainImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    TaxonomyStrain obj = new TaxonomyStrainImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    TaxonomyStrain impl = new TaxonomyStrainImpl("name", Collections.singletonList("syno"));
    TaxonomyStrain obj = new TaxonomyStrainBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}