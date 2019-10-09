package org.uniprot.core.uniprot.evidence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTypeDetailTest {
  @Test
  void uriLinkWillBeNeverNull() {
    EvidenceTypeDetail e = new EvidenceTypeDetail();
    assertNotNull(e.getUriLink());
  }

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    EvidenceTypeDetail e = new EvidenceTypeDetail();
    assertNotNull(e);
  }

  @Test
  void defaultObjsAreEqual() {
    EvidenceTypeDetail obj = new  EvidenceTypeDetail();
    EvidenceTypeDetail obj2 = new EvidenceTypeDetail();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }

  @Test
  void constructor_objsAreEqual() {
    EvidenceTypeDetail obj = new  EvidenceTypeDetail("a","b",EvidenceTypeCategory.A,null);
    EvidenceTypeDetail obj2 = new EvidenceTypeDetail("a","b",EvidenceTypeCategory.A,null);
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }

  @Test
  void canGetCategory() {
    EvidenceTypeDetail obj = new  EvidenceTypeDetail("a","b",EvidenceTypeCategory.A,null);
    assertEquals(EvidenceTypeCategory.A, obj.getCategory());
  }
}