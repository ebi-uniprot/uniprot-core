package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryId;

import static org.junit.jupiter.api.Assertions.*;

class UniRefEntryIdImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniRefEntryId obj = new UniRefEntryIdImpl();
    assertNotNull(obj);
  }
}