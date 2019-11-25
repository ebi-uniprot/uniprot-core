package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.builder.ProteomeEntryBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createProteomeEntry;

class ProteomeEntryImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ProteomeEntry obj = new ProteomeEntryImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ProteomeEntry impl = createProteomeEntry();
    ProteomeEntry obj = new ProteomeEntryBuilder().from(impl).build();

    assertTrue(impl.getProteinCount() > 0);

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}