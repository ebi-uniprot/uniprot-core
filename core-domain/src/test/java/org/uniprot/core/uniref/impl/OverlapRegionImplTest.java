package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.OverlapRegion;

import static org.junit.jupiter.api.Assertions.*;

class OverlapRegionImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    OverlapRegion obj = new OverlapRegionImpl();
    assertNotNull(obj);
  }
}