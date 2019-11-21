package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhysiologicalDirectionTypeTest {

  @Nested
  class typeOf {

    @Test
    void canConvertLowerCase() {
      assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, PhysiologicalDirectionType.typeOf("left-to-right"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(PhysiologicalDirectionType.RIGHT_TO_LEFT, PhysiologicalDirectionType.typeOf("RIGHT-TO-LEFT"));
    }

    @Test
    void canConvertMixCase() {
      assertEquals(PhysiologicalDirectionType.RIGHT_TO_LEFT, PhysiologicalDirectionType.typeOf("RIGHT-to-LEFT"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
    void willThrowException(String val) {
      assertThrows(IllegalArgumentException.class, () -> PhysiologicalDirectionType.typeOf(val));
    }

    @Test
    void willThrowException_null() {
      assertThrows(IllegalArgumentException.class, () -> PhysiologicalDirectionType.typeOf(null));
    }
  }

  @Test
  void displayNameWillReturnName() {
    assertEquals("right-to-left", PhysiologicalDirectionType.RIGHT_TO_LEFT.toDisplayName()); }
}