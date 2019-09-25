package org.uniprot.core.util;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.util.MessageDigestUtil;

class MessageDigestUtilTest {

  @Test
  void testGetMd5Digest() {
    assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getMD5("abc"));
  }

  @Test
  void forWrongTypeGetDigest_returnSameString() {
    assertEquals("abc", MessageDigestUtil.getDigest("abc", "abc"));
  }
}
