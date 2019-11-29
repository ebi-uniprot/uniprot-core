package org.uniprot.core.uniprot.feature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;

import static org.junit.jupiter.api.Assertions.*;

class FeatureLocationTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    FeatureLocation obj = new FeatureLocation();
    assertNotNull(obj);
  }

  @Test
  void canCreateWithOutSequence() {
    FeatureLocation obj = new FeatureLocation(null,null, PositionModifier.EXACT, PositionModifier.UNSURE);

    assertNull(obj.getSequence());

    assertNotNull(obj.getStart());
    assertNull(obj.getStart().getValue());
    assertEquals(PositionModifier.EXACT, obj.getStart().getModifier());

    assertNotNull(obj.getEnd());
    assertNull(obj.getEnd().getValue());
    assertEquals(PositionModifier.UNSURE, obj.getEnd().getModifier());
  }
}