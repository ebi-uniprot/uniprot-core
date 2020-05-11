package org.uniprot.core.util.property;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PropertyTokenerTest {
    private static final String KEY_VALUE = "{\"key\":\"value\"}";
    private PropertyTokener tokener;

    /**
     * verify that back() fails as expected.
     *
     * @throws IOException thrown if something unexpected happens.
     */
    @Test
    void verifyBackFailureZeroIndex() throws IOException {
        try (Reader reader = new StringReader("some test string")) {
            final PropertyTokener tokener = new PropertyTokener(reader);
            try {
                // this should fail since the index is 0;
                tokener.back();
                fail("Expected an exception");
            } catch (PropertyException e) {
                assertEquals("Stepping back two steps is not supported", e.getMessage());
            } catch (Exception e) {
                fail(
                        "Unknown Exception type "
                                + e.getClass().getCanonicalName()
                                + " with message "
                                + e.getMessage());
            }
        }
    }

    /**
     * verify that back() fails as expected.
     *
     * @throws IOException thrown if something unexpected happens.
     */
    @Test
    void verifyBackFailureDoubleBack() throws IOException {
        try (Reader reader = new StringReader("some test string")) {
            final PropertyTokener tokener = new PropertyTokener(reader);
            tokener.next();
            tokener.back();
            try {
                // this should fail since the index is 0;
                tokener.back();
                fail("Expected an exception");
            } catch (PropertyException e) {
                assertEquals("Stepping back two steps is not supported", e.getMessage());
            } catch (Exception e) {
                fail(
                        "Unknown Exception type "
                                + e.getClass().getCanonicalName()
                                + " with message "
                                + e.getMessage());
            }
        }
    }

    @Test
    void testValid() {
        checkValid("0", Number.class);
        checkValid(" 0  ", Number.class);
        checkValid("23", Number.class);
        checkValid("23.5", Number.class);
        checkValid(" 23.5  ", Number.class);
        checkValid("null", null);
        checkValid(" null  ", null);
        checkValid("true", Boolean.class);
        checkValid(" true\n", Boolean.class);
        checkValid("false", Boolean.class);
        checkValid("\nfalse  ", Boolean.class);
        checkValid("{}", PropertyObject.class);
        checkValid(" {}  ", PropertyObject.class);
        checkValid("{\"a\":1}", PropertyObject.class);
        checkValid(" {\"a\":1}  ", PropertyObject.class);
        checkValid("[]", PropertyArray.class);
        checkValid(" []  ", PropertyArray.class);
        checkValid("[1,2]", PropertyArray.class);
        checkValid("\n\n[1,2]\n\n", PropertyArray.class);
        checkValid("1 2", String.class);
    }

    @Test
    void testErrors() {
        // Check that stream can detect that a value is found after
        // the first one
        checkError(" { \"a\":1 }  4 ");
        checkError("null \"a\"");
        checkError("{} true");
    }

    private void checkValid(String testStr, Class<?> aClass) {
        Object result = nextValue(testStr);

        // Check class of object returned
        if (null == aClass) {
            if (!PropertyObject.NULL.equals(result)) {
                throw new PropertyException(
                        "Unexpected class: " + result.getClass().getSimpleName());
            }
        } else {
            if (null == result) {
                throw new PropertyException("Unexpected null result");
            } else if (!aClass.isAssignableFrom(result.getClass())) {
                throw new PropertyException(
                        "Unexpected class: " + result.getClass().getSimpleName());
            }
        }
    }

    private void checkError(String testStr) {
        try {
            nextValue(testStr);

            fail("Error should be triggered: (\"" + testStr + "\")");
        } catch (PropertyException e) {
            // OK
        }
    }

    /**
     * Verifies that PropertyTokener can read a stream that contains a value. After the reading is
     * done, check that the stream is left in the correct state by reading the characters after. All
     * valid cases should reach end of stream.
     *
     * @param testStr string to parse
     * @return Object
     * @throws PropertyException when value not found or unexpected token
     */
    private Object nextValue(String testStr) throws PropertyException {
        try (StringReader sr = new StringReader(testStr)) {
            PropertyTokener tokener = new PropertyTokener(sr);

            Object result = tokener.nextValue();

            if (result == null) {
                throw new PropertyException(
                        "Unable to find value token in JSON stream: (" + tokener + "): " + testStr);
            }

            char c = tokener.nextClean();
            if (0 != c) {
                throw new PropertyException(
                        "Unexpected character found at end of JSON stream: "
                                + c
                                + " ("
                                + tokener
                                + "): "
                                + testStr);
            }

            return result;
        }
    }

    /**
     * Tests the failure of the skipTo method with a buffered reader. Preferably we'd like this not
     * to fail but at this time we don't have a good recovery.
     *
     * @throws IOException thrown if something unexpected happens.
     */
    @Test
    void testSkipToFailureWithBufferedReader() throws IOException {
        final byte[] superLongBuffer = new byte[1000001];
        // fill our buffer
        for (int i = 0; i < superLongBuffer.length; i++) {
            superLongBuffer[i] = 'A';
        }
        try (Reader reader =
                new BufferedReader(
                        new InputStreamReader(new ByteArrayInputStream(superLongBuffer)))) {
            final PropertyTokener tokener = new PropertyTokener(reader);
            try {
                // this should fail since the internal markAhead buffer is only 1,000,000
                // but 'B' doesn't exist in our buffer that is 1,000,001 in size
                tokener.skipTo('B');
                fail("Expected exception");
            } catch (PropertyException e) {
                assertEquals("Mark invalid", e.getMessage());
            } catch (Exception e) {
                fail(
                        "Unknown Exception type "
                                + e.getClass().getCanonicalName()
                                + " with message "
                                + e.getMessage());
            }
        }
    }

    /**
     * Tests the success of the skipTo method with a String reader.
     *
     * @throws IOException thrown if something unexpected happens.
     */
    @Test
    void testSkipToSuccessWithStringReader() throws IOException {
        final StringBuilder superLongBuffer = new StringBuilder(1000001);
        // fill our buffer
        for (int i = 0; i < superLongBuffer.length(); i++) {
            superLongBuffer.append('A');
        }
        try (Reader reader = new StringReader(superLongBuffer.toString())) {
            final PropertyTokener tokener = new PropertyTokener(reader);
            try {
                // this should not fail since the internal markAhead is ignored for StringReaders
                tokener.skipTo('B');
            } catch (Exception e) {
                fail(
                        "Unknown Exception type "
                                + e.getClass().getCanonicalName()
                                + " with message "
                                + e.getMessage());
            }
        }
    }

    /**
     * Verify that next and back are working properly and tracking the correct positions with
     * different new line combinations.
     */
    @Test
    void testNextBackComboWithNewLines() {
        final String testString = "this is\nA test\r\nWith some different\rNew Lines";
        //                         ^       ^         ^                    ^
        // index positions         0       8        16                   36
        final PropertyTokener tokener = new PropertyTokener(testString);
        assertEquals(" at 0 [character 1 line 1]", tokener.toString());
        assertEquals('t', tokener.next());
        assertEquals(" at 1 [character 2 line 1]", tokener.toString());
        tokener.skipTo('\n');
        assertEquals(" at 7 [character 8 line 1]", tokener.toString());
        assertEquals('\n', tokener.next());
        assertEquals(" at 8 [character 0 line 2]", tokener.toString());
        assertEquals('A', tokener.next());
        assertEquals(" at 9 [character 1 line 2]", tokener.toString());
        tokener.back();
        assertEquals(" at 8 [character 0 line 2]", tokener.toString());
        tokener.skipTo('\r');
        assertEquals(" at 14 [character 6 line 2]", tokener.toString());
        // verify \r\n combo doesn't increment the line twice
        assertEquals('\r', tokener.next());
        assertEquals(" at 15 [character 0 line 3]", tokener.toString());
        assertEquals('\n', tokener.next());
        assertEquals(" at 16 [character 0 line 3]", tokener.toString());
        // verify stepping back after reading the \n of an \r\n combo doesn't  increment the line
        // incorrectly
        tokener.back();
        assertEquals(" at 15 [character 6 line 2]", tokener.toString());
        assertEquals('\n', tokener.next());
        assertEquals(" at 16 [character 0 line 3]", tokener.toString());
        assertEquals('W', tokener.next());
        assertEquals(" at 17 [character 1 line 3]", tokener.toString());
        assertEquals('i', tokener.next());
        assertEquals(" at 18 [character 2 line 3]", tokener.toString());
        tokener.skipTo('\r');
        assertEquals(" at 35 [character 19 line 3]", tokener.toString());
        assertEquals('\r', tokener.next());
        assertEquals(" at 36 [character 0 line 4]", tokener.toString());
        tokener.back();
        assertEquals(" at 35 [character 19 line 3]", tokener.toString());
        assertEquals('\r', tokener.next());
        assertEquals(" at 36 [character 0 line 4]", tokener.toString());
        assertEquals('N', tokener.next());
        assertEquals(" at 37 [character 1 line 4]", tokener.toString());

        // verify we get the same data just walking though, no calls to back
        final PropertyTokener t2 = new PropertyTokener(testString);
        for (int i = 0; i < 7; i++) {
            assertTrue(t2.toString().startsWith(" at " + i + " "));
            assertEquals(testString.charAt(i), t2.next());
        }
        assertEquals(" at 7 [character 8 line 1]", t2.toString());
        assertEquals(testString.charAt(7), t2.next());
        assertEquals(" at 8 [character 0 line 2]", t2.toString());
        for (int i = 8; i < 14; i++) {
            assertTrue(t2.toString().startsWith(" at " + i + " "));
            assertEquals(testString.charAt(i), t2.next());
        }
        assertEquals(" at 14 [character 6 line 2]", t2.toString());
        assertEquals('\r', t2.next());
        assertEquals(" at 15 [character 0 line 3]", t2.toString());
        assertEquals('\n', t2.next());
        assertEquals(" at 16 [character 0 line 3]", t2.toString());
        assertEquals('W', t2.next());
        assertEquals(" at 17 [character 1 line 3]", t2.toString());
        for (int i = 17; i < 37; i++) {
            assertTrue(t2.toString().startsWith(" at " + i + " "));
            assertEquals(testString.charAt(i), t2.next());
        }
        assertEquals(" at 37 [character 1 line 4]", t2.toString());
        for (int i = 37; i < testString.length(); i++) {
            assertTrue(t2.toString().startsWith(" at " + i + " "));
            assertEquals(testString.charAt(i), t2.next());
        }
        assertEquals(" at " + testString.length() + " [character 9 line 4]", t2.toString());
        // end of the input
        assertEquals(0, t2.next());
        assertFalse(t2.more());
    }

    @BeforeEach
    void setUp() {
        tokener = new PropertyTokener(KEY_VALUE);
    }

    @Test
    void canCrateFromString() {
        new PropertyTokener(KEY_VALUE);
    }

    @Test
    void skipToCanSkipCharacters() {
        char nextChar = tokener.skipTo('y');
        assertEquals('y', nextChar);
    }

    @Test
    void canCrateSyntaxError() {
        assertAll(
                () -> assertTrue(tokener.syntaxError("problem") instanceof PropertyException),
                () ->
                        assertEquals(
                                "problem at 0 [character 1 line 1]",
                                tokener.syntaxError("problem").getMessage()));
    }

    @Test
    void canCrateSyntaxErrorWithThrowable() {
        Throwable throwable = new Throwable("throwable");
        assertAll(
                () ->
                        assertTrue(
                                tokener.syntaxError("problem", throwable)
                                        instanceof PropertyException),
                () ->
                        assertEquals(
                                "problem at 0 [character 1 line 1]",
                                tokener.syntaxError("problem", throwable).getMessage()));
    }

    @Test
    void toStringWillReturnTheCurrentPositionOfTokener() {
        PropertyTokener t = new PropertyTokener(KEY_VALUE);
        assertEquals(" at 0 [character 1 line 1]", t.toString());

        t.next();
        assertEquals(" at 1 [character 2 line 1]", t.toString());
    }

    @Test
    void whenReaderDonotSupportMark_wrapItWithBufferReader() {
        MockReader pr = new MockReader();
        new PropertyTokener(pr);
    }

    @Test
    // mokito
    void shortCut_back_next() {
        PropertyTokener tokener = new PropertyTokener("ab");
        tokener.next();
        tokener.back();
        tokener.more();
    }

    @Test
    void moreWillReturnTrue_whenTokenerHaveCharacters() {
        PropertyTokener tokener = new PropertyTokener("ab");
        assertTrue(tokener.more());
    }

    @Test
    void moreWillCastExceptionToPropertyException_whenGetExceptionWhileRead() {
        PropertyTokener tokener = new PropertyTokener(new MockReader());
        assertThrows(PropertyException.class, tokener::more);
    }

    @Test
    void moreWillCastExceptionToPropertyException_whenGetExceptionWhileMarking() {
        PropertyTokener tokener = new PropertyTokener(new MockReaderMarkable());
        assertThrows(PropertyException.class, tokener::more);
    }

    @Test
    void nextWillCastExceptionToPropertyException_whenGetExceptionWhileRead() {
        PropertyTokener tokener = new PropertyTokener(new MockReader());
        assertThrows(PropertyException.class, tokener::next);
    }

    @Test
    void emptyString_whenCallingNextIndex0() {
        PropertyTokener tokener = new PropertyTokener(new MockReader());
        assertEquals("", tokener.next(0));
    }

    @Test
    void stringWithSpaces_notValidValue() {
        PropertyTokener tokener = new PropertyTokener("    ");
        assertThrows(PropertyException.class, tokener::nextValue);
    }

    @Test
    void callingNextWithIndexMoreThenSize_causeException() {
        PropertyTokener tokener = new PropertyTokener("");
        assertThrows(PropertyException.class, () -> tokener.next(2));
    }

    @Test
    void canQuote_backSlash_b() {
        PropertyTokener tokener = new PropertyTokener("\\ba");
        assertEquals(Character.toString('\b'), tokener.nextString('a'));
    }

    @Test
    void canQuote_backSlash_t() {
        PropertyTokener tokener = new PropertyTokener("\\ta");
        assertEquals(Character.toString('\t'), tokener.nextString('a'));
    }

    @Test
    void canQuote_backSlash_n() {
        PropertyTokener tokener = new PropertyTokener("\\na");
        assertEquals(Character.toString('\n'), tokener.nextString('a'));
    }

    @Test
    void canQuote_backSlash_f() {
        PropertyTokener tokener = new PropertyTokener("\\fa");
        assertEquals(Character.toString('\f'), tokener.nextString('a'));
    }

    @Test
    void canQuote_backSlash_r() {
        PropertyTokener tokener = new PropertyTokener("\\ra");
        assertEquals(Character.toString('\r'), tokener.nextString('a'));
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\da", "\\$a", "\\qa", "\\^", "\\s", "\\+"})
    void canQuote_afterBackSlash_otherCharacter_notAllowed(String token) {
        PropertyTokener tokener = new PropertyTokener(token);
        assertThrows(PropertyException.class, () -> tokener.nextString('a'));
    }

    @Test
    void canQuote_backSlash_forwardSlash() {
        PropertyTokener tokener = new PropertyTokener("\\/a");
        assertEquals(Character.toString('/'), tokener.nextString('a'));
    }

    private static class MockReader extends Reader {

        public int read(char[] cbuf, int off, int len) throws IOException {
            throw new IOException();
        }

        public void mark(int readAheadLimit) throws IOException {
            throw new IOException();
        }

        public void close() {}
    }

    private static class MockReaderMarkable extends MockReader {
        public boolean markSupported() {
            return true;
        }
    }
}
