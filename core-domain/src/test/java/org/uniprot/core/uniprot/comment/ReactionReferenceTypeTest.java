package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ReactionReferenceTypeTest {
  @Test
  void getName_displayName_areSame() {
    assertSame(
      ReactionReferenceType.CHEBI.getName(),
      ReactionReferenceType.CHEBI.toDisplayName());
  }

  @Nested
  class typeOf {

    @Test
    void canConvertLowerCase() {
      assertEquals(ReactionReferenceType.RHEA, ReactionReferenceType.typeOf("rhea"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(ReactionReferenceType.CHEBI, ReactionReferenceType.typeOf("CHEBI"));
    }

    @Test
    void canConvertMixCase() {
      assertEquals(ReactionReferenceType.RHEA, ReactionReferenceType.typeOf("RheA"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
    void willBeUnknown(String val) {
      assertEquals(ReactionReferenceType.UNKNOWN, ReactionReferenceType.typeOf(val));
    }

    @Test
    void willBeUnknown_null() {
      assertEquals(ReactionReferenceType.UNKNOWN, ReactionReferenceType.typeOf(null));
    }
  }
}