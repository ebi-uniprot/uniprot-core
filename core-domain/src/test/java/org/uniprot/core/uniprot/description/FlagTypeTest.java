package org.uniprot.core.uniprot.description;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlagTypeTest {
  @Test
  void getValueAndToDisplay_areSame() {
    assertSame(FlagType.FRAGMENT.getValue(), FlagType.FRAGMENT.toDisplayName());
  }
}