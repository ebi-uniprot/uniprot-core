package org.uniprot.core.uniprot;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UniProtEntryTypeTest {

  @Nested
  class typeOf{

    @Test
    void canConvertLowerCase() {
      assertEquals(UniProtEntryType.TREMBL, UniProtEntryType.typeOf("trembl"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(UniProtEntryType.INACTIVE, UniProtEntryType.typeOf("INACTIVE"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  ", "SwissProt"})
    void forOthersWillReturnUnknown(String val) {
      assertEquals(UniProtEntryType.UNKNOWN, UniProtEntryType.typeOf(val));
    }

    @Test
    void forNullWillReturnUnknown() {
      assertEquals(UniProtEntryType.UNKNOWN, UniProtEntryType.typeOf(null));
    }
  }

  @Test
  void getValue_displayName_areSame(){
    assertSame(UniProtEntryType.SWISSPROT.getValue(), UniProtEntryType.SWISSPROT.toDisplayName());
  }

}