package uk.ac.ebi.uniprot.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageDigestUtilTest {

    @Test
    public void testGetDigest() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getDigest("abc", "MD5"));

    }

    @Test
    public void testMD5() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getMD5("abc"));

    }

    @Test
    public void testGetDigestFromBytes() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getDigest("abc".getBytes(), "MD5"));

    }

}
