package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;

import static org.junit.jupiter.api.Assertions.*;

class UniProtEntryImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniProtEntry obj = new UniProtEntryImpl();
    assertNotNull(obj);
  }
}