package org.uniprot.core;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionModifierTest {

  @ParameterizedTest
  @EnumSource(PositionModifier.class)
  void toDisplayName_returnSameConst(PositionModifier enm) {
    assertEquals(enm.toString(), enm.toDisplayName());
  }
}