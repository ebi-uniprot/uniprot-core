package org.uniprot.core.util.property;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PropertyArrayTest {

  private PropertyArray arr = null;

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
      .put(new String(string1))
      .put(2);

    assertFalse(obj1.similar(obj2), "Should eval to false");

    assertTrue(obj1.similar(obj3), "Should eval to true");
  }

  /**
   * Attempt to create a PropertyArray with a null string.
   * Expects a NullPointerException.
   */
  void nullException() {
    String str = null;
    assertThrows(NullPointerException.class, () -> new PropertyArray(str), "Should throw an exception");
  }

  /**
   * Attempt to create a PropertyArray with an empty string.
   * Expects a PropertyException.
   */
  @Test
  void emptStr() {
    String str = "";
    try {
      assertNull(new PropertyArray(str), "Should throw an exception");
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
      assertNull(new PropertyArray("["), "Should throw an exception");
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
      assertNull(new PropertyArray("[\"test\""), "Should throw an exception");
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
      assertNull(new PropertyArray("[\"test\","), "Should throw an exception");
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
      assertNull(new PropertyArray((Object) str), "Should throw an exception");
    } catch (PropertyException e) {
      assertTrue("JSONArray initial value should be a string or collection or array.".
        equals(e.getMessage()), "Expected an exception message");
    }
  }

  /**
   * Verifies that the constructor has backwards compatibility with RAW types pre-java5.
   */
  @Test
  void verifyConstructor() {

    final PropertyArray expected = new PropertyArray("[10]");

    @SuppressWarnings("rawtypes")
    Collection myRawC = Collections.singleton(Integer.valueOf(10));
    PropertyArray jaRaw = new PropertyArray(myRawC);

    Collection<Integer> myCInt = Collections.singleton(Integer.valueOf(10));
    PropertyArray jaInt = new PropertyArray(myCInt);

    Collection<Object> myCObj = Collections.singleton((Object) Integer
      .valueOf(10));
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
  public void getArrayValues() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    // booleans
    assertTrue(true == jsonArray.getBoolean(0), "Array true");
    assertTrue(false == jsonArray.getBoolean(1), "Array false");
    assertTrue(true == jsonArray.getBoolean(2), "Array string true");
    assertTrue(false == jsonArray.getBoolean(3), "Array string false");
    // strings
    assertTrue("hello".equals(jsonArray.getString(4)), "Array value string");
    // doubles
    assertTrue(new Double(23.45e-4).equals(jsonArray.getNumber(5)), "Array double");
    assertTrue(new Double(23.45).equals(jsonArray.getNumber(6)), "Array string double");
    // ints
    assertTrue(Integer.valueOf(42).equals(jsonArray.getInt(7)), "Array value int");
    assertTrue(Integer.valueOf(43).equals(jsonArray.getInt(8)), "Array value string int");
    // nested objects
    PropertyArray nestedJsonArray = jsonArray.getJSONArray(9);
    assertTrue(nestedJsonArray != null, "Array value PropertyArray");
    PropertyObject nestedJsonObject = jsonArray.getJSONObject(10);
    assertTrue(nestedJsonObject != null, "Array value JSONObject");
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
  public void failedGetArrayValues() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    try {
      jsonArray.getBoolean(4);
      assertTrue(false, "expected getBoolean to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[4] is not a boolean.", e.getMessage());
    }
    try {
      jsonArray.get(-1);
      assertTrue(false, "expected get to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[-1] not found.", e.getMessage());
    }
    try {
      jsonArray.getNumber(4);
      assertTrue(false, "expected getDouble to fail");
    } catch (PropertyException e) {
      assertEquals(
        "JSONArray[4] is not a number.", e.getMessage());
    }
    try {
      jsonArray.getInt(4);
      assertTrue(false, "expected getInt to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a number.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getJSONArray(4);
      assertTrue(false, "expected getJSONArray to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a JSONArray.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getJSONObject(4);
      assertTrue(false, "expected getJSONObject to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a JSONObject.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getNumber(4);
      assertTrue(false, "expected getLong to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[4] is not a number.", e.getMessage(), "Expected an exception message");
    }
    try {
      jsonArray.getString(5);
      assertTrue(false, "expected getString to fail");
    } catch (PropertyException e) {
      assertEquals("JSONArray[5] not a string.", e.getMessage(), "Expected an exception message");
    }
  }

  /**
   * Confirm the PropertyArray.length() method
   */
  @Test
  void length() {
    assertTrue(
      new PropertyArray().length() == 0, "expected empty PropertyArray length 0");
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    assertTrue(jsonArray.length() == 13, "expected PropertyArray length 13. instead found " + jsonArray.length());
    PropertyArray nestedJsonArray = jsonArray.getJSONArray(9);
    assertTrue(nestedJsonArray.length() == 1, "expected PropertyArray length 1");
  }

  /**
   * Create a PropertyArray doc with a variety of different elements.
   * Confirm that the values can be accessed via the opt[type](index)
   * and opt[type](index, default) API methods.
   */
  @SuppressWarnings("boxing")
  @Test
  public void opt() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    assertTrue(
      Boolean.TRUE == jsonArray.opt(0), "Array opt value true");
    assertTrue(
      null == jsonArray.opt(-1), "Array opt value out of range");

    assertTrue(
      null == jsonArray.opt(jsonArray.length()), "Array opt value out of range");

    assertTrue(
      Boolean.TRUE == jsonArray.optBoolean(0, false), "Array opt boolean");
    assertTrue(
      Boolean.FALSE == jsonArray.optBoolean(-1, Boolean.FALSE), "Array opt boolean default");
    assertTrue(
      Boolean.FALSE == jsonArray.optBoolean(-1, false), "Array opt boolean implicit default");

    assertTrue(
      "hello".equals(jsonArray.optString(4)), "Array opt string");
    assertTrue(
      "".equals(jsonArray.optString(-1)), "Array opt string default implicit");
  }

  /**
   * Verifies that the opt methods properly convert string values.
   */
  @Test
  public void optStringConversion() {
    PropertyArray ja = new PropertyArray("[\"123\",\"true\",\"false\"]");
    assertTrue(ja.optBoolean(1, false) == true, "unexpected optBoolean value");
    assertTrue(ja.optBoolean(2, true) == false, "unexpected optBoolean value");
  }

  /**
   * Exercise the PropertyArray.remove(index) method
   * and confirm the resulting PropertyArray.
   */
  @Test
  public void remove() {
    String arrayStr1 =
      "[" +
        "1" +
        "]";
    PropertyArray jsonArray = new PropertyArray(arrayStr1);
    jsonArray.remove(0);
    assertTrue(null == jsonArray.remove(5), "array should be empty");
    assertTrue(jsonArray.isEmpty(), "jsonArray should be empty");
  }

  /**
   * Exercise the PropertyArray.similar() method with various parameters
   * and confirm the results when not similar.
   */
  @Test
  public void notSimilar() {
    String arrayStr1 =
      "[" +
        "1" +
        "]";
    PropertyArray jsonArray = new PropertyArray(arrayStr1);
    PropertyArray otherJsonArray = new PropertyArray();
    assertTrue(!jsonArray.similar(otherJsonArray), "arrays lengths differ");

    PropertyArray nestedJsonArray = new PropertyArray("[1, 2]");
    PropertyArray otherNestedJsonArray = new PropertyArray();
    jsonArray = new PropertyArray();
    jsonArray.put(nestedJsonArray);
    otherJsonArray = new PropertyArray();
    otherJsonArray.put(otherNestedJsonArray);
    assertTrue(
      !jsonArray.similar(otherJsonArray), "arrays nested JSONArrays differ");

    jsonArray = new PropertyArray();
    jsonArray.put("hello");
    otherJsonArray = new PropertyArray();
    otherJsonArray.put("world");
    assertTrue(
      !jsonArray.similar(otherJsonArray), "arrays values differ");
  }

  /**
   * Convert an empty PropertyArray to JSONObject
   */
  @Test
  public void toJSONObject() {
    PropertyArray names = new PropertyArray();
    PropertyArray jsonArray = new PropertyArray();
    assertTrue(
      null == jsonArray.toJSONObject(names), "toJSONObject should return null");
  }


  /**
   * Exercise the PropertyArray iterator.
   */
  @SuppressWarnings("boxing")
  @Test
  public void iterator() {
    PropertyArray jsonArray = new PropertyArray(this.arrayStr);
    Iterator<Object> it = jsonArray.iterator();
    assertTrue(
      Boolean.TRUE.equals(it.next()), "Array true");
    assertTrue(
      Boolean.FALSE.equals(it.next()), "Array false");
    assertTrue(
      "true".equals(it.next()), "Array string true");
    assertTrue(
      "false".equals(it.next()), "Array string false");
    assertTrue(
      "hello".equals(it.next()), "Array string");

    assertTrue(
      new Double(23.45e-4).equals(it.next()), "Array double");
    assertTrue(
      new Double(23.45).equals(Double.parseDouble((String) it.next())), "Array string double");

    assertTrue(
      new Integer(42).equals(it.next()), "Array value int");
    assertTrue(
      new Integer(43).equals(Integer.parseInt((String) it.next())), "Array value string int");

    PropertyArray nestedJsonArray = (PropertyArray) it.next();
    assertTrue(nestedJsonArray != null, "Array value PropertyArray");

    PropertyObject nestedJsonObject = (PropertyObject) it.next();
    assertTrue(nestedJsonObject != null, "Array value JSONObject");

    assertTrue(
      new Long(0).equals(((Number) it.next()).longValue()), "Array value long");
    assertTrue(
      new Long(-1).equals(Long.parseLong((String) it.next())), "Array value string long");
    assertTrue(!it.hasNext(), "should be at end of array");
  }


  /**
   * Exercise PropertyArray toString() method with various indent levels.
   */
  @Test
  public void toList() {
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

    assertTrue(list != null, "List should not be null");
    assertTrue(list.size() == 3, "List should have 3 elements");

    List<?> val1List = (List<?>) list.get(0);
    assertTrue(val1List != null, "val1 should not be null");
    assertTrue(val1List.size() == 3, "val1 should have 3 elements");

    assertTrue(val1List.get(0).equals(Integer.valueOf(1)), "val1 value 1 should be 1");
    assertTrue(val1List.get(1).equals(Integer.valueOf(2)), "val1 value 2 should be 2");

    Map<?, ?> key1Value3Map = (Map<?, ?>) val1List.get(2);
    assertTrue(key1Value3Map != null, "Map should not be null");
    assertTrue(key1Value3Map.size() == 1, "Map should have 1 element");
    assertTrue(key1Value3Map.get("key3").equals(Boolean.TRUE), "Map key3 should be true");

    Map<?, ?> val2Map = (Map<?, ?>) list.get(1);
    assertTrue(val2Map != null, "val2 should not be null");
    assertTrue(val2Map.size() == 4, "val2 should have 4 elements");
    assertTrue(val2Map.get("key1").equals("val1"), "val2 map key 1 should be val1");
    assertTrue(val2Map.get("key3").equals(Integer.valueOf(42)), "val2 map key 3 should be 42");

    Map<?, ?> val2Key2Map = (Map<?, ?>) val2Map.get("key2");
    assertTrue(val2Key2Map != null, "val2 map key 2 should not be null");
    assertTrue(val2Key2Map.containsKey("key2"), "val2 map key 2 should have an entry");
    assertTrue(val2Key2Map.get("key2") == null, "val2 map key 2 value should be null");

    List<?> val2Key4List = (List<?>) val2Map.get("key4");
    assertTrue(val2Key4List != null, "val2 map key 4 should not be null");
    assertTrue(val2Key4List.isEmpty(), "val2 map key 4 should be empty");

    List<?> val3List = (List<?>) list.get(2);
    assertTrue(val3List != null, "val3 should not be null");
    assertTrue(val3List.size() == 2, "val3 should have 2 elements");

    List<?> val3Val1List = (List<?>) val3List.get(0);
    assertTrue(val3Val1List != null, "val3 list val 1 should not be null");
    assertTrue(val3Val1List.size() == 2, "val3 list val 1 should have 2 elements");
    assertTrue(val3Val1List.get(0).equals("value1"), "val3 list val 1 list element 1 should be value1");
    assertTrue(val3Val1List.get(1).equals(Double.valueOf("2.1")), "val3 list val 1 list element 2 should be 2.1");

    List<?> val3Val2List = (List<?>) val3List.get(1);
    assertTrue(val3Val2List != null, "val3 list val 2 should not be null");
    assertTrue(val3Val2List.size() == 1, "val3 list val 2 should have 1 element");
    assertTrue(val3Val2List.get(0) == null, "val3 list val 2 list element 1 should be null");

    // assert that toList() is a deep copy
    jsonArray.getJSONObject(1).put("key1", "still val1");
    assertTrue(val2Map.get("key1").equals("val1"), "val2 map key 1 should be val1");

    // assert that the new list is mutable
    assertTrue(list.remove(2) != null, "Removing an entry should succeed");
    assertTrue(list.size() == 2, "List should have 2 elements");
  }
}