package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.builder.ProteomeIdBuilder;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeIdImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ProteomeId obj = new ProteomeIdImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ProteomeId impl = new ProteomeIdImpl("value");
    ProteomeId obj = new ProteomeIdBuilder("val").from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}