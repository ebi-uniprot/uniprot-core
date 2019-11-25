package org.uniprot.core.taxonomy.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyLineageImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    TaxonomyLineage obj = new TaxonomyLineageImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    TaxonomyLineage impl = new TaxonomyLineageImpl(87L, "sciName", TaxonomyRank.FAMILY, false);
    TaxonomyLineage obj = new TaxonomyLineageBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}