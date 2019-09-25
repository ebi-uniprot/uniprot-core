package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UtilsTest {

  @Nested
  class Strings {
    @Nested
    class nullToEmpty {

      @Test
      void whenNull_returnEmptyString() {
        assertEquals("", Utils.nullToEmpty(null));
      }

      @ParameterizedTest
      @ValueSource(strings = {"a", "AB", "a%^b", "()+^£$^%%", "aB", "Ab", "", "null", "   ", "123432"})
      void notNull_returnAsItIs(String test) {
        assertEquals(test, Utils.nullToEmpty(test));
      }
    }

    @Nested
    class nullOrEmpty {
      @Test
      void whenNull_returnTrue() {
        assertTrue(Utils.nullOrEmpty((String) null));
      }

      @Test
      void whenEmpty_returnTrue() {
        assertTrue(Utils.nullOrEmpty(""));
      }

      @ParameterizedTest
      @ValueSource(strings = {"1", "AB", "a%^b", "()+^£$^%%", "aB", "Ab", " s ", "NULL", "   ", "123432"})
      void nonEmpty_shouldAlwaysReturnFalse(String test) {
        assertEquals(test, Utils.nullToEmpty(test));
      }
    }

    @Nested
    class upperFirstChar {
      @Test
      void whenNullOrEmpty_returnSame() {
        assertAll(
          () -> assertNull(Utils.upperFirstChar(null)),
          () -> assertEquals("", Utils.upperFirstChar(""))
        );
      }

      @Test
      void capitalizeFirstChar() {
        assertAll(
          () -> assertEquals("Protein", Utils.upperFirstChar("protein")),
          () -> assertEquals(" protein", Utils.upperFirstChar(" protein")),
          () -> assertEquals("Protein abc", Utils.upperFirstChar("protein abc")),
          () -> assertEquals("$protein", Utils.upperFirstChar("$protein")),
          () -> assertEquals("P!rotein", Utils.upperFirstChar("p!rotein")),
          () -> assertEquals("Protein Def", Utils.upperFirstChar("protein Def")),
          () -> assertEquals("12345", Utils.upperFirstChar("12345"))
        );
      }
    }

    @Nested
    class lowerFirstChar {
      @Test
      void whenNullOrEmpty_returnSame() {
        assertAll(
          () -> assertNull(Utils.lowerFirstChar(null)),
          () -> assertEquals("", Utils.lowerFirstChar(""))
        );
      }

      @Test
      void uncapitalizeFirstChar() {
        String str = "Protein";
        String result = Utils.lowerFirstChar(str);
        assertEquals("protein", result);
        assertAll(
          () -> assertEquals("protein", Utils.lowerFirstChar("Protein")),
          () -> assertEquals(" protein", Utils.lowerFirstChar(" protein")),
          () -> assertEquals("protein abc", Utils.lowerFirstChar("Protein abc")),
          () -> assertEquals("$protein", Utils.lowerFirstChar("$protein")),
          () -> assertEquals("p!rotein", Utils.lowerFirstChar("P!rotein")),
          () -> assertEquals("protein Def", Utils.lowerFirstChar("Protein Def")),
          () -> assertEquals("12345", Utils.lowerFirstChar("12345"))
        );
      }
    }

  }
}

