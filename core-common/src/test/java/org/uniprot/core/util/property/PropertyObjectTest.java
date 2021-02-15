package org.uniprot.core.util.property;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
class PropertyObjectTest {

    /**
     * Regular Expression Pattern that matches JSON Numbers. This is primarily used for output to
     * guarantee that we are always writing valid JSON.
     */
    private static final Pattern NUMBER_PATTERN =
            Pattern.compile("-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?");

    /** Tests that the similar method is working as expected. */
    @Test
    void verifySimilar() {
        final String string1 = "HasSameRef";
        PropertyObject obj1 =
                new PropertyObject().put("key1", "abc").put("key2", 2).put("key3", string1);

        PropertyObject obj2 =
                new PropertyObject().put("key1", "abc").put("key2", 3).put("key3", string1);

        PropertyObject obj3 =
                new PropertyObject().put("key1", "abc").put("key2", 2).put("key3", string1);

        assertFalse(obj1.similar(obj2), "Should eval to false");

        assertTrue(obj1.similar(obj3), "Should eval to true");
    }

    @Test
    void timeNumberParsing() {
        // test data to use
        final String[] testData =
                new String[] {
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
                    ,
                    "123467890123467890123467890123467890123467890123467890123467"
                            + "8901234678901234678901234678901234678901234678901234678"
                            + "9012346789012346789012346789012346789012346789012346789"
                            + "0a"
                };
        /*
         * Changed to 1000 for faster test runs
         */
        // final int iterations = 1000000;
        final int iterations = 1000;

        // 10 million iterations 1,000,000 * 10 (currently 100,000)
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            for (String testDatum : testData) {
                try {
                    BigDecimal v1 = new BigDecimal(testDatum);
                    v1.signum();
                } catch (Exception ignore) {
                    // do nothing
                }
            }
        }
        final long elapsedNano1 = System.nanoTime() - startTime;
        log.debug("new BigDecimal(testData[]) : " + elapsedNano1 / 1000000 + " ms");

        startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            for (String testDatum : testData) {
                try {
                    NUMBER_PATTERN.matcher(testDatum).matches();
                } catch (Exception ignore) {
                    // do nothing
                }
            }
        }
        final long elapsedNano2 = System.nanoTime() - startTime;
        log.debug(
                "NUMBER_PATTERN.matcher(testData[]).matches() : " + elapsedNano2 / 1000000 + " ms");
        assertTrue(elapsedNano1 > 0);
        assertTrue(elapsedNano2 > 0);
        // don't assert normally as the testing is machine dependent.
        // assertTrue("Expected Pattern matching to be faster than BigDecimal
        // constructor",elapsedNano2<elapsedNano1);
    }

    /**
     * PropertyObject built from a bean, but only using a null value. Nothing good is expected to
     * happen. Expects NullPointerException
     */
    @Test
    void jsonObjectByNullBean() {
        assertThrows(NullPointerException.class, () -> new PropertyObject((String) null));
    }

    /**
     * The JSON parser is permissive of unambiguous unquoted keys and values. Such JSON text should
     * be allowed, even if it does not strictly conform to the spec. However, after being parsed,
     * toString() should emit strictly conforming JSON text.
     */
    @Test
    void unquotedText() {
        String str = "{key1:value1, key2:42}";
        PropertyObject jsonObject = new PropertyObject(str);
        assertTrue(jsonObject.keySet().contains("key1"), "expected key1");
        assertEquals("value1", jsonObject.get("key1"), "expected value1");
        assertTrue(jsonObject.keySet().contains("key2"), "expected key2");
        assertEquals(42, jsonObject.get("key2"), "expected 42");
    }

    @Test
    void testLongFromString() {
        String str = "26315000000253009";
        PropertyObject json = new PropertyObject();
        json.put("key", str);

        final Object actualKey = json.opt("key");
        assert str.equals(actualKey) : "Incorrect key value. Got " + actualKey + " expected " + str;

        final long actualLong = json.optLong("key", 0L);
        assertTrue(actualLong > 0, "Unable to extract long value for string " + str);
        assert 26315000000253009L == actualLong
                : "Incorrect key value. Got " + actualLong + " expected " + str;

        final String actualString = json.optString("key", "");
        assert str.equals(actualString)
                : "Incorrect key value. Got " + actualString + " expected " + str;
    }

    /** A PropertyObject can be created with no content */
    @Test
    void emptyJsonObject() {
        PropertyObject jsonObject = new PropertyObject();
        assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
    }

    /**
     * JSONObjects can be built from a Map<String, Object>. In this test the map is null. the
     * PropertyObject(JsonTokener) ctor is not tested directly since it already has full coverage
     * from other tests.
     */
    @Test
    void jsonObjectByNullMap() {
        Map<String, Object> map = null;
        PropertyObject jsonObject = new PropertyObject(map);
        assertTrue(jsonObject.isEmpty(), "jsonObject should be empty");
    }

    /** Verifies that the put Collection has backwards compatibility with RAW types pre-java5. */
    @Test
    void verifyPutCollection() {

        final PropertyObject expected = new PropertyObject("{\"myCollection\":[10]}");

        @SuppressWarnings("rawtypes")
        Collection myRawC = Collections.singleton(10);
        PropertyObject jaRaw = new PropertyObject();
        jaRaw.put("myCollection", myRawC);

        Collection<Object> myCObj = Collections.singleton(10);
        PropertyObject jaObj = new PropertyObject();
        jaObj.put("myCollection", myCObj);

        Collection<Integer> myCInt = Collections.singleton(10);
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

    /** Verifies that the put Map has backwards compatibility with RAW types pre-java5. */
    @Test
    void verifyPutMap() {

        final PropertyObject expected = new PropertyObject("{\"myMap\":{\"myKey\":10}}");

        @SuppressWarnings("rawtypes")
        Map myRawC = Collections.singletonMap("myKey", 10);
        PropertyObject jaRaw = new PropertyObject();
        jaRaw.put("myMap", myRawC);

        Map<String, Object> myCStrObj = Collections.singletonMap("myKey", 10);
        PropertyObject jaStrObj = new PropertyObject();
        jaStrObj.put("myMap", myCStrObj);

        Map<String, Integer> myCStrInt = Collections.singletonMap("myKey", 10);
        PropertyObject jaStrInt = new PropertyObject();
        jaStrInt.put("myMap", myCStrInt);

        Map<?, ?> myCObjObj = Collections.singletonMap((Object) "myKey", (Object) 10);
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

    /** Exercise some PropertyObject get[type] and opt[type] methods */
    @Test
    void jsonObjectValues() {
        String str =
                "{\"trueKey\":true,\"falseKey\":false,\"trueStrKey\":\"true\",\"falseStrKey\":\"false\",\"stringKey\":\"hello"
                        + " world!\",\"intKey\":42,"
                        + "\"intStrKey\":\"43\",\"longKey\":1234567890123456789,"
                        + "\"longStrKey\":\"987654321098765432\",\"doubleKey\":-23.45e7,"
                        + "\"doubleStrKey\":\"00001.000\","
                        + "\"BigDecimalStrKey\":\"19007199254740993.35481234487103587486413587843213584\","
                        + "\"negZeroKey\":-0.0,\"negZeroStrKey\":\"-0.0\",\"arrayKey\":[0,1,2],"
                        + "\"objectKey\":{\"myKey\":\"myVal\"}}";
        PropertyObject jsonObject = new PropertyObject(str);
        assertEquals(
                "hello world!", jsonObject.getString("stringKey"), "stringKey should be string");
        assertTrue(
                jsonObject.opt("negZeroKey") instanceof Double,
                "opt negZeroKey should be a Double");
        assertTrue(
                jsonObject.get("negZeroKey") instanceof Double,
                "get negZeroKey should be a Double");
        assertTrue(
                jsonObject.optNumber("negZeroKey") instanceof Double,
                "optNumber negZeroKey should return Double");
        assertTrue(
                jsonObject.optNumber("negZeroStrKey") instanceof Double,
                "optNumber negZeroStrKey should return Double");
        assertEquals(
                0,
                Double.compare(jsonObject.optNumber("negZeroKey").doubleValue(), -0.0d),
                "optNumber negZeroKey should be -0.0");
        assertEquals(
                0,
                Double.compare(jsonObject.optNumber("negZeroStrKey").doubleValue(), -0.0d),
                "optNumber negZeroStrKey should be -0.0");
        assertEquals(
                1234567890123456789L,
                jsonObject.optLong("longKey", 0L),
                "opt longKey should be long");
        assertEquals(
                1234567890123456789L,
                jsonObject.optLong("longKey", 0),
                "opt longKey with default should be long");
        assertTrue(
                jsonObject.optNumber("intKey") instanceof Integer,
                "optNumber int should return Integer");
        assertTrue(
                jsonObject.optNumber("longKey") instanceof Long,
                "optNumber long should return Long");
        assertTrue(
                jsonObject.optNumber("doubleKey") instanceof Double,
                "optNumber double should return Double");
        assertTrue(
                jsonObject.optNumber("intStrKey") instanceof Integer,
                "optNumber Str int should return Integer");
        assertTrue(
                jsonObject.optNumber("longStrKey") instanceof Long,
                "optNumber Str long should return Long");
        assertTrue(
                jsonObject.optNumber("doubleStrKey") instanceof Double,
                "optNumber Str double should return Double");
        assertTrue(
                jsonObject.optNumber("BigDecimalStrKey") instanceof BigDecimal,
                "optNumber BigDecimalStrKey should return BigDecimal");
        assertTrue(jsonObject.isNull("xKey"), "xKey should not exist");
        assertTrue(jsonObject.has("stringKey"), "stringKey should exist");
        assertEquals(
                "hello world!",
                jsonObject.optString("stringKey", ""),
                "opt stringKey should string");
        assertEquals(
                "hello world!",
                jsonObject.optString("stringKey", "not found"),
                "opt stringKey with default should string");
        PropertyArray jsonArray = jsonObject.getJSONArray("arrayKey");
        assertTrue(
                jsonArray.getInt(0) == 0 && jsonArray.getInt(1) == 1 && jsonArray.getInt(2) == 2,
                "arrayKey should be JSONArray");
        jsonArray = jsonObject.optJSONArray("arrayKey");
        assertTrue(
                jsonArray.getInt(0) == 0 && jsonArray.getInt(1) == 1 && jsonArray.getInt(2) == 2,
                "opt arrayKey should be JSONArray");
        PropertyObject jsonObjectInner = jsonObject.getJSONObject("objectKey");
        assertEquals("myVal", jsonObjectInner.get("myKey"), "objectKey should be PropertyObject");
    }

    /** Check whether PropertyObject handles large or high precision numbers correctly */
    @Test
    void stringToValueNumbersTest() {
        assertTrue(PropertyObject.stringToValue("-0") instanceof Double, "-0 Should be a Double!");
        assertTrue(
                PropertyObject.stringToValue("-0.0") instanceof Double, "-0.0 Should be a Double!");
        assertTrue(PropertyObject.stringToValue("-") instanceof String, "'-' Should be a String!");
        assertTrue(
                PropertyObject.stringToValue("0.2") instanceof Double, "0.2 should be a Double!");
        assertTrue(
                PropertyObject.stringToValue("0.2f") instanceof Double,
                "Doubles should be Doubles, even when incorrectly converting floats!");
        /*
         * This test documents a need for BigDecimal conversion.
         */
        Object obj = PropertyObject.stringToValue("299792.457999999984");
        assertEquals(
                299792.458,
                obj,
                "evaluates to 299792.458 double instead of 299792.457999999984 BigDecimal!");
        assertTrue(PropertyObject.stringToValue("1") instanceof Integer, "1 should be an Integer!");
        assertTrue(
                PropertyObject.stringToValue(Integer.valueOf(Integer.MAX_VALUE).toString())
                        instanceof Integer,
                "Integer.MAX_VALUE should still be an Integer!");
        assertTrue(
                PropertyObject.stringToValue(
                                Long.valueOf(Long.sum(Integer.MAX_VALUE, 1)).toString())
                        instanceof Long,
                "Large integers should be a Long!");
        assertTrue(
                PropertyObject.stringToValue(Long.valueOf(Long.MAX_VALUE).toString())
                        instanceof Long,
                "Long.MAX_VALUE should still be an Integer!");

        String str =
                new BigInteger(Long.valueOf(Long.MAX_VALUE).toString())
                        .add(BigInteger.ONE)
                        .toString();
        assertEquals(
                "9223372036854775808",
                PropertyObject.stringToValue(str),
                "Really large integers currently evaluate to string");
    }

    /**
     * This test documents numeric values which could be numerically handled as BigDecimal or
     * BigInteger. It helps determine what outputs will change if those types are supported.
     */
    @Test
    void jsonValidNumberValuesNeitherLongNorIEEE754Compatible() {
        // Valid JSON Numbers, probably should return BigDecimal or BigInteger objects
        String str =
                "{"
                        + "\"numberWithDecimals\":299792.457999999984,"
                        + "\"largeNumber\":12345678901234567890,"
                        + "\"preciseNumber\":0.2000000000000000111,"
                        + "\"largeExponent\":-23.45e2327"
                        + "}";
        PropertyObject jsonObject = new PropertyObject(str);
        // Comes back as a double, but loses precision
        assertEquals(
                299792.458,
                jsonObject.get("numberWithDecimals"),
                "numberWithDecimals currently evaluates to double 299792.458");
        Object obj = jsonObject.get("largeNumber");
        assertEquals("12345678901234567890", obj, "largeNumber currently evaluates to string");
        // comes back as a double but loses precision
        assertEquals(
                0.2,
                jsonObject.get("preciseNumber"),
                "preciseNumber currently evaluates to double 0.2");
        obj = jsonObject.get("largeExponent");
        assertEquals("-23.45e2327", obj, "largeExponent should currently evaluates as a string");
    }

    /** This test documents how JSON-Java handles invalid numeric input. */
    @Test
    void jsonInvalidNumberValues() {
        // Number-notations supported by Java and invalid as JSON
        String str =
                "{"
                        + "\"hexNumber\":-0x123,"
                        + "\"tooManyZeros\":00,"
                        + "\"negativeInfinite\":-Infinity,"
                        + "\"negativeNaN\":-NaN,"
                        + "\"negativeFraction\":-.01,"
                        + "\"tooManyZerosFraction\":00.001,"
                        + "\"negativeHexFloat\":-0x1.fffp1,"
                        + "\"hexFloat\":0x1.0P-1074,"
                        + "\"floatIdentifier\":0.1f,"
                        + "\"doubleIdentifier\":0.1d"
                        + "}";
        PropertyObject jsonObject = new PropertyObject(str);
        Object obj;
        obj = jsonObject.get("hexNumber");
        assertFalse(
                obj instanceof Number, "hexNumber must not be a number (should throw exception!?)");
        assertEquals("-0x123", obj, "hexNumber currently evaluates to string");
        assertEquals(
                "00", jsonObject.get("tooManyZeros"), "tooManyZeros currently evaluates to string");
        obj = jsonObject.get("negativeInfinite");
        assertEquals("-Infinity", obj, "negativeInfinite currently evaluates to string");
        obj = jsonObject.get("negativeNaN");
        assertEquals("-NaN", obj, "negativeNaN currently evaluates to string");
        assertEquals(
                -0.01,
                jsonObject.get("negativeFraction"),
                "negativeFraction currently evaluates to double -0.01");
        assertEquals(
                0.001,
                jsonObject.get("tooManyZerosFraction"),
                "tooManyZerosFraction currently evaluates to double 0.001");
        assertEquals(
                -3.99951171875,
                jsonObject.get("negativeHexFloat"),
                "negativeHexFloat currently evaluates to double -3.99951171875");
        assertEquals(
                4.9E-324,
                jsonObject.get("hexFloat"),
                "hexFloat currently evaluates to double 4.9E-324");
        assertEquals(
                0.1,
                jsonObject.get("floatIdentifier"),
                "floatIdentifier currently evaluates to double 0.1");
        assertEquals(
                0.1,
                jsonObject.get("doubleIdentifier"),
                "doubleIdentifier currently evaluates to double 0.1");
    }

    /** Tests how PropertyObject get[type] handles incorrect types */
    @Test
    void jsonObjectNonAndWrongValues() {
        String str =
                "{"
                        + "\"trueKey\":true,"
                        + "\"falseKey\":false,"
                        + "\"trueStrKey\":\"true\","
                        + "\"falseStrKey\":\"false\","
                        + "\"stringKey\":\"hello world!\","
                        + "\"intKey\":42,"
                        + "\"intStrKey\":\"43\","
                        + "\"longKey\":1234567890123456789,"
                        + "\"longStrKey\":\"987654321098765432\","
                        + "\"doubleKey\":-23.45e7,"
                        + "\"doubleStrKey\":\"00001.000\","
                        + "\"arrayKey\":[0,1,2],"
                        + "\"objectKey\":{\"myKey\":\"myVal\"}"
                        + "}";
        PropertyObject jsonObject = new PropertyObject(str);

        try {
            jsonObject.getString("nonKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"nonKey\"] not found.", e.getMessage());
        }
        try {
            jsonObject.getString("trueKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"trueKey\"] not a string.", e.getMessage());
        }

        try {
            jsonObject.getJSONArray("nonKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"nonKey\"] not found.", e.getMessage());
        }
        try {
            jsonObject.getJSONArray("stringKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"stringKey\"] is not a JSONArray.", e.getMessage());
        }
        try {
            jsonObject.getJSONObject("nonKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"nonKey\"] not found.", e.getMessage());
        }
        try {
            jsonObject.getJSONObject("stringKey");
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("JSONObject[\"stringKey\"] is not a JSONObject.", e.getMessage());
        }
    }

    /**
     * Exercise the PropertyObject wrap() method. Sometimes wrap() will change the object being
     * wrapped, other times not. The purpose of wrap() is to ensure the value is packaged in a way
     * that is compatible with how a PropertyObject value or PropertyArray value is supposed to be
     * stored.
     */
    @Test
    void wrapObject() {
        // wrap(null) returns NULL
        assertSame(PropertyObject.NULL, PropertyObject.wrap(null), "null wrap() incorrect");

        // wrap(Integer) returns Integer
        Integer in = 1;
        assertSame(in, PropertyObject.wrap(in), "Integer wrap() incorrect");

        /*
         * This test is to document the preferred behavior if BigDecimal is
         * supported. Previously bd returned as a string, since it
         * is recognized as being a Java package class. Now with explicit
         * support for big numbers, it remains a BigDecimal
         */
        Object bdWrap = PropertyObject.wrap(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, bdWrap, "BigDecimal.ONE evaluates to ONE");

        // wrap PropertyObject returns PropertyObject
        String jsonObjectStr =
                "{" + "\"key1\":\"val1\"," + "\"key2\":\"val2\"," + "\"key3\":\"val3\"" + "}";
        PropertyObject jsonObject = new PropertyObject(jsonObjectStr);
        assertSame(jsonObject, PropertyObject.wrap(jsonObject), "PropertyObject wrap() incorrect");
    }

    /**
     * RFC 7159 defines control characters to be U+0000 through U+001F. This test verifies that the
     * parser is checking for these in expected ways.
     */
    @Test
    void jsonObjectParseControlCharacters() {
        for (int i = 0; i <= 0x001f; i++) {
            final String charString = String.valueOf((char) i);
            final String source = "{\"key\":\"" + charString + "\"}";
            try {
                PropertyObject jo = new PropertyObject(source);
                assertEquals(
                        charString,
                        jo.getString("key"),
                        "Expected "
                                + charString
                                + "("
                                + i
                                + ") in the JSON Object but did not find it.");
            } catch (PropertyException ex) {
                assertTrue(
                        i == '\0' || i == '\n' || i == '\r',
                        "Only \\0 (U+0000), \\n"
                                + " (U+000A), and \\r"
                                + " (U+000D) should cause an error. Instead "
                                + charString
                                + "("
                                + i
                                + ") caused an error");
            }
        }
    }

    /** Explore how PropertyObject handles parsing errors. */
    @SuppressWarnings("boxing")
    @Test
    void jsonObjectParsingErrors() {
        try {
            // does not start with '{'
            String str = "abc";
            new PropertyObject(str);
        } catch (PropertyException e) {
            assertEquals(
                    "A JSONObject text must begin with '{' at 1 [character 2 line 1]",
                    e.getMessage());
        }
        try {
            // does not end with '}'
            String str = "{";
            new PropertyObject(str);
        } catch (PropertyException e) {
            assertEquals(
                    "A JSONObject text must end with '}' at 1 [character 2 line 1]",
                    e.getMessage());
        }
        try {
            // key with no ':'
            String str = "{\"myKey\" = true}";
            new PropertyObject(str);
        } catch (PropertyException e) {
            assertEquals("Expected a ':' after a key at 10 [character 11 line 1]", e.getMessage());
        }
        try {
            // entries with no ',' separator
            String str = "{\"myKey\":true \"myOtherKey\":false}";
            new PropertyObject(str);
        } catch (PropertyException e) {
            assertEquals("Expected a ',' or '}' at 15 [character 16 line 1]", e.getMessage());
        }
        try {
            // invalid key
            String str = "{\"myKey\":true, \"myOtherKey\":false}";
            PropertyObject jsonObject = new PropertyObject(str);
            jsonObject.get(null);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("Null key.", e.getMessage());
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
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr03\":\"value-04\"\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("Duplicate key \"attr03\" at 90 [character 13 line 5]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key (level 0) holding an object
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr03\": {"
                            + "        \"attr04-01\":\"value-04-01\",n"
                            + "        \"attr04-02\":\"value-04-02\",n"
                            + "        \"attr04-03\":\"value-04-03\"n"
                            + "    }\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("Duplicate key \"attr03\" at 90 [character 13 line 5]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key (level 0) holding an array
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr03\": [\n"
                            + "        {"
                            + "            \"attr04-01\":\"value-04-01\",n"
                            + "            \"attr04-02\":\"value-04-02\",n"
                            + "            \"attr04-03\":\"value-04-03\"n"
                            + "        }\n"
                            + "    ]\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("Duplicate key \"attr03\" at 90 [character 13 line 5]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key (level 1)
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr04\": {\n"
                            + "        \"attr04-01\":\"value04-01\",\n"
                            + "        \"attr04-02\":\"value04-02\",\n"
                            + "        \"attr04-03\":\"value04-03\",\n"
                            + "        \"attr04-03\":\"value04-04\"\n"
                            + "    }\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals(
                    "Duplicate key \"attr04-03\" at 215 [character 20 line 9]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key (level 1) holding an object
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr04\": {\n"
                            + "        \"attr04-01\":\"value04-01\",\n"
                            + "        \"attr04-02\":\"value04-02\",\n"
                            + "        \"attr04-03\":\"value04-03\",\n"
                            + "        \"attr04-03\": {\n"
                            + "            \"attr04-04-01\":\"value04-04-01\",\n"
                            + "            \"attr04-04-02\":\"value04-04-02\",\n"
                            + "            \"attr04-04-03\":\"value04-04-03\",\n"
                            + "        }\n"
                            + "    }\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals(
                    "Duplicate key \"attr04-03\" at 215 [character 20 line 9]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key (level 1) holding an array
            String str =
                    "{\n"
                            + "    \"attr01\":\"value-01\",\n"
                            + "    \"attr02\":\"value-02\",\n"
                            + "    \"attr03\":\"value-03\",\n"
                            + "    \"attr04\": {\n"
                            + "        \"attr04-01\":\"value04-01\",\n"
                            + "        \"attr04-02\":\"value04-02\",\n"
                            + "        \"attr04-03\":\"value04-03\",\n"
                            + "        \"attr04-03\": [\n"
                            + "            {\n"
                            + "                \"attr04-04-01\":\"value04-04-01\",\n"
                            + "                \"attr04-04-02\":\"value04-04-02\",\n"
                            + "                \"attr04-04-03\":\"value04-04-03\",\n"
                            + "            }\n"
                            + "        ]\n"
                            + "    }\n"
                            + "}";
            new PropertyObject(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals(
                    "Duplicate key \"attr04-03\" at 215 [character 20 line 9]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key in object (level 0) within an
            // array
            String str =
                    "[\n"
                            + "    {\n"
                            + "        \"attr01\":\"value-01\",\n"
                            + "        \"attr02\":\"value-02\"\n"
                            + "    },\n"
                            + "    {\n"
                            + "        \"attr01\":\"value-01\",\n"
                            + "        \"attr01\":\"value-02\"\n"
                            + "    }\n"
                            + "]";
            new PropertyArray(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals("Duplicate key \"attr01\" at 124 [character 17 line 8]", e.getMessage());
        }
        try {
            // test exception message when including a duplicate key in object (level 1) within an
            // array
            String str =
                    "[\n"
                            + "    {\n"
                            + "        \"attr01\":\"value-01\",\n"
                            + "        \"attr02\": {\n"
                            + "            \"attr02-01\":\"value-02-01\",\n"
                            + "            \"attr02-02\":\"value-02-02\"\n"
                            + "        }\n"
                            + "    },\n"
                            + "    {\n"
                            + "        \"attr01\":\"value-01\",\n"
                            + "        \"attr02\": {\n"
                            + "            \"attr02-01\":\"value-02-01\",\n"
                            + "            \"attr02-01\":\"value-02-02\"\n"
                            + "        }\n"
                            + "    }\n"
                            + "]";
            new PropertyArray(str);
            fail("Expected an exception");
        } catch (PropertyException e) {
            assertEquals(
                    "Duplicate key \"attr02-01\" at 269 [character 24 line 13]", e.getMessage());
        }
    }

    /** Confirm behavior when putOnce() is called with null parameters */
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

    /** Exercise PropertyObject opt(key, default) method. */
    @Test
    void jsonObjectOptDefault() {

        String str = "{\"myKey\": \"myval\", \"hiKey\": null}";
        PropertyObject jsonObject = new PropertyObject(str);

        assertNull(jsonObject.optJSONArray("myKey"), "optJSONArray() should return null ");
        assertEquals(42L, jsonObject.optLong("myKey", 42L), "optLong() should return default long");

        assertEquals(
                42L,
                jsonObject.optNumber("myKey", 42L).longValue(),
                "optNumber() should return default Number");
        assertEquals(
                "hi",
                jsonObject.optString("hiKey", "hi"),
                "optString() should return default string");
    }

    /** Exercise PropertyObject opt(key, default) method when the key doesn't exist. */
    @Test
    void jsonObjectOptNoKey() {

        PropertyObject jsonObject = new PropertyObject();

        assertNull(jsonObject.opt(null));

        assertNull(jsonObject.optJSONArray("myKey"), "optJSONArray() should return null ");
        assertEquals(42L, jsonObject.optLong("myKey", 42L), "optLong() should return default long");
        assertEquals(
                42L,
                jsonObject.optNumber("myKey", 42L).longValue(),
                "optNumber() should return default Number");
        assertEquals(
                "hi",
                jsonObject.optString("hiKey", "hi"),
                "optString() should return default string");
    }

    /** Verifies that the opt methods properly convert string values. */
    @Test
    void jsonObjectOptStringConversion() {
        PropertyObject jo =
                new PropertyObject("{\"int\":\"123\",\"true\":\"true\",\"false\":\"false\"}");
        assertEquals(123L, jo.optLong("int", 0), "unexpected optLong value");
        assertEquals(
                123L,
                jo.optNumber("int", BigInteger.ZERO).longValue(),
                "unexpected optNumber value");
    }

    /**
     * Verifies that the opt methods properly convert string values to numbers and coerce them
     * consistently.
     */
    @Test
    void jsonObjectOptCoercion() {
        PropertyObject jo =
                new PropertyObject(
                        "{\"largeNumberStr\":\"19007199254740993.35481234487103587486413587843213584\"}");
        // currently the parser doesn't recognize BigDecimal, to we have to put it manually
        jo.put(
                "largeNumber",
                new BigDecimal("19007199254740993.35481234487103587486413587843213584"));

        // Test type coercion from larger to smaller
        assertEquals(19007199254740993L, jo.optLong("largeNumber", 0L));

        // conversion from a string
        assertEquals(19007199254740993L, jo.optLong("largeNumberStr", 0L));

        // the integer portion of the actual value is larger than a double can hold.
        assertNotEquals(
                (long) Double.parseDouble("19007199254740993.35481234487103587486413587843213584"),
                jo.optLong("largeNumber", 0L));
        assertNotEquals(
                (long) Double.parseDouble("19007199254740993.35481234487103587486413587843213584"),
                jo.optLong("largeNumberStr", 0L));
        assertEquals(
                19007199254740992L,
                (long) Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
        assertEquals(
                2147483647,
                (int) Double.parseDouble("19007199254740993.35481234487103587486413587843213584"));
    }

    /** Confirm behavior when PropertyObject put(key, null object) is called */
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
     * Exercise PropertyObject quote() method This purpose of quote() is to ensure that for strings
     * with embedded quotes, the quotes are properly escaped.
     */
    @Test
    void jsonObjectQuote() {
        String str;
        str = "";
        String quotedStr;
        quotedStr = PropertyObject.quote(str);
        assertEquals("\"\"", quotedStr, "quote() expected escaped quotes, found " + quotedStr);
        str = "\"\"";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"\\\"\\\"\"", quotedStr, "quote() expected escaped quotes, found " + quotedStr);
        str = "</";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"<\\/\"", quotedStr, "quote() expected escaped frontslash, found " + quotedStr);
        str = "AB\bC";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"AB\\bC\"", quotedStr, "quote() expected escaped backspace, found " + quotedStr);
        str = "ABC\n";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"ABC\\n\"", quotedStr, "quote() expected escaped newline, found " + quotedStr);
        str = "AB\fC";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"AB\\fC\"", quotedStr, "quote() expected escaped formfeed, found " + quotedStr);
        str = "\r";
        quotedStr = PropertyObject.quote(str);
        assertEquals("\"\\r\"", quotedStr, "quote() expected escaped return, found " + quotedStr);
        str = "\u1234\u0088";
        quotedStr = PropertyObject.quote(str);
        assertEquals(
                "\"\u1234\\u0088\"",
                quotedStr,
                "quote() expected escaped unicode, found " + quotedStr);
    }

    /** Confirm behavior when PropertyObject stringToValue() is called for an empty string */
    @Test
    void stringToValue() {
        String str = "";
        String valueStr = (String) (PropertyObject.stringToValue(str));
        assertEquals("", valueStr, "stringToValue() expected empty String, found " + valueStr);
    }

    /** Exercise the PropertyObject equals() method */
    @Test
    void equals() {
        String str = "{\"key\":\"value\"}";
        PropertyObject aJsonObject = new PropertyObject(str);
        assertEquals(aJsonObject, aJsonObject, "Same PropertyObject should be equal to itself");
    }

    /**
     * JSON null is not the same as Java null. This test examines the differences in how they are
     * handled by JSON-java.
     */
    @Test
    void jsonObjectNullOperations() {
        /*
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
        assertEquals(obj, value, "opt() PropertyObject.NULL should find PropertyObject.NULL");
        value = jsonObjectJONull.get("key");
        assertEquals(obj, value, "get() PropertyObject.NULL should find PropertyObject.NULL");
        if (value == null) {
            value = "";
        }
        String string = value instanceof String ? (String) value : null;
        assertNull(string, "XML toString() should convert PropertyObject.NULL to null");

        // now try it with null
        PropertyObject jsonObjectNull = new PropertyObject();
        obj = null;
        jsonObjectNull.put("key", obj);
        value = jsonObjectNull.opt("key");
        assertNull(value, "opt() null should find null");
        try {
            jsonObjectNull.get("key");
            fail("get() null should throw exception");
        } catch (Exception ignored) {
        }
    }

    @Test
    void invalidEscapeSequence() {
        String json = "{ \"\\url\": \"value\" }";
        assertThrows(
                PropertyException.class, () -> new PropertyObject(json), "Expected an exception");
    }

    /** Exercise PropertyObject toMap() method. */
    @Test
    void toMap() {
        String jsonObjectStr =
                "{"
                        + "\"key1\":"
                        + "[1,2,"
                        + "{\"key3\":true}"
                        + "],"
                        + "\"key2\":"
                        + "{\"key1\":\"val1\",\"key2\":"
                        + "{\"key2\":null},"
                        + "\"key3\":42"
                        + "},"
                        + "\"key3\":"
                        + "["
                        + "[\"value1\",2.1]"
                        + ","
                        + "[null]"
                        + "]"
                        + "}";

        PropertyObject jsonObject = new PropertyObject(jsonObjectStr);
        Map<?, ?> map = jsonObject.toMap();

        assertNotNull(map, "Map should not be null");
        assertEquals(3, map.size(), "Map should have 3 elements");

        List<?> key1List = (List<?>) map.get("key1");
        assertNotNull(key1List, "key1 should not be null");
        assertEquals(3, key1List.size(), "key1 list should have 3 elements");
        assertEquals(1, key1List.get(0), "key1 value 1 should be 1");
        assertEquals(2, key1List.get(1), "key1 value 2 should be 2");

        Map<?, ?> key1Value3Map = (Map<?, ?>) key1List.get(2);
        assertNotNull(key1Value3Map, "Map should not be null");
        assertEquals(1, key1Value3Map.size(), "Map should have 1 element");
        assertEquals(Boolean.TRUE, key1Value3Map.get("key3"), "Map key3 should be true");

        Map<?, ?> key2Map = (Map<?, ?>) map.get("key2");
        assertNotNull(key2Map, "key2 should not be null");
        assertEquals(3, key2Map.size(), "key2 map should have 3 elements");
        assertEquals("val1", key2Map.get("key1"), "key2 map key 1 should be val1");
        assertEquals(42, key2Map.get("key3"), "key2 map key 3 should be 42");

        Map<?, ?> key2Val2Map = (Map<?, ?>) key2Map.get("key2");
        assertNotNull(key2Val2Map, "key2 map key 2 should not be null");
        assertTrue(key2Val2Map.containsKey("key2"), "key2 map key 2 should have an entry");
        assertNull(key2Val2Map.get("key2"), "key2 map key 2 value should be null");

        List<?> key3List = (List<?>) map.get("key3");
        assertNotNull(key3List, "key3 should not be null");
        assertEquals(2, key3List.size(), "key3 list should have 3 elements");

        List<?> key3Val1List = (List<?>) key3List.get(0);
        assertNotNull(key3Val1List, "key3 list val 1 should not be null");
        assertEquals(2, key3Val1List.size(), "key3 list val 1 should have 2 elements");
        assertEquals(
                "value1", key3Val1List.get(0), "key3 list val 1 list element 1 should be value1");
        assertEquals(
                key3Val1List.get(1),
                Double.valueOf("2.1"),
                "key3 list val 1 list element 2 should be 2.1");

        List<?> key3Val2List = (List<?>) key3List.get(1);
        assertNotNull(key3Val2List, "key3 list val 2 should not be null");
        assertEquals(1, key3Val2List.size(), "key3 list val 2 should have 1 element");
        assertNull(key3Val2List.get(0), "key3 list val 2 list element 1 should be null");

        // Assert that toMap() is a deep copy
        jsonObject.getJSONArray("key3").getJSONArray(0).put(0, "still value 1");
        assertEquals(
                "value1", key3Val1List.get(0), "key3 list val 1 list element 1 should be value1");

        // assert that the new map is mutable
        assertNotNull(map.remove("key3"), "Removing a key should succeed");
        assertEquals(2, map.size(), "Map should have 2 elements");
    }

    @Test
    void testPutNullBoolean() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, false));
    }

    @Test
    void testPutNullCollection() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        Set<Object> emptySet = Collections.emptySet();
        assertThrows(
                NullPointerException.class, () -> jsonObject.put(null, emptySet));
    }

    @Test
    void testPutNullDouble() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, 0.0d));
    }

    @Test
    void testPutNullFloat() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, 0.0f));
    }

    @Test
    void testPutNullInt() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, 0));
    }

    @Test
    void testPutNullLong() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, 0L));
    }

    @Test
    void testPutNullMap() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        Map<Object, Object> emptyMap = Collections.emptyMap();
        assertThrows(
                NullPointerException.class, () -> jsonObject.put(null, emptyMap));
    }

    @Test
    void testPutNullObject() {
        // null put key
        PropertyObject jsonObject = new PropertyObject("{}");
        Object obj = new Object();
        assertThrows(NullPointerException.class, () -> jsonObject.put(null, obj));
    }

    @Test
    void propertyObject_tokenizer_havingCommaAfterLastpair() {
        PropertyTokener pt = new PropertyTokener("{a:b,}");
        PropertyObject jsonObject = new PropertyObject(pt);
        assertEquals("b", jsonObject.getString("a"));
    }

    @Test
    void nullKeyIsNotAllowed_creatingObjectWithMap() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "");
        assertThrows(NullPointerException.class, () -> new PropertyObject(map));
    }

    @Test
    void quoteCanHandleTab() {
        String ret = PropertyObject.quote("b\t");
        assertEquals("\"b\\t\"", ret);
    }

    @Test
    void stringToNumberCanReturnBigDecimal_numberUsingENotation() {
        Number n = PropertyObject.stringToNumber("4.9E324");
        assertTrue(n instanceof BigDecimal);
    }

    @Test
    void stringToNumberCanReturnBigInteger() {
        Number n = PropertyObject.stringToNumber("9999999999999999999999999999999999999999999");
        assertTrue(n instanceof BigInteger);
    }

    @Test
    void canWrapCollectionIntoPropertyArray() {
        Object o = PropertyObject.wrap(Collections.singleton("1"));
        assertTrue(o instanceof PropertyArray);
        assertEquals("1", ((PropertyArray) o).getString(0));
    }

    @Test
    void canWrapArrayIntoPropertyArray() {
        Object o = PropertyObject.wrap(new String[] {"2"});
        assertTrue(o instanceof PropertyArray);
        assertEquals("2", ((PropertyArray) o).getString(0));
    }

    @Test
    void canWrapMapIntoPropertyObject() {
        Object o = PropertyObject.wrap(Collections.singletonMap("key", "value"));
        assertTrue(o instanceof PropertyObject);
        assertEquals("value", ((PropertyObject) o).getString("key"));
    }

    @Test
    void wrapWillReturnEmptyPropertyObject_UnknownTypes() {
        Object o = PropertyObject.wrap(new Thread());
        assertTrue(o instanceof PropertyObject);
        assertTrue(((PropertyObject) o).isEmpty());
    }

    @Test
    void nullWillHaveHashCodeZebo() {
        assertEquals(0, PropertyObject.NULL.hashCode());
    }

    @Test
    void nullToString_null() {
        assertEquals("null", PropertyObject.NULL.toString());
    }

    @Test
    void propertyObject_differentKeySet_notSimilar() {
        assertFalse(
                new PropertyObject()
                        .similar(new PropertyObject(Collections.singletonMap("1", "2"))));
    }

    @Test
    void nullAndEmptyValuesAreNotEqual() {
        PropertyObject nullVal = new PropertyObject();
        nullVal.put("a", PropertyObject.NULL);
        PropertyObject.wrap(null);
        PropertyObject emptyVal = new PropertyObject("{a:\"\"}");
        assertFalse(nullVal.similar(emptyVal));
    }

    @Test
    void nestedPropertyObjectsCanBeCheckForSimilar() {
        PropertyObject obj1 = new PropertyObject();
        obj1.put("a", new PropertyObject("{c:1}"));
        PropertyObject obj2 =
                new PropertyObject(Collections.singletonMap("a", new PropertyObject()));
        assertFalse(obj1.similar(obj2));
    }

    @Test
    void nestedPropertyArrayCanBeCheckForSimilar() {
        PropertyObject obj1 = new PropertyObject();
        obj1.put("a", new PropertyArray("[1]"));
        PropertyObject obj2 =
                new PropertyObject(Collections.singletonMap("a", new PropertyArray("[2]")));
        assertFalse(obj1.similar(obj2));
    }
}
