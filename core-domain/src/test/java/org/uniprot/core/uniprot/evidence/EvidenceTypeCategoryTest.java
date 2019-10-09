package org.uniprot.core.uniprot.evidence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTypeCategoryTest {
  @Test
  void I() {
    assertEquals("I", EvidenceTypeCategory.I.toDisplayName());
  }

  @Test
  void C() {
    assertEquals("C", EvidenceTypeCategory.C.toDisplayName());
  }

  @Test
  void A() {
    assertEquals("A", EvidenceTypeCategory.A.toDisplayName());
  }
}