package org.uniprot.core.literature.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.builder.LiteratureStatisticsBuilder;

import static org.junit.jupiter.api.Assertions.*;

class LiteratureStatisticsImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    LiteratureStatistics obj = new LiteratureStatisticsImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    LiteratureStatistics impl = new LiteratureStatisticsImpl(7, 8,10);
    LiteratureStatistics obj = new LiteratureStatisticsBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}