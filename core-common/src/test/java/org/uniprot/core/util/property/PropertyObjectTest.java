package org.uniprot.core.util.property;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PropertyObjectTest {

  /**
   *  Regular Expression Pattern that matches JSON Numbers. This is primarily used for
   *  output to guarantee that we are always writing valid JSON.
   */
  static final Pattern NUMBER_PATTERN = Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

  /**
   * Tests that the similar method is working as expected.
   */
  @Test
  void verifySimilar() {
    final String string1 = "HasSameRef";
    PropertyObject obj1 = new PropertyObject()
      .put("key1", "abc")
      .put("key2", 2)
      .put("key3", string1);

    PropertyObject obj2 = new PropertyObject()
      .put("key1", "abc")
      .put("key2", 3)
      .put("key3", string1);

    PropertyObject obj3 = new PropertyObject()
      .put("key1", "abc")
      .put("key2", 2)
      .put("key3", new String(string1));

    assertFalse(obj1.similar(obj2), "Should eval to false");

    assertTrue(obj1.similar(obj3), "Should eval to true");

  }

  @Test
  void timeNumberParsing() {
    // test data to use
    final String[] testData = new String[] {
      null,
      "",
      "100",
      "-100",
      "abc123",
      "012345",
      "100.5e199",
      "-100.5e199",
      "DEADBEEF",
      "0xDEADBEEF",
      "1234567890.1234567890",
      "-1234567890.1234567890",
      "adloghakuidghauiehgauioehgdkjfb nsruoh aeu noerty384 nkljfgh "
        + "395h tdfn kdz8yt3 4hkls gn.ey85 4hzfhnz.o8y5a84 onvklt "
        + "yh389thub nkz8y49lihv al4itlaithknty8hnbl"
      // long (in length) number sequences with invalid data at the end of the
      // string offer very poor performance for the REGEX.
      ,"123467890123467890123467890123467890123467890123467890123467"
      + "8901234678901234678901234678901234678901234678901234678"
      + "9012346789012346789012346789012346789012346789012346789"
      + "0a"
    };
    final int testDataLength = testData.length;
    /**
     * Changed to 1000 for faster test runs
     */
    // final int iterations = 1000000;
    final int iterations = 1000;

    // 10 million iterations 1,000,000 * 10 (currently 100,000)
    long startTime = System.nanoTime();
    for(int i = 0; i < iterations; i++) {
      for(int j = 0; j < testDataLength; j++) {
        try {
          BigDecimal v1 = new BigDecimal(testData[j]);
          v1.signum();
        } catch(Exception ignore) {
          //do nothing
        }
      }
    }
    final long elapsedNano1 = System.nanoTime() - startTime;
    System.out.println("new BigDecimal(testData[]) : " + elapsedNano1 / 1000000 + " ms");

    startTime = System.nanoTime();
    for(int i = 0; i < iterations; i++) {
      for(int j = 0; j < testDataLength; j++) {
        try {
          boolean v2 = NUMBER_PATTERN.matcher(testData[j]).matches();
          assert v2 == !!v2;
        } catch(Exception ignore) {
          //do nothing
        }
      }
    }
    final long elapsedNano2 = System.nanoTime() - startTime;
    System.out.println("NUMBER_PATTERN.matcher(testData[]).matches() : " + elapsedNano2 / 1000000 + " ms");
    // don't assert normally as the testing is machine dependent.
    // assertTrue("Expected Pattern matching to be faster than BigDecimal constructor",elapsedNano2<elapsedNano1);
  }

  /**
   * PropertyObject built from a bean, but only using a null value.
   * Nothing good is expected to happen.
   * Expects NullPointerException
   */
  @Test
  void jsonObjectByNullBean() {
    assertThrows(NullPointerException.class, ()-> new PropertyObject((String) null));
  }

  /**
   * The JSON parser is permissive of unambiguous unquoted keys and values.
   * Such JSON text should be allowed, even if it does not strictly conform
   * to the spec. However, after being parsed, toString() should emit strictly
   * conforming JSON text.
   */
  @Test
  void unquotedText() {
    String str = "{key1:value1, key2:42}";
    PropertyObject jsonObject = new PropertyObject(str);
    String textStr = jsonObject.toString();
    assertTrue(jsonObject.keySet().contains("key1"), "expected key1");
    assertEquals("value1", jsonObject.get("key1"), "expected value1");
    assertTrue(jsonObject.keySet().contains("key2"), "expected key2");
    assertEquals(42, jsonObject.get("key2"), "expected 42");
  }

  @Test
  void testLongFromString(){
    String str = "26315000000253009";
    PropertyObject json = new PropertyObject();
    json.put("key", str);

    final Object actualKey = json.opt("key");
    assert str.equals(actualKey) : "Incorrect key value. Got " + actualKey
      + " expected " + str;

    final long actualLong = json.optLong("key", 0L);
    assert actualLong != 0 : "Unable to extract long value for string " + str;
    assert 26315000000253009L == actualLong : "Incorrect key value. Got "
      + actualLong + " expected " + str;

    final String actualString = json.optString("key", "");
    assert str.equals(actualString) : "Incorrect key value. Got "
      + actualString + " expected " + str;
  }

  /**
   * A PropertyObject can be created with no content
   */
  @Test
  void emptyJsonObject() {
    PropertyObject jsonObject = new PropertyObject();
    assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
  }

  /**
   * JSONObjects can be built from a Map<String, Object>.
   * In this test the map is null.
   * the PropertyObject(JsonTokener) ctor is not tested directly since it already
   * has full coverage from other tests.
   */
  @Test
  void jsonObjectByNullMap() {
    Map<String, Object> map = null;
    PropertyObject jsonObject = new PropertyObject(map);
    assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
  }

  /**
   * Verifies that the put Collection has backwards compatibility with RAW types pre-java5.
   */
  @Test
  void verifyPutCollection() {

    final PropertyObject expected = new PropertyObject("{\"myCollection\":[10]}");

    @SuppressWarnings("rawtypes")
    Collection myRawC = Collections.singleton(Integer.valueOf(10));
    PropertyObject jaRaw = new PropertyObject();
    jaRaw.put("myCollection", myRawC);

    Collection<Object> myCObj = Collections.singleton((Object) Integer
      .valueOf(10));
    PropertyObject jaObj = new PropertyObject();
    jaObj.put("myCollection", myCObj);

    Collection<Integer> myCInt = Collections.singleton(Integer
      .valueOf(10));
    PropertyObject jaInt = new PropertyObject();
    jaInt.put("myCollection", myCInt);

    assertTrue(
      expected.similar(jaRaw),
      "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(
      expected.similar(jaObj),
      "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(
      expected.similar(jaInt),
      "The RAW Collection should give me the same as the Typed Collection");
  }


  /**
   * Verifies that the put Map has backwards compatibility with RAW types pre-java5.
   */
  @Test
  void verifyPutMap() {

    final PropertyObject expected = new PropertyObject("{\"myMap\":{\"myKey\":10}}");

    @SuppressWarnings("rawtypes")
    Map myRawC = Collections.singletonMap("myKey", Integer.valueOf(10));
    PropertyObject jaRaw = new PropertyObject();
    jaRaw.put("myMap", myRawC);

    Map<String, Object> myCStrObj = Collections.singletonMap("myKey",
      (Object) Integer.valueOf(10));
    PropertyObject jaStrObj = new PropertyObject();
    jaStrObj.put("myMap", myCStrObj);

    Map<String, Integer> myCStrInt = Collections.singletonMap("myKey",
      Integer.valueOf(10));
    PropertyObject jaStrInt = new PropertyObject();
    jaStrInt.put("myMap", myCStrInt);

    Map<?, ?> myCObjObj = Collections.singletonMap((Object) "myKey",
      (Object) Integer.valueOf(10));
    PropertyObject jaObjObj = new PropertyObject();
    jaObjObj.put("myMap", myCObjObj);

    assertTrue(
      expected.similar(jaRaw),
      "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(
      expected.similar(jaStrObj),
      "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(
      expected.similar(jaStrInt),
      "The RAW Collection should give me the same as the Typed Collection");
    assertTrue(
      expected.similar(jaObjObj),
      "The RAW Collection should give me the same as the Typed Collection");
  }

  /**
   * Exercise some PropertyObject get[type] and opt[type] methods
   */
  @Test
  void jsonObjectValues() {
    String str =
      "{"+
        "\"trueKey\":true,"+
        "\"falseKey\":false,"+
        "\"trueStrKey\":\"true\","+
        "\"falseStrKey\":\"false\","+
        "\"stringKey\":\"hello world!\","+
        "\"intKey\":42,"+
        "\"intStrKey\":\"43\","+
        "\"longKey\":1234567890123456789,"+
        "\"longStrKey\":\"987654321098765432\","+
        "\"doubleKey\":-23.45e7,"+
        "\"doubleStrKey\":\"00001.000\","+
        "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","+
        "\"negZeroKey\":-0.0,"+
        "\"negZeroStrKey\":\"-0.0\","+
        "\"arrayKey\":[0,1,2],"+
        "\"objectKey\":{\"myKey\":\"myVal\"}"+
        "}";
    PropertyObject jsonObject = new PropertyObject(str);
    assertTrue(jsonObject.getString("stringKey").equals("hello world!"),
      "stringKey should be string");
    assertTrue(jsonObject.opt("negZeroKey") instanceof Double,
      "opt negZeroKey should be a Double");
    assertTrue(jsonObject.get("negZeroKey") instanceof Double,
      "get negZeroKey should be a Double");
    assertTrue(jsonObject.optNumber("negZeroKey") instanceof Double,
      "optNumber negZeroKey should return Double");
    assertTrue(jsonObject.optNumber("negZeroStrKey") instanceof Double,
      "optNumber negZeroStrKey should return Double");
    assertTrue(Double.compare(jsonObject.optNumber("negZeroKey").doubleValue(), -0.0d) == 0,
      "optNumber negZeroKey should be -0.0");
    assertTrue(Double.compare(jsonObject.optNumber("negZeroStrKey").doubleValue(), -0.0d) == 0,
      "optNumber negZeroStrKey should be -0.0");
    assertTrue(jsonObject.optLong("longKey", 0L) == 1234567890123456789L,
      "opt longKey should be long");
    assertTrue(jsonObject.optLong("longKey", 0) == 1234567890123456789L,
      "opt longKey with default should be long");
    assertTrue(jsonObject.optNumber("intKey") instanceof Integer,
      "optNumber int should return Integer");
    assertTrue(jsonObject.optNumber("longKey") instanceof Long,
      "optNumber long should return Long");
    assertTrue(jsonObject.optNumber("doubleKey") instanceof Double,
      "optNumber double should return Double");
    assertTrue(jsonObject.optNumber("intStrKey") instanceof Integer,
      "optNumber Str int should return Integer");
    assertTrue(jsonObject.optNumber("longStrKey") instanceof Long,
      "optNumber Str long should return Long");
    assertTrue(jsonObject.optNumber("doubleStrKey") instanceof Double,
      "optNumber Str double should return Double");
    assertTrue(jsonObject.optNumber("BigDecimalStrKey") instanceof BigDecimal,
      "optNumber BigDecimalStrKey should return BigDecimal");
    assertTrue(jsonObject.isNull("xKey"),
      "xKey should not exist");
    assertTrue(jsonObject.has("stringKey"),
      "stringKey should exist");
    assertTrue(jsonObject.optString("stringKey", "").equals("hello world!"),
      "opt stringKey should string");
    assertTrue(jsonObject.optString("stringKey", "not found").equals("hello world!"),
      "opt stringKey with default should string");
    PropertyArray jsonArray = jsonObject.getJSONArray("arrayKey");
    assertTrue(jsonArray.getInt(0) == 0 &&
            jsonArray.getInt(1) == 1 &&
            jsonArray.getInt(2) == 2,
      "arrayKey should be JSONArray");
    jsonArray = jsonObject.optJSONArray("arrayKey");
    assertTrue(jsonArray.getInt(0) == 0 &&
            jsonArray.getInt(1) == 1 &&
            jsonArray.getInt(2) == 2,
      "opt arrayKey should be JSONArray");
    PropertyObject jsonObjectInner = jsonObject.getJSONObject("objectKey");
    assertTrue(jsonObjectInner.get("myKey").equals("myVal"),
      "objectKey should be PropertyObject");
  }

  /**
   * Check whether PropertyObject handles large or high precision numbers correctly
   */
  @Test
  void stringToValueNumbersTest() {
    assertTrue(PropertyObject.stringToValue("-0")  instanceof Double, "-0 Should be a Double!");
    assertTrue(PropertyObject.stringToValue("-0.0") instanceof Double, "-0.0 Should be a Double!");
    assertTrue(PropertyObject.stringToValue("-") instanceof String, "'-' Should be a String!");
    assertTrue(PropertyObject.stringToValue( "0.2" ) instanceof Double,
      "0.2 should be a Double!");
    assertTrue(PropertyObject.stringToValue( new Double( "0.2f" ).toString() ) instanceof Double,
      "Doubles should be Doubles, even when incorrectly converting floats!");
    /**
     * This test documents a need for BigDecimal conversion.
     */
    Object obj = PropertyObject.stringToValue( "299792.457999999984" );
    assertTrue(obj.equals(new Double(299792.458)),
      "evaluates to 299792.458 double instead of 299792.457999999984 BigDecimal!");
    assertTrue(PropertyObject.stringToValue( "1" ) instanceof Integer,
      "1 should be an Integer!");
    assertTrue(PropertyObject.stringToValue( new Integer( Integer.MAX_VALUE ).toString() ) instanceof Integer,
      "Integer.MAX_VALUE should still be an Integer!");
    assertTrue(PropertyObject.stringToValue( new Long( Long.sum( Integer.MAX_VALUE, 1 ) ).toString() ) instanceof Long,
      "Large integers should be a Long!");
    assertTrue(PropertyObject.stringToValue( new Long( Long.MAX_VALUE ).toString() ) instanceof Long,
      "Long.MAX_VALUE should still be an Integer!");

    String str = new BigInteger( new Long( Long.MAX_VALUE ).toString() ).add( BigInteger.ONE ).toString();
    assertTrue(PropertyObject.stringToValue(str).equals("9223372036854775808"),
      "Really large integers currently evaluate to string");
  }

  /**
   * This test documents numeric values which could be numerically
   * handled as BigDecimal or BigInteger. It helps determine what outputs
   * will change if those types are supported.
   */
  @Test
  void jsonValidNumberValuesNeitherLongNorIEEE754Compatible() {
    // Valid JSON Numbers, probably should return BigDecimal or BigInteger objects
    String str =
      "{"+
        "\"numberWithDecimals\":299792.457999999984,"+
        "\"largeNumber\":12345678901234567890,"+
        "\"preciseNumber\":0.2000000000000000111,"+
        "\"largeExponent\":-23.45e2327"+
        "}";
    PropertyObject jsonObject = new PropertyObject(str);
    // Comes back as a double, but loses precision
    assertEquals(jsonObject.get("numberWithDecimals"), new Double("299792.458"), "numberWithDecimals currently evaluates to double 299792.458");
    Object obj = jsonObject.get( "largeNumber" );
    assertTrue("12345678901234567890".equals(obj),
      "largeNumber currently evaluates to string");
    // comes back as a double but loses precision
    assertTrue(jsonObject.get( "preciseNumber" ).equals(new Double(0.2)),
      "preciseNumber currently evaluates to double 0.2");
    obj = jsonObject.get( "largeExponent" );
    assertTrue("-23.45e2327".equals(obj),
      "largeExponent should currently evaluates as a string");
  }

  /**
   * This test documents how JSON-Java handles invalid numeric input.
   */
  @Test
  void jsonInvalidNumberValues() {
    // Number-notations supported by Java and invalid as JSON
    String str =
      "{"+
        "\"hexNumber\":-0x123,"+
        "\"tooManyZeros\":00,"+
        "\"negativeInfinite\":-Infinity,"+
        "\"negativeNaN\":-NaN,"+
        "\"negativeFraction\":-.01,"+
        "\"tooManyZerosFraction\":00.001,"+
        "\"negativeHexFloat\":-0x1.fffp1,"+
        "\"hexFloat\":0x1.0P-1074,"+
        "\"floatIdentifier\":0.1f,"+
        "\"doubleIdentifier\":0.1d"+
        "}";
    PropertyObject jsonObject = new PropertyObject(str);
    Object obj;
    obj = jsonObject.get( "hexNumber" );
    assertFalse(obj instanceof Number,
      "hexNumber must not be a number (should throw exception!?)");
    assertTrue(obj.equals("-0x123"),
      "hexNumber currently evaluates to string");
    assertTrue(jsonObject.get( "tooManyZeros" ).equals("00"),
      "tooManyZeros currently evaluates to string");
    obj = jsonObject.get("negativeInfinite");
    assertTrue(obj.equals("-Infinity"),
      "negativeInfinite currently evaluates to string");
    obj = jsonObject.get("negativeNaN");
    assertTrue(obj.equals("-NaN"),
      "negativeNaN currently evaluates to string");
    assertTrue(jsonObject.get( "negativeFraction" ).equals(new Double(-0.01)),
      "negativeFraction currently evaluates to double -0.01");
    assertTrue(jsonObject.get( "tooManyZerosFraction" ).equals(new Double(0.001)),
      "tooManyZerosFraction currently evaluates to double 0.001");
    assertTrue(jsonObject.get( "negativeHexFloat" ).equals(new Double(-3.99951171875)),
      "negativeHexFloat currently evaluates to double -3.99951171875");
    assertTrue(jsonObject.get("hexFloat").equals(new Double(4.9E-324)),
      "hexFloat currently evaluates to double 4.9E-324");
    assertTrue(jsonObject.get("floatIdentifier").equals(new Double(0.1)),
      "floatIdentifier currently evaluates to double 0.1");
    assertTrue(jsonObject.get("doubleIdentifier").equals(new Double(0.1)),
      "doubleIdentifier currently evaluates to double 0.1");
  }

  /**
   * Tests how PropertyObject get[type] handles incorrect types
   */
  @Test
  void jsonObjectNonAndWrongValues() {
    String str =
      "{"+
        "\"trueKey\":true,"+
        "\"falseKey\":false,"+
        "\"trueStrKey\":\"true\","+
        "\"falseStrKey\":\"false\","+
        "\"stringKey\":\"hello world!\","+
        "\"intKey\":42,"+
        "\"intStrKey\":\"43\","+
        "\"longKey\":1234567890123456789,"+
        "\"longStrKey\":\"987654321098765432\","+
        "\"doubleKey\":-23.45e7,"+
        "\"doubleStrKey\":\"00001.000\","+
        "\"arrayKey\":[0,1,2],"+
        "\"objectKey\":{\"myKey\":\"myVal\"}"+
        "}";
    PropertyObject jsonObject = new PropertyObject(str);

    try {
      jsonObject.getString("nonKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"nonKey\"] not found.",
        e.getMessage());
    }
    try {
      jsonObject.getString("trueKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"trueKey\"] not a string.",
        e.getMessage());
    }

    try {
      jsonObject.getJSONArray("nonKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"nonKey\"] not found.",
        e.getMessage());
    }
    try {
      jsonObject.getJSONArray("stringKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"stringKey\"] is not a JSONArray.",
        e.getMessage());
    }
    try {
      jsonObject.getJSONObject("nonKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"nonKey\"] not found.",
        e.getMessage());
    }
    try {
      jsonObject.getJSONObject("stringKey");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "JSONObject[\"stringKey\"] is not a JSONObject.",
        e.getMessage());
    }
  }

  /**
   * Exercise the PropertyObject wrap() method. Sometimes wrap() will change
   * the object being wrapped, other times not. The purpose of wrap() is
   * to ensure the value is packaged in a way that is compatible with how
   * a PropertyObject value or PropertyArray value is supposed to be stored.
   */
  @Test
  void wrapObject() {
    // wrap(null) returns NULL
    assertTrue(PropertyObject.NULL == PropertyObject.wrap(null),
      "null wrap() incorrect");

    // wrap(Integer) returns Integer
    Integer in = new Integer(1);
    assertTrue(in == PropertyObject.wrap(in),
      "Integer wrap() incorrect");

    /**
     * This test is to document the preferred behavior if BigDecimal is
     * supported. Previously bd returned as a string, since it
     * is recognized as being a Java package class. Now with explicit
     * support for big numbers, it remains a BigDecimal
     */
    Object bdWrap = PropertyObject.wrap(BigDecimal.ONE);
    assertTrue(bdWrap.equals(BigDecimal.ONE),
      "BigDecimal.ONE evaluates to ONE");

    // wrap PropertyObject returns PropertyObject
    String jsonObjectStr =
      "{"+
        "\"key1\":\"val1\","+
        "\"key2\":\"val2\","+
        "\"key3\":\"val3\""+
        "}";
    PropertyObject jsonObject = new PropertyObject(jsonObjectStr);
    assertTrue(jsonObject == PropertyObject.wrap(jsonObject),
      "PropertyObject wrap() incorrect");
  }


  /**
   * RFC 7159 defines control characters to be U+0000 through U+001F. This test verifies that the parser is checking for these in expected ways.
   */
  @Test
  void jsonObjectParseControlCharacters(){
    for(int i = 0;i<=0x001f;i++){
      final String charString = String.valueOf((char)i);
      final String source = "{\"key\":\""+charString+"\"}";
      try {
        PropertyObject jo = new PropertyObject(source);
        assertTrue(charString.equals(jo.getString("key")), "Expected "+charString+"("+i+") in the JSON Object but did not find it.");
      } catch (PropertyException ex) {
        assertTrue(i=='\0' || i=='\n' || i=='\r',
          "Only \\0 (U+0000), \\n (U+000A), and \\r (U+000D) should cause an error. Instead "+charString+"("+i+") caused an error"
                );
      }
    }
  }

  /**
   * Explore how PropertyObject handles parsing errors.
   */
  @SuppressWarnings("boxing")
  @Test
  void jsonObjectParsingErrors() {
    try {
      // does not start with '{'
      String str = "abc";
      assertNull(new PropertyObject(str), "Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "A JSONObject text must begin with '{' at 1 [character 2 line 1]",
        e.getMessage());
    }
    try {
      // does not end with '}'
      String str = "{";
      assertNull(new PropertyObject(str), "Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "A JSONObject text must end with '}' at 1 [character 2 line 1]",
        e.getMessage());
    }
    try {
      // key with no ':'
      String str = "{\"myKey\" = true}";
      assertNull(new PropertyObject(str), "Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Expected a ':' after a key at 10 [character 11 line 1]",
        e.getMessage());
    }
    try {
      // entries with no ',' separator
      String str = "{\"myKey\":true \"myOtherKey\":false}";
      assertNull(new PropertyObject(str), "Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Expected a ',' or '}' at 15 [character 16 line 1]",
        e.getMessage());
    }
    try {
      // invalid key
      String str = "{\"myKey\":true, \"myOtherKey\":false}";
      PropertyObject jsonObject = new PropertyObject(str);
      jsonObject.get(null);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Null key.",
        e.getMessage());
    }
    try {
      // multiple putOnce key
      PropertyObject jsonObject = new PropertyObject("{}");
      jsonObject.putOnce("hello", "world");
      jsonObject.putOnce("hello", "world!");
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertTrue(true, "");
    }
    try {
      // test validity of invalid double
      PropertyObject.testValidity(Double.NaN);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertTrue(true, "");
    }
    try {
      // test validity of invalid float
      PropertyObject.testValidity(Float.NEGATIVE_INFINITY);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertTrue(true, "");
    }
    try {
      // test exception message when including a duplicate key (level 0)
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr03\":\"value-04\"\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr03\" at 90 [character 13 line 5]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key (level 0) holding an object
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr03\": {"
        +"        \"attr04-01\":\"value-04-01\",n"
        +"        \"attr04-02\":\"value-04-02\",n"
        +"        \"attr04-03\":\"value-04-03\"n"
        + "    }\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr03\" at 90 [character 13 line 5]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key (level 0) holding an array
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr03\": [\n"
        +"        {"
        +"            \"attr04-01\":\"value-04-01\",n"
        +"            \"attr04-02\":\"value-04-02\",n"
        +"            \"attr04-03\":\"value-04-03\"n"
        +"        }\n"
        + "    ]\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr03\" at 90 [character 13 line 5]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key (level 1)
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr04\": {\n"
        +"        \"attr04-01\":\"value04-01\",\n"
        +"        \"attr04-02\":\"value04-02\",\n"
        +"        \"attr04-03\":\"value04-03\",\n"
        +"        \"attr04-03\":\"value04-04\"\n"
        + "    }\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr04-03\" at 215 [character 20 line 9]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key (level 1) holding an object
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr04\": {\n"
        +"        \"attr04-01\":\"value04-01\",\n"
        +"        \"attr04-02\":\"value04-02\",\n"
        +"        \"attr04-03\":\"value04-03\",\n"
        +"        \"attr04-03\": {\n"
        +"            \"attr04-04-01\":\"value04-04-01\",\n"
        +"            \"attr04-04-02\":\"value04-04-02\",\n"
        +"            \"attr04-04-03\":\"value04-04-03\",\n"
        +"        }\n"
        +"    }\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr04-03\" at 215 [character 20 line 9]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key (level 1) holding an array
      String str = "{\n"
        +"    \"attr01\":\"value-01\",\n"
        +"    \"attr02\":\"value-02\",\n"
        +"    \"attr03\":\"value-03\",\n"
        +"    \"attr04\": {\n"
        +"        \"attr04-01\":\"value04-01\",\n"
        +"        \"attr04-02\":\"value04-02\",\n"
        +"        \"attr04-03\":\"value04-03\",\n"
        +"        \"attr04-03\": [\n"
        +"            {\n"
        +"                \"attr04-04-01\":\"value04-04-01\",\n"
        +"                \"attr04-04-02\":\"value04-04-02\",\n"
        +"                \"attr04-04-03\":\"value04-04-03\",\n"
        +"            }\n"
        +"        ]\n"
        +"    }\n"
        + "}";
      new PropertyObject(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr04-03\" at 215 [character 20 line 9]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key in object (level 0) within an array
      String str = "[\n"
        +"    {\n"
        +"        \"attr01\":\"value-01\",\n"
        +"        \"attr02\":\"value-02\"\n"
        +"    },\n"
        +"    {\n"
        +"        \"attr01\":\"value-01\",\n"
        +"        \"attr01\":\"value-02\"\n"
        +"    }\n"
        + "]";
      new PropertyArray(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr01\" at 124 [character 17 line 8]",
        e.getMessage());
    }
    try {
      // test exception message when including a duplicate key in object (level 1) within an array
      String str = "[\n"
        +"    {\n"
        +"        \"attr01\":\"value-01\",\n"
        +"        \"attr02\": {\n"
        +"            \"attr02-01\":\"value-02-01\",\n"
        +"            \"attr02-02\":\"value-02-02\"\n"
        +"        }\n"
        +"    },\n"
        +"    {\n"
        +"        \"attr01\":\"value-01\",\n"
        +"        \"attr02\": {\n"
        +"            \"attr02-01\":\"value-02-01\",\n"
        +"            \"attr02-01\":\"value-02-02\"\n"
        +"        }\n"
        +"    }\n"
        + "]";
      new PropertyArray(str);
      fail("Expected an exception");
    } catch (PropertyException e) {
      assertEquals(
        "Duplicate key \"attr02-01\" at 269 [character 24 line 13]",
        e.getMessage());
    }
  }

  /**
   * Confirm behavior when putOnce() is called with null parameters
   */
  @Test
  void jsonObjectPutOnceNull() {
    PropertyObject jsonObject = new PropertyObject();
    jsonObject.putOnce(null, null);
    assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
    jsonObject.putOnce("", null);
    assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
    jsonObject.putOnce(null, "");
    assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
  }

  /**
   * Exercise PropertyObject opt(key, default) method.
   */
  @Test
  void jsonObjectOptDefault() {

    String str = "{\"myKey\": \"myval\", \"hiKey\": null}";
    PropertyObject jsonObject = new PropertyObject(str);

    assertTrue(null==jsonObject.optJSONArray("myKey"),
      "optJSONArray() should return null ");
    assertTrue(42L == jsonObject.optLong("myKey", 42L),
      "optLong() should return default long");

    assertTrue(42L == jsonObject.optNumber("myKey", 42L).longValue(),
      "optNumber() should return default Number");
    assertTrue("hi".equals(jsonObject.optString("hiKey", "hi")),
      "optString() should return default string");
  }

  /**
   * Exercise PropertyObject opt(key, default) method when the key doesn't exist.
   */
  @Test
  void jsonObjectOptNoKey() {

    PropertyObject jsonObject = new PropertyObject();

    assertNull(jsonObject.opt(null));

    assertTrue(null==jsonObject.optJSONArray("myKey"),
      "optJSONArray() should return null ");
    assertTrue(42l == jsonObject.optLong("myKey", 42l),
      "optLong() should return default long");
    assertTrue(42l == jsonObject.optNumber("myKey", Long.valueOf(42)).longValue(),
      "optNumber() should return default Number");
    assertTrue("hi".equals(jsonObject.optString("hiKey", "hi")),
      "optString() should return default string");
  }

  /**
   * Verifies that the opt methods properly convert string values.
   */
  @Test
  void jsonObjectOptStringConversion() {
    PropertyObject jo = new PropertyObject("{\"int\":\"123\",\"true\":\"true\",\"false\":\"false\"}");
    assertEquals(123L, jo.optLong("int", 0), "unexpected optLong value");
    assertEquals(123L, jo.optNumber("int", BigInteger.ZERO).longValue(), "unexpected optNumber value");
  }

  /**
   * Verifies that the opt methods properly convert string values to numbers and coerce them consistently.
   */
  @Test
  void jsonObjectOptCoercion() {
    PropertyObject jo = new PropertyObject("{\"largeNumberStr\":\"19007199254740993.35481234487103587486413587843213584\"}");
    // currently the parser doesn't recognize BigDecimal, to we have to put it manually
    jo.put("largeNumber", new BigDecimal("19007199254740993.35481234487103587486413587843213584"));

    // Test type coercion from larger to smaller
    assertEquals(19007199254740993l, jo.optLong("largeNumber", 0L));

    // conversion from a string
    assertEquals(19007199254740993l, jo.optLong("largeNumberStr", 0L));

    // the integer portion of the actual value is larger than a double can hold.
    assertNotEquals((long)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"), jo.optLong("largeNumber", 0L));
    assertNotEquals((long)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"), jo.optLong("largeNumberStr", 0L));
    assertEquals(19007199254740992l, (long)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
    assertEquals(2147483647, (int)Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
  }

  /**
   * Confirm behavior when PropertyObject put(key, null object) is called
   */
  @Test
  void jsonObjectputNull() {

    // put null should remove the item.
    String str = "{\"myKey\": \"myval\"}";
    PropertyObject jsonObjectRemove = new PropertyObject(str);
    jsonObjectRemove.remove("myKey");
    assertTrue(jsonObjectRemove.isEmpty(), "jsonObject should be empty");

    PropertyObject jsonObjectPutNull = new PropertyObject(str);
    jsonObjectPutNull.put("myKey", (Object) null);
    assertTrue(jsonObjectPutNull.isEmpty(), "jsonObject should be empty");


  }

  /**
   * Exercise PropertyObject quote() method
   * This purpose of quote() is to ensure that for strings with embedded
   * quotes, the quotes are properly escaped.
   */
  @Test
  void jsonObjectQuote() {
    String str;
    str = "";
    String quotedStr;
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"\"".equals(quotedStr),
      "quote() expected escaped quotes, found "+quotedStr);
    str = "\"\"";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"\\\"\\\"\"".equals(quotedStr),
      "quote() expected escaped quotes, found "+quotedStr);
    str = "</";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"<\\/\"".equals(quotedStr),
      "quote() expected escaped frontslash, found "+quotedStr);
    str = "AB\bC";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"AB\\bC\"".equals(quotedStr),
      "quote() expected escaped backspace, found "+quotedStr);
    str = "ABC\n";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"ABC\\n\"".equals(quotedStr),
      "quote() expected escaped newline, found "+quotedStr);
    str = "AB\fC";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"AB\\fC\"".equals(quotedStr),
      "quote() expected escaped formfeed, found "+quotedStr);
    str = "\r";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"\\r\"".equals(quotedStr),
      "quote() expected escaped return, found "+quotedStr);
    str = "\u1234\u0088";
    quotedStr = PropertyObject.quote(str);
    assertTrue("\"\u1234\\u0088\"".equals(quotedStr),
      "quote() expected escaped unicode, found "+quotedStr);
  }

  /**
   * Confirm behavior when PropertyObject stringToValue() is called for an
   * empty string
   */
  @Test
  void stringToValue() {
    String str = "";
    String valueStr = (String)(PropertyObject.stringToValue(str));
    assertTrue("".equals(valueStr),
      "stringToValue() expected empty String, found "+valueStr);
  }

  /**
   * Exercise the PropertyObject equals() method
   */
  @Test
  void equals() {
    String str = "{\"key\":\"value\"}";
    PropertyObject aJsonObject = new PropertyObject(str);
    assertTrue(aJsonObject.equals(aJsonObject),
      "Same PropertyObject should be equal to itself");
  }

  /**
   * JSON null is not the same as Java null. This test examines the differences
   * in how they are handled by JSON-java.
   */
  @Test
  void jsonObjectNullOperations() {
    /**
     * The Javadoc for PropertyObject.NULL states:
     *      "PropertyObject.NULL is equivalent to the value that JavaScript calls null,
     *      whilst Java's null is equivalent to the value that JavaScript calls
     *      undefined."
     *
     * Standard ECMA-262 6th Edition / June 2015 (included to help explain the javadoc):
     *      undefined value: primitive value used when a variable has not been assigned a value
     *      Undefined type:  type whose sole value is the undefined value
     *      null value:      primitive value that represents the intentional absence of any object value
     *      Null type:       type whose sole value is the null value
     * Java SE8 language spec (included to help explain the javadoc):
     *      The Kinds of Types and Values ...
     *      There is also a special null type, the type of the expression null, which has no name.
     *      Because the null type has no name, it is impossible to declare a variable of the null
     *      type or to cast to the null type. The null reference is the only possible value of an
     *      expression of null type. The null reference can always be assigned or cast to any reference type.
     *      In practice, the programmer can ignore the null type and just pretend that null is merely
     *      a special literal that can be of any reference type.
     * Extensible Markup Language (XML) 1.0 Fifth Edition / 26 November 2008
     *      No mention of null
     * ECMA-404 1st Edition / October 2013:
     *      JSON Text  ...
     *      These are three literal name tokens: ...
     *      null
     *
     * There seems to be no best practice to follow, it's all about what we
     * want the code to do.
     */

    // add PropertyObject.NULL then convert to string in the manner of XML.toString()
    PropertyObject jsonObjectJONull = new PropertyObject();
    Object obj = PropertyObject.NULL;
    jsonObjectJONull.put("key", obj);
    Object value = jsonObjectJONull.opt("key");
    assertTrue(obj.equals(value),
      "opt() PropertyObject.NULL should find PropertyObject.NULL");
    value = jsonObjectJONull.get("key");
    assertTrue(obj.equals(value),
      "get() PropertyObject.NULL should find PropertyObject.NULL");
    if (value == null) {
      value = "";
    }
    String string = value instanceof String ? (String)value : null;
    assertTrue(string == null,
      "XML toString() should convert PropertyObject.NULL to null");

    // now try it with null
    PropertyObject jsonObjectNull = new PropertyObject();
    obj = null;
    jsonObjectNull.put("key", obj);
    value = jsonObjectNull.opt("key");
    assertNull(value, "opt() null should find null");
    try {
      value = jsonObjectNull.get("key");
      fail("get() null should throw exception");
    } catch (Exception ignored) {}
  }

  @Test
  public void invalidEscapeSequence() {
    String json = "{ \"\\url\": \"value\" }";
    assertThrows(PropertyException.class, () -> new PropertyObject(json), "Expected an exception");
  }

  /**
   * Exercise PropertyObject toMap() method.
   */
  @Test
  void toMap() {
    String jsonObjectStr =
      "{" +
        "\"key1\":" +
        "[1,2," +
        "{\"key3\":true}" +
        "]," +
        "\"key2\":" +
        "{\"key1\":\"val1\",\"key2\":" +
        "{\"key2\":null}," +
        "\"key3\":42" +
        "}," +
        "\"key3\":" +
        "[" +
        "[\"value1\",2.1]" +
        "," +
        "[null]" +
        "]" +
        "}";

    PropertyObject jsonObject = new PropertyObject(jsonObjectStr);
    Map<?,?> map = jsonObject.toMap();

    assertTrue(map != null, "Map should not be null");
    assertTrue(map.size() == 3, "Map should have 3 elements");

    List<?> key1List = (List<?>)map.get("key1");
    assertTrue(key1List != null, "key1 should not be null");
    assertTrue(key1List.size() == 3, "key1 list should have 3 elements");
    assertTrue(key1List.get(0).equals(Integer.valueOf(1)), "key1 value 1 should be 1");
    assertTrue(key1List.get(1).equals(Integer.valueOf(2)), "key1 value 2 should be 2");

    Map<?,?> key1Value3Map = (Map<?,?>)key1List.get(2);
    assertTrue(key1Value3Map != null, "Map should not be null");
    assertTrue(key1Value3Map.size() == 1, "Map should have 1 element");
    assertTrue(key1Value3Map.get("key3").equals(Boolean.TRUE), "Map key3 should be true");

    Map<?,?> key2Map = (Map<?,?>)map.get("key2");
    assertTrue(key2Map != null, "key2 should not be null");
    assertTrue(key2Map.size() == 3, "key2 map should have 3 elements");
    assertTrue(key2Map.get("key1").equals("val1"), "key2 map key 1 should be val1");
    assertTrue(key2Map.get("key3").equals(Integer.valueOf(42)), "key2 map key 3 should be 42");

    Map<?,?> key2Val2Map = (Map<?,?>)key2Map.get("key2");
    assertTrue(key2Val2Map != null, "key2 map key 2 should not be null");
    assertTrue(key2Val2Map.containsKey("key2"), "key2 map key 2 should have an entry");
    assertTrue(key2Val2Map.get("key2") == null, "key2 map key 2 value should be null");

    List<?> key3List = (List<?>)map.get("key3");
    assertTrue(key3List != null, "key3 should not be null");
    assertTrue(key3List.size() == 2, "key3 list should have 3 elements");

    List<?> key3Val1List = (List<?>)key3List.get(0);
    assertTrue(key3Val1List != null, "key3 list val 1 should not be null");
    assertTrue(key3Val1List.size() == 2, "key3 list val 1 should have 2 elements");
    assertTrue(key3Val1List.get(0).equals("value1"), "key3 list val 1 list element 1 should be value1");
    assertTrue(key3Val1List.get(1).equals(Double.valueOf("2.1")), "key3 list val 1 list element 2 should be 2.1");

    List<?> key3Val2List = (List<?>)key3List.get(1);
    assertTrue(key3Val2List != null, "key3 list val 2 should not be null");
    assertTrue(key3Val2List.size() == 1, "key3 list val 2 should have 1 element");
    assertTrue(key3Val2List.get(0) == null, "key3 list val 2 list element 1 should be null");

    // Assert that toMap() is a deep copy
    jsonObject.getJSONArray("key3").getJSONArray(0).put(0, "still value 1");
    assertTrue(key3Val1List.get(0).equals("value1"), "key3 list val 1 list element 1 should be value1");

    // assert that the new map is mutable
    assertTrue(map.remove("key3") != null, "Removing a key should succeed");
    assertTrue(map.size() == 2, "Map should have 2 elements");
  }

  @Test
  public void testPutNullBoolean() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, false));
  }
  @Test
  public void testPutNullCollection() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, Collections.emptySet()));
  }
  @Test
  public void testPutNullDouble() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, 0.0d));
  }
  @Test
  public void testPutNullFloat() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, 0.0f));
  }
  @Test
  public void testPutNullInt() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, 0));
  }
  @Test
  public void testPutNullLong() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, 0L));
  }
  @Test
  public void testPutNullMap() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, Collections.emptyMap()));
  }
  @Test
  public void testPutNullObject() {
    // null put key
    PropertyObject jsonObject = new PropertyObject("{}");
    assertThrows(NullPointerException.class, ()->jsonObject.put(null, new Object()));
  }
}