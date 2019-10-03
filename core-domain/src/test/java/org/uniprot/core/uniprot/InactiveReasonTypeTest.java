package org.uniprot.core.uniprot;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class InactiveReasonTypeTest {

  @ParameterizedTest
  @EnumSource(InactiveReasonType.class)
  void displayNameIs_enumName(InactiveReasonType enm) {
    assertEquals(enm.toString(), enm.toDisplayName());
  }

}