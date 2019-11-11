package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MichaelisConstant;
import org.uniprot.core.uniprot.comment.builder.MichaelisConstantBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

class MichaelisConstantImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    MichaelisConstant obj = new MichaelisConstantImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    MichaelisConstant impl = new MichaelisConstantImpl(8.2, MichaelisConstant.NORMALIZED_UNIT, "sub",
      createEvidences());
    MichaelisConstant obj = new MichaelisConstantBuilder().from(impl).build();

    assertTrue(impl.hasEvidences());

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }

}