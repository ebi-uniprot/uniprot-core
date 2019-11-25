package org.uniprot.core.proteome;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SuperkingdomTest {

  @Test
  void getName_toDisplayName_areSame() {
    assertSame(Superkingdom.EUKARYOTA.getName(), Superkingdom.EUKARYOTA.toDisplayName());
  }

  @Nested
  class typeOf {

    @Test
    void canConvertLowerCase() {
      assertEquals(Superkingdom.ARCHAEA, Superkingdom.fromValue("archaea"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(Superkingdom.VIRUSES, Superkingdom.fromValue("VIRUSES"));
    }

    @Test
    void canConvertMixCase() {
      assertEquals(Superkingdom.BACTERIA, Superkingdom.fromValue("BActErIa"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
    void forOthersWillReturnUnknown(String val) {
      assertThrows(IllegalArgumentException.class, () -> Superkingdom.fromValue(val));
    }

    @Test
    void forNullWillReturnUnknown() {
      assertThrows(IllegalArgumentException.class, () -> Superkingdom.fromValue(null));
    }
  }
}