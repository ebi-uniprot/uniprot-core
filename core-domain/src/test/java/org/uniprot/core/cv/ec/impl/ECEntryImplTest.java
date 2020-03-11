package org.uniprot.core.cv.ec.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.ECEntry;

import static org.junit.jupiter.api.Assertions.*;

class ECEntryImplTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ECEntry obj = new ECEntryImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ECEntry impl = new ECEntryImpl("id", "label");
    ECEntry obj = ECEntryBuilder.from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}