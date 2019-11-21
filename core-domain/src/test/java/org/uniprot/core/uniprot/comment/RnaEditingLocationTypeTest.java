package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RnaEditingLocationTypeTest {
  @Test
  void toDisplayNameIsSame_name() {
    assertSame(RnaEditingLocationType.Undetermined.name(), RnaEditingLocationType.Undetermined.toDisplayName());
  }

  @Nested
  class typeOf {

    @Test
    void canConvertExactCase() {
      assertEquals(RnaEditingLocationType.Undetermined, RnaEditingLocationType.getType("Undetermined"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "2", "3","4","0abc","8DSF"})
    void canConvertFirstDigitToKnow(String val) {
      assertEquals(RnaEditingLocationType.Known, RnaEditingLocationType.getType(val));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
    void willReturnKnown(String val) {
      assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.getType(val));
    }

    @ParameterizedTest
    @ValueSource(strings = {"NOT_applicable", "NOT_APPLICABLE", "not_applicable", "n"})
    void willReturnKnown_caseSensitive(String val) {
      assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.getType(val));
    }

    @Test
    void willReturnKnown_null() {
      assertEquals(RnaEditingLocationType.Unknown, RnaEditingLocationType.getType(null));
    }
  }
}