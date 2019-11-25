package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcId;

import static org.junit.jupiter.api.Assertions.*;

class UniParcIdImplTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniParcId obj = new UniParcIdImpl();
    assertNotNull(obj);
  }
}