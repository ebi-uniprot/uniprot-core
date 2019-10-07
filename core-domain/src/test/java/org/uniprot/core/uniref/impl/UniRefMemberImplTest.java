package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefMember;

import static org.junit.jupiter.api.Assertions.*;

class UniRefMemberImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniRefMember obj = new UniRefMemberImpl();
    assertNotNull(obj);
  }
}