package org.uniprot.core.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.util.MessageDigestUtil;

class MessageDigestUtilTest {

    @Test
    void testGetDigest() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getDigest("abc", "MD5"));

    }

    @Test
    void testMD5() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getMD5("abc"));

    }

    @Test
    void testGetDigestFromBytes() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getDigest("abc".getBytes(), "MD5"));

    }

}
