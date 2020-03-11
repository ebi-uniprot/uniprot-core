package org.uniprot.core.cv.chebi.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.ChebiEntry;

import static org.junit.jupiter.api.Assertions.*;

class ChebiEntryImplTest {
  private ChebiEntry impl = new ChebiEntryImpl("id", "name", "inchiKey");

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ChebiEntry obj = new ChebiEntryImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ChebiEntry obj = ChebiEntryBuilder.from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }

  @Test
  void toStringTest() {
    assertEquals("ChebiEntryImpl{id='id', inchiKey='inchiKey', name='name'}", impl.toString());
  }
}