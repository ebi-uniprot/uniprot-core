package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CommentTypeTest {

  @Nested
  class typeOf {

    @Test
    void canConvertLowerCase() {
      assertEquals(CommentType.CATALYTIC_ACTIVITY, CommentType.typeOf("catalytic activity"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(CommentType.COFACTOR, CommentType.typeOf("COFACTOR"));
    }

    @Test
    void canConvertMixCase() {
      assertEquals(CommentType.ACTIVITY_REGULATION, CommentType.typeOf("AcTiVItY ReGULaTION"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
    void willThrowException(String val) {
      assertThrows(IllegalArgumentException.class, () -> CommentType.typeOf(val));
    }

    @Test
    void willThrowException_null() {
      assertThrows(IllegalArgumentException.class, () -> CommentType.typeOf(null));
    }

    @Test
    void dependsOnDisplayName() {
      assertEquals(CommentType.WEBRESOURCE, CommentType.typeOf("web resource"));
      assertThrows(IllegalArgumentException.class, () -> CommentType.typeOf("online information"));
    }
  }

  @Test
  void toXmlDisplayName_displayName_areNotSame() {
    assertNotSame(
      CommentType.PHARMACEUTICAL.toXmlDisplayName(),
      CommentType.PHARMACEUTICAL.toDisplayName());
  }

}