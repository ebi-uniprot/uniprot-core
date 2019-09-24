package org.uniprot.core.util.property;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PropertyArrayTest {

  private final String arrayStr =
    "[" +
      "true," +
      "false," +
      "\"true\"," +
      "\"false\"," +
      "\"hello\"," +
      "23.45e-4," +
      "\"23.45\"," +
      "42," +
      "\"43\"," +
      "[" +
      "\"world\"" +
      "]," +
      "{" +
      "\"key1\":\"value1\"," +
      "\"key2\":\"value2\"," +
      "\"key3\":\"value3\"," +
      "\"key4\":\"value4\"" +
      "}," +
      "0," +
      "\"-1\"" +
      "]";

  /**
   * Tests that the similar method is working as expected.
   */
  @Test
  void verifySimilar() {
    final String string1 = "HasSameRef";
    PropertyArray obj1 = new PropertyArray()
      .put("abc")
      .put(string1)
      .put(2);

    PropertyArray obj2 = new PropertyArray()
      .put("abc")
      .put(string1)
      .put(3);

    PropertyArray obj3 = new PropertyArray()
      .put("abc")
      .put(string1)
      .put(2);

    assertFalse(obj1.similar(obj2), "Should eval to false");

    assertTrue(obj1.similar(obj3), "Should eval to true");
  }

  /**
   * Attempt to create a PropertyArray with a null string.
   * Expects a NullPointerException.
   */
  @Test
  void nullException() {
    String str = null;
    assertThrows(NullPointerException.class, () -> new PropertyArray(str), "Should throw an exception");
  }

  /**
   * Attempt to create a PropertyArray with an empty string.
   * Expects a PropertyException.
   */
  @Test
  void emptyStr() {
    String str = "";
    try {
      new PropertyArray(str);
    } catch (PropertyException e) {
      assertEquals(
        "A JSONArray text must start with '[' at 0 [character 1 line 1]",
        e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Attempt to create a PropertyArray with an unclosed array.
   * Expects an exception
   */
  @Test
  void unclosedArray() {
    try {
      new PropertyArray("[");
    } catch (PropertyException e) {
      assertEquals(
        "Expected a ',' or ']' at 1 [character 2 line 1]",
        e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Attempt to create a PropertyArray with an unclosed array.
   * Expects an exception
   */
  @Test
  void unclosedArray2() {
    try {
      new PropertyArray("[\"test\"");
    } catch (PropertyException e) {
      assertEquals(
        "Expected a ',' or ']' at 7 [character 8 line 1]",
        e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Attempt to create a PropertyArray with an unclosed array.
   * Expects an exception
   */
  @Test
  void unclosedArray3() {
    try {
      new PropertyArray("[\"test\",");
    } catch (PropertyException e) {
      assertEquals(
        "Expected a ',' or ']' at 8 [character 9 line 1]",
        e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Attempt to create a PropertyArray with a string as object that is
   * not a JSON array doc.
   * Expects a PropertyException.
   */
  @Test
  void badObject() {
    String str = "abc";
    try {
      new PropertyArray((Object) str);
    } catch (PropertyException e) {
      assertEquals("JSONArray initial value should be a string or collection or array.", e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Verifies that the constructor has backwards compatibility with RAW types pre-java5.
   */
  @Test
  void verifyConstructor() {

    final PropertyArray expected = new PropertyArray("[10]");

    @SuppressWarnings("rawtypes")
    Collection myRawC = Collections.singleton(10);
    PropertyArray jaRaw = new PropertyArray(myRawC);

    Collection<Integer> myCInt = Collections.singleton(10);
    PropertyArray jaInt = new PropertyArray(myCInt);

    Collection<Object> myCObj = Collections.singleton(10);
    PropertyArray jaObj = new PropertyArray(myCObj);

    assertTrue(expected.similar(jaRaw), "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(expected.similar(jaInt), "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(expected.similar(jaObj), "The RAW Collection should give me the same as the Typed Collection");
  }

  /**
   * Create a PropertyArray doc with a variety of different elements.
   * Confirm that the values can be accessed via the get[type]() API methods
   */
  @SuppressWarnings("boxing")
  @Test
  void getArrayValues() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    // booleans
    assertTrue(jsonArray.getBoolean(0), "Array true");
    assertFalse(jsonArray.getBoolean(1), "Array false");
    assertTrue(jsonArray.getBoolean(2), "Array string true");
    assertFalse(jsonArray.getBoolean(3), "Array string false");
    // strings
    assertEquals("hello", jsonArray.getString(4), "Array value string");
    // doubles
    assertEquals(23.45e-4, jsonArray.getNumber(5), "Array double");
    assertEquals(23.45, jsonArray.getNumber(6), "Array string double");
    // ints
    assertEquals(42, jsonArray.getInt(7), "Array value int");
    assertEquals(43, jsonArray.getInt(8), "Array value string int");
    // nested objects
    PropertyArray nestedJsonArray = jsonArray.getJSONArray(9);
    assertNotNull(nestedJsonArray, "Array value PropertyArray");
    PropertyObject nestedJsonObject = jsonArray.getJSONObject(10);
    assertNotNull(nestedJsonObject, "Array value JSONObject");
    // longs
    assertEquals(0, jsonArray.getNumber(11), "Array value long");
    assertEquals(-1, jsonArray.getNumber(12), "Array value string long");
  }

  /**
   * Create a PropertyArray doc with a variety of different elements.
   * Confirm that attempting to get the wrong types via the get[type]()
   * API methods result in JSONExceptions
   */
  @Test
  void failedGetArrayValues() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    try {
      jsonArray.getBoolean(4);
      fail("expected getBoolean to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[4] is not a boolean.", e.getMessage());
    }
    try {
      jsonArray.get(-1);
      fail("expected get to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[-1] not found.", e.getMessage());
    }
    try {
      jsonArray.getNumber(4);
      fail("expected getDouble to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[4] is not a number.", e.getMessage());
    }
    try {
      jsonArray.getInt(4);
      fail("expected getInt to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a number.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getJSONArray(4);
      fail("expected getJSONArray to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a JSONArray.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getJSONObject(4);
      fail("expected getJSONObject to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a JSONObject.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getNumber(4);
      fail("expected getLong to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a number.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getString(5);
      fail("expected getString to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[5] not a string.", e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Confirm the PropertyArray.length() method
   */
  @Test
  void length() {
    assertEquals(0, new PropertyArray().length(), "expected empty PropertyArray length 0");
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    assertEquals(13, jsonArray.length(), "expected PropertyArray length 13. instead found " + jsonArray.length());
    PropertyArray nestedJsonArray = jsonArray.getJSONArray(9);
    assertEquals(1, nestedJsonArray.length(), "expected PropertyArray length 1");
  }

  /**
   * Create a PropertyArray doc with a variety of different elements.
   * Confirm that the values can be accessed via the opt[type](index)
   * and opt[type](index, default) API methods.
   */
  @Test
  void opt() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    assertSame(Boolean.TRUE, jsonArray.opt(0), "Array opt value true");
    assertNull(jsonArray.opt(-1), "Array opt value out of range");

    assertNull(jsonArray.opt(jsonArray.length()), "Array opt value out of range");

    assertEquals(Boolean.TRUE, jsonArray.optBoolean(0, false), "Array opt boolean");
    assertEquals(Boolean.FALSE, jsonArray.optBoolean(-1, Boolean.FALSE), "Array opt boolean default");
    assertEquals(Boolean.FALSE, jsonArray.optBoolean(-1, false), "Array opt boolean implicit default");

    assertEquals("hello", jsonArray.optString(4), "Array opt string");
    assertEquals("", jsonArray.optString(-1), "Array opt string default implicit");
  }

  /**
   * Verifies that the opt methods properly convert string values.
   */
  @Test
  void optStringConversion() {
    PropertyArray ja = new PropertyArray("[\"123\",\"true\",\"false\"]");
    assertTrue(ja.optBoolean(1, false), "unexpected optBoolean value");
    assertFalse(ja.optBoolean(2, true), "unexpected optBoolean value");
  }

  /**
   * Exercise the PropertyArray.remove(index) method
   * and confirm the resulting PropertyArray.
   */
  @Test
  void remove() {
    String arrayStr1 =
      "[" +
        "1" +
        "]";
    PropertyArray jsonArray = new PropertyArray(arrayStr1);
    jsonArray.remove(0);
    assertNull(jsonArray.remove(5), "array should be empty");
    assertTrue(jsonArray.isEmpty(), "jsonArray should be empty");
  }

  /**
   * Exercise the PropertyArray.similar() method with various parameters
   * and confirm the results when not similar.
   */
  @Test
  void notSimilar() {
    String arrayStr1 =
      "[" +
        "1" +
        "]";
    PropertyArray jsonArray = new PropertyArray(arrayStr1);
    PropertyArray otherJsonArray = new PropertyArray();
    assertFalse(jsonArray.similar(otherJsonArray), "arrays lengths differ");

    PropertyArray nestedJsonArray = new PropertyArray("[1, 2]");
    PropertyArray otherNestedJsonArray = new PropertyArray();
    jsonArray = new PropertyArray();
    jsonArray.put(nestedJsonArray);
    otherJsonArray = new PropertyArray();
    otherJsonArray.put(otherNestedJsonArray);
    assertFalse(jsonArray.similar(otherJsonArray), "arrays nested JSONArrays differ");

    jsonArray = new PropertyArray();
    jsonArray.put("hello");
    otherJsonArray = new PropertyArray();
    otherJsonArray.put("world");
    assertFalse(jsonArray.similar(otherJsonArray), "arrays values differ");
  }

  /**
   * Convert an empty PropertyArray to JSONObject
   */
  @Test
  void toJSONObject() {
    PropertyArray names = new PropertyArray();
    PropertyArray jsonArray = new PropertyArray();
    assertNull(jsonArray.toJSONObject(names), "toJSONObject should return null");
  }


  /**
   * Exercise the PropertyArray iterator.
   */
  @SuppressWarnings("boxing")
  @Test
  void iterator() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    Iterator<Object> it = jsonArray.iterator();
    assertEquals(Boolean.TRUE, it.next(), "Array true");
    assertEquals(Boolean.FALSE, it.next(), "Array false");
    assertEquals("true", it.next(), "Array string true");
    assertEquals("false", it.next(), "Array string false");
    assertEquals("hello", it.next(), "Array string");

    assertEquals(23.45e-4, it.next(), "Array double");
    assertEquals(23.45, Double.parseDouble((String) it.next()), 0.01, "Array string double");

    assertEquals(42, it.next(), "Array value int");
    assertEquals(43, Integer.parseInt((String) it.next()), "Array value string int");

    PropertyArray nestedJsonArray = (PropertyArray) it.next();
    assertNotNull(nestedJsonArray, "Array value PropertyArray");

    PropertyObject nestedJsonObject = (PropertyObject) it.next();
    assertNotNull(nestedJsonObject, "Array value JSONObject");

    assertEquals(0, ((Number) it.next()).longValue(), "Array value long");
    assertEquals(-1, Long.parseLong((String) it.next()), "Array value string long");
    assertFalse(it.hasNext(), "should be at end of array");
  }


  /**
   * Exercise PropertyArray toString() method with various indent levels.
   */
  @Test
  void toList() {
    String jsonArrayStr =
      "[" +
        "[1,2," +
        "{\"key3\":true}" +
        "]," +
        "{\"key1\":\"val1\",\"key2\":" +
        "{\"key2\":null}," +
        "\"key3\":42,\"key4\":[]" +
        "}," +
        "[" +
        "[\"value1\",2.1]" +
        "," +
        "[null]" +
        "]" +
        "]";

    PropertyArray jsonArray = new PropertyArray(jsonArrayStr);
    List<?> list = jsonArray.toList();

    assertNotNull(list, "List should not be null");
    assertEquals(3, list.size(), "List should have 3 elements");

    List<?> val1List = (List<?>) list.get(0);
    assertNotNull(val1List, "val1 should not be null");
    assertEquals(3, val1List.size(), "val1 should have 3 elements");

    assertEquals(val1List.get(0), 1, "val1 value 1 should be 1");
    assertEquals(val1List.get(1), 2, "val1 value 2 should be 2");

    Map<?, ?> key1Value3Map = (Map<?, ?>) val1List.get(2);
    assertNotNull(key1Value3Map, "Map should not be null");
    assertEquals(1, key1Value3Map.size(), "Map should have 1 element");
    assertEquals(key1Value3Map.get("key3"), Boolean.TRUE, "Map key3 should be true");

    Map<?, ?> val2Map = (Map<?, ?>) list.get(1);
    assertNotNull(val2Map, "val2 should not be null");
    assertEquals(4, val2Map.size(), "val2 should have 4 elements");
    assertEquals("val1", val2Map.get("key1"), "val2 map key 1 should be val1");
    assertEquals(val2Map.get("key3"), 42, "val2 map key 3 should be 42");

    Map<?, ?> val2Key2Map = (Map<?, ?>) val2Map.get("key2");
    assertNotNull(val2Key2Map, "val2 map key 2 should not be null");
    assertTrue(val2Key2Map.containsKey("key2"), "val2 map key 2 should have an entry");
    assertNull(val2Key2Map.get("key2"), "val2 map key 2 value should be null");

    List<?> val2Key4List = (List<?>) val2Map.get("key4");
    assertNotNull(val2Key4List, "val2 map key 4 should not be null");
    assertTrue(val2Key4List.isEmpty(), "val2 map key 4 should be empty");

    List<?> val3List = (List<?>) list.get(2);
    assertNotNull(val3List, "val3 should not be null");
    assertEquals(2, val3List.size(), "val3 should have 2 elements");

    List<?> val3Val1List = (List<?>) val3List.get(0);
    assertNotNull(val3Val1List, "val3 list val 1 should not be null");
    assertEquals(2, val3Val1List.size(), "val3 list val 1 should have 2 elements");
    assertEquals("value1", val3Val1List.get(0), "val3 list val 1 list element 1 should be value1");
    assertEquals(val3Val1List.get(1), Double.valueOf("2.1"), "val3 list val 1 list element 2 should be 2.1");

    List<?> val3Val2List = (List<?>) val3List.get(1);
    assertNotNull(val3Val2List, "val3 list val 2 should not be null");
    assertEquals(1, val3Val2List.size(), "val3 list val 2 should have 1 element");
    assertNull(val3Val2List.get(0), "val3 list val 2 list element 1 should be null");

    // assert that toList() is a deep copy
    jsonArray.getJSONObject(1).put("key1", "still val1");
    assertEquals("val1", val2Map.get("key1"), "val2 map key 1 should be val1");

    // assert that the new list is mutable
    assertNotNull(list.remove(2), "Removing an entry should succeed");
    assertEquals(2, list.size(), "List should have 2 elements");
  }

  @Test
  void canBeCreatedWithNullCollection() {
    Collection nullCollection = null;
    PropertyArray arr = new PropertyArray(nullCollection);
    assertTrue(arr.isEmpty());
  }

  @Test
  void propertyArrayCanBeCreatedWithArray() {
    String[] arr = {"abc", "def"};
    PropertyArray propertyArray = new PropertyArray(arr);
    assertEquals("def", propertyArray.getString(1));
  }

  @Test
  void exceptionWhileAdding_NegativeIndex() {
    PropertyArray arr = new PropertyArray();
    assertThrows(PropertyException.class, () -> arr.put(-1, ""));
  }

  @Test
  void simplePut_whenIndexAndLengthAreEqual() {
    String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    PropertyArray propertyArray = new PropertyArray(arr);
    propertyArray.put(9, "10");
    assertEquals("10", propertyArray.getString(9));
  }

  @Test
  void canDirectlyPutToIndex_whichIsMoreThanDefaultIndex() {
    PropertyArray propertyArray = new PropertyArray();
    propertyArray.put(10, "1");
    assertEquals("1", propertyArray.getString(10));
  }

  @Test
  void similarWillOnlyConsiderTypePropertyArray() {
    String[] arr = {};
    PropertyArray propertyArray = new PropertyArray();
    assertFalse(propertyArray.similar(arr));
  }

  @Test
  void nullAndEmptyAreNotSimilar() {
    PropertyArray nullArr = new PropertyArray();
    nullArr.put(null);
    PropertyArray empty = new PropertyArray();
    empty.put("");
    assertFalse(nullArr.similar(empty));
  }

  @Test
  void whileComparingPropertyArrayDelegatesCallToPropertyObject() {
    PropertyArray arrWithObject = new PropertyArray();
    arrWithObject.put(new PropertyObject("{}"));
    PropertyArray empty = new PropertyArray();
    empty.put("");
    assertFalse(arrWithObject.similar(empty));
  }

  @Test
  void fromPropertyArray_canCreatePropertyObject() {
    String[] arr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    PropertyArray propertyArray = new PropertyArray(arr);
    PropertyObject pObj = propertyArray.toJSONObject(new PropertyArray(new String[]{"1"}));
    assertEquals("1", pObj.getString("1"));
  }

  @Test
  void propertyArray_canBeCreatedFromComplexTokenizer() {
    PropertyTokener pt = new PropertyTokener("[b,c,d],1,2.3,[,]");
    PropertyArray propertyArray = new PropertyArray(pt);
    assertEquals("c", propertyArray.getString(1));
  }

  @Test
  void propertyArray_TokenizerWillThrowSyntaxErrorIfBracketMissing() {
    PropertyTokener pt = new PropertyTokener("[b,c,d");
    PropertyException e = assertThrows(PropertyException.class, () ->new PropertyArray(pt));
    assertEquals("Expected a ',' or ']' at 6 [character 7 line 1]", e.getMessage());
  }

  @Test
  void propertyArray_TokenizerWillThrowSyntaxErrorIfOnlyBracket() {
    PropertyTokener pt = new PropertyTokener("]");
    PropertyException e = assertThrows(PropertyException.class, () ->new PropertyArray(pt));
    assertEquals("A JSONArray text must start with '[' at 1 [character 2 line 1]", e.getMessage());
  }

  @Test
  void propertyArray_TokenizerWillAddNullObject_stringWith2Commas() {
    PropertyTokener pt = new PropertyTokener("[,]");
    PropertyArray propertyArray = new PropertyArray(pt);
    assertEquals(PropertyObject.NULL, propertyArray.get(0));
  }

  @Test
  void propertyArray_TokenizerWillThrowSyntaxError_specialCharacter_defaultCase() {
    PropertyTokener pt = new PropertyTokener("[,']'[");
    PropertyException e = assertThrows(PropertyException.class, () ->new PropertyArray(pt));
    assertEquals("Expected a ',' or ']' at 6 [character 7 line 1]", e.getMessage());
  }
}