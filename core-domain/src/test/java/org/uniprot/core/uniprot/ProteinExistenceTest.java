package org.uniprot.core.uniprot;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ProteinExistenceTest {

  @Nested
  class typeOf{

    @Test
    void canConvertLowerCase() {
      assertEquals(ProteinExistence.PROTEIN_LEVEL, ProteinExistence.typeOf("evidence at protein level"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(ProteinExistence.UNCERTAIN, ProteinExistence.typeOf("UNCERTAIN"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
    void forOthersWillReturnUnknown(String val) {
      assertEquals(ProteinExistence.UNKNOWN, ProteinExistence.typeOf(val));
    }

    @Test
    void forNullWillReturnUnknown() {
      assertEquals(ProteinExistence.UNKNOWN, ProteinExistence.typeOf(null));
    }
  }

  @Test
  void getValue_displayName_areNotSame(){
    assertNotEquals(ProteinExistence.TRANSCRIPT_LEVEL.getValue(), ProteinExistence.TRANSCRIPT_LEVEL.toDisplayName());
  }

  @Test
  void getValue_toString_areSame(){
    assertSame(ProteinExistence.HOMOLOGY.getValue(), ProteinExistence.HOMOLOGY.toString());
  }

}