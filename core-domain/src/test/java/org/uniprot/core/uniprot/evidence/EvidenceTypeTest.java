package org.uniprot.core.uniprot.evidence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTypeTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    EvidenceType obj = new EvidenceType();
    assertNotNull(obj);
  }

  @Test
  void twoObjsWithNullNamesAreEqual() {
    assertEquals(new EvidenceType(null), new EvidenceType(null));
  }
}