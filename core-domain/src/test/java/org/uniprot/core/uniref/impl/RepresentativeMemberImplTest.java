package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.RepresentativeMember;

import static org.junit.jupiter.api.Assertions.*;

class RepresentativeMemberImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    RepresentativeMember obj = new RepresentativeMemberImpl();
    assertNotNull(obj);
  }
}