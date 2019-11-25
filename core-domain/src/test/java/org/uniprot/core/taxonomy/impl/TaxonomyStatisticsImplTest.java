package org.uniprot.core.taxonomy.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStatistics;
import org.uniprot.core.taxonomy.builder.TaxonomyStatisticsBuilder;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyStatisticsImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    TaxonomyStatistics obj = new TaxonomyStatisticsImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    TaxonomyStatistics impl = new TaxonomyStatisticsImpl(87L,35L, 32L, 9867L);
    TaxonomyStatistics obj = new TaxonomyStatisticsBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}