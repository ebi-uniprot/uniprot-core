package org.uniprot.core.citation.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Journal;

import static org.junit.jupiter.api.Assertions.*;

class JournalImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    Journal obj = new JournalImpl();
    assertNotNull(obj);
  }
}