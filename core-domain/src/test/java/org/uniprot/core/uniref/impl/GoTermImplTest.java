package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.GoTerm;

import static org.junit.jupiter.api.Assertions.*;

class GoTermImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    GoTerm obj = new GoTermImpl();
    assertNotNull(obj);
  }
}