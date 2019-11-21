package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MichaelisConstantUnitTest {
  @Test
  void getName_displayName_displayNameString_allAreSame() {
    assertSame(MichaelisConstantUnit.MICRO_MOL.getName(), MichaelisConstantUnit.MICRO_MOL.toDisplayName());
    assertSame(MichaelisConstantUnit.MICRO_MOL.getName(), MichaelisConstantUnit.MICRO_MOL.toDisplayNameString());
  }

  @Nested
  class typeOf {

    @Test
    void canConvertExactCase() {
      assertEquals(MichaelisConstantUnit.MILLI_MOL, MichaelisConstantUnit.convert("mM"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"m", "MM", "Mg/ML"})
    void willThrowException_caseSensitive(String val) {
      assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.convert(val));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
    void willThrowException(String val) {
      assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.convert(val));
    }

    @Test
    void willThrowException_null() {
      assertThrows(RuntimeException.class, () -> MichaelisConstantUnit.convert(null));
    }
  }
}