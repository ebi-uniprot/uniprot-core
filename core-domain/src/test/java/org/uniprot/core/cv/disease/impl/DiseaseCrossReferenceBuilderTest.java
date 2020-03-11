package org.uniprot.core.cv.disease.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;

import static org.junit.jupiter.api.Assertions.*;

class DiseaseCrossReferenceBuilderTest {
  @Test
  void canAddSingleProperty() {
    String property = "property";
    DiseaseCrossReference reference = new DiseaseCrossReferenceBuilder().propertiesAdd(property).build();
    assertFalse(reference.getProperties().isEmpty());
    assertEquals(1, reference.getProperties().size());
    assertEquals(property, reference.getProperties().get(0));
  }
}