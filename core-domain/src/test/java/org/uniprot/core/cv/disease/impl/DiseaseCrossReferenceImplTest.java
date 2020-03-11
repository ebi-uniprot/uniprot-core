package org.uniprot.core.cv.disease.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseCrossReferenceImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    DiseaseCrossReference obj = new DiseaseCrossReferenceImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    DiseaseCrossReference impl = new DiseaseCrossReferenceImpl("db", "id", Collections.emptyList());
    DiseaseCrossReference obj = DiseaseCrossReferenceBuilder.from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}