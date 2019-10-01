package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MessageDigestUtilTest {

    @Test
    void testGetMd5Digest() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getMD5("abc"));
    }

    @Test
    void forWrongTypeGetDigest_returnSameString() {
        assertEquals("abc", MessageDigestUtil.getDigest("abc", "abc"));
    }

    @Test
    void canNotGetMd5OfNullString(){
        assertThrows(NullPointerException.class, ()-> MessageDigestUtil.getMD5(null));
    }

    @Test
    void canGetMd5OfEmptyString(){
        assertEquals("D41D8CD98F00B204E9800998ECF8427E", MessageDigestUtil.getMD5(""));
    }
}
