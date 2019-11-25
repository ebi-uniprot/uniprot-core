package org.uniprot.core.proteome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeTypeTest {
  @Test
  void getName_toDisplayName_areSame() {
    assertSame(ProteomeType.REDUNDANT.getName(), ProteomeType.REDUNDANT.toDisplayName());
  }
}