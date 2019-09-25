package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

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
        assertFalse(Utils.nullOrEmpty(test));
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

    @Nested
    class notNullOrEmpty {

      @Test
      void whenNullOrEmpty_returnFalse() {
        assertAll(
          () -> assertFalse(Utils.notNullOrEmpty((String) null)),
          () -> assertFalse(Utils.notNullOrEmpty(""))
        );
      }

      @ParameterizedTest
      @ValueSource(strings = {"1", "AB", "a%^b", "()+^£$^%%", "aB", "Ab", " s ", "NULL", "   ", "123432"})
      void nonEmpty_shouldAlwaysReturnTrue(String test) {
        assertTrue(Utils.notNullOrEmpty(test));
      }
    }

  }

  @Nested
  class Lists {
    @Nested
    class notNullOrEmpty {
      @Test
      void whenNullOrEmpty_returnFalse() {
        assertAll(
          () -> assertFalse(Utils.notNullOrEmpty((List) null)),
          () -> assertFalse(Utils.notNullOrEmpty(Collections.emptyList()))
        );
      }

      @Test
      void nonEmpty_shouldAlwaysReturnTrue() {
        List<String> list = new LinkedList<>();
        list.add("test1");
        list.add("test2");
        assertAll(
          () -> assertTrue(Utils.notNullOrEmpty(Collections.singletonList(123))),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.checkedList(list, String.class))),
          () -> assertTrue(Utils.notNullOrEmpty(list)),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.synchronizedList(list))),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.unmodifiableList(list)))
        );
      }
    }

    @Nested
    class nullOrEmpty {
      @Test
      void whenNullOrEmpty_returnTrue() {
        assertTrue(Utils.nullOrEmpty((List) null));
        assertTrue(Utils.nullOrEmpty(Collections.emptyList()));
      }

      @Test
      void nonEmpty_shouldAlwaysReturnFalse() {
        List<Integer> list = new LinkedList<>();
        list.add(-12321);
        list.add(454353);
        assertAll(
          () -> assertFalse(Utils.nullOrEmpty(Collections.singletonList("test"))),
          () -> assertFalse(Utils.nullOrEmpty(Collections.checkedList(list, Integer.class))),
          () -> assertFalse(Utils.nullOrEmpty(list)),
          () -> assertFalse(Utils.nullOrEmpty(Collections.synchronizedList(list))),
          () -> assertFalse(Utils.nullOrEmpty(Collections.unmodifiableList(list)))
        );
      }
    }
  }

  @Nested
  class Maps{

    @Nested
    class notNullOrEmpty {
      @Test
      void whenNullOrEmpty_returnFalse() {
        assertAll(
          () -> assertFalse(Utils.notNullOrEmpty((Map) null)),
          () -> assertFalse(Utils.notNullOrEmpty(Collections.emptyMap()))
        );
      }

      @Test
      void nonEmpty_shouldAlwaysReturnTrue() {
        Map<String, Boolean> map = new HashMap<>();
        map.put("test1", true);
        map.put("test2", false);
        assertAll(
          () -> assertTrue(Utils.notNullOrEmpty(Collections.singletonMap(1,2))),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.checkedMap(map, String.class, Boolean.class))),
          () -> assertTrue(Utils.notNullOrEmpty(map)),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.synchronizedMap(map))),
          () -> assertTrue(Utils.notNullOrEmpty(Collections.unmodifiableMap(map)))
        );
      }
    }

    @Nested
    class nullOrEmpty {
      @Test
      void whenNullOrEmpty_returnTrue() {
        assertTrue(Utils.nullOrEmpty((Map) null));
        assertTrue(Utils.nullOrEmpty(Collections.emptyMap()));
      }

      @Test
      void nonEmpty_shouldAlwaysReturnFalse() {
        Map<Integer, Double> map = new HashMap<>();
        map.put(-12321, 1D);
        map.put(454353, 2D);
        assertAll(
          () -> assertFalse(Utils.nullOrEmpty(Collections.singletonMap("test", 1))),
          () -> assertFalse(Utils.nullOrEmpty(Collections.checkedMap(map, Integer.class, Double.class))),
          () -> assertFalse(Utils.nullOrEmpty(map)),
          () -> assertFalse(Utils.nullOrEmpty(Collections.synchronizedMap(map))),
          () -> assertFalse(Utils.nullOrEmpty(Collections.unmodifiableMap(map)))
        );
      }
    }
  }
}

