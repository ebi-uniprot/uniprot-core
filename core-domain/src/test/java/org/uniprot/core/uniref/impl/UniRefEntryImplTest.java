package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntry;

import static org.junit.jupiter.api.Assertions.*;

class UniRefEntryImplTest {


  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniRefEntry obj = new UniRefEntryImpl();
    assertNotNull(obj);
    assertTrue(obj.getGoTerms().isEmpty());
    assertTrue(obj.getMembers().isEmpty());
  }
}