package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UtilsTest {

  @Test
  void testCapitalize() {
    String str = "protein";
    String result = Utils.upperFirstChar(str);
    assertEquals("Protein", result);

  }

  @Test
  void testUncapitalize() {
    String str = "Protein";
    String result = Utils.lowerFirstChar(str);
    assertEquals("protein", result);

  }

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
				assertTrue(Utils.nullOrEmpty((String)null));
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
  }
}

