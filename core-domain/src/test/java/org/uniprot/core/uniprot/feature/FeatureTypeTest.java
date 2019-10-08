package org.uniprot.core.uniprot.feature;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTypeTest {

  @Test
  void canCreateFromLowerCaseValue() {
    assertEquals(FeatureType.CARBOHYD, FeatureType.typeOf("glycosylation site"));
  }

  @Test
  void canCreateFromUpperCaseValue() {
    assertEquals(FeatureType.DOMAIN, FeatureType.typeOf("DOMAIN"));
  }

  @Test
  void canCreateFromLowerCaseName() {
    assertEquals(FeatureType.HELIX, FeatureType.typeOf("helix"));
  }

  @Test
  void canCreateFromUpperCaseName() {
    assertEquals(FeatureType.NON_CONS, FeatureType.typeOf("NON_CONS"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "bc", "   "})
  void throwsIllegalForOthers(String val) {
    assertThrows(IllegalArgumentException.class, ()->FeatureType.typeOf(val));
  }

  @Test
  void valueAndDisplayNameAreEqual() {
    assertEquals(FeatureType.CHAIN.toDisplayName(), FeatureType.CHAIN.getValue());
  }

  @Test
  void nameAndDisplayNameAreNotEqual() {
    assertNotEquals(FeatureType.CHAIN.toDisplayName(), FeatureType.CHAIN.getName());
  }
}