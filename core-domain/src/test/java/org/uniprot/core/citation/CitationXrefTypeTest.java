package org.uniprot.core.citation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CitationXrefTypeTest {
  @Test
  void getName_toDisplayName_areSame() {
    assertSame(CitationXrefType.AGRICOLA.getName(), CitationXrefType.AGRICOLA.toDisplayName());
  }

  @Nested
  class typeOf {

    @Test
    void canConvertLowerCase() {
      assertEquals(CitationXrefType.DOI, CitationXrefType.typeOf("doi"));
    }

    @Test
    void canConvertUpperCase() {
      assertEquals(CitationXrefType.AGRICOLA, CitationXrefType.typeOf("AGRICOLA"));
    }

    @Test
    void canConvertMixCase() {
      assertEquals(CitationXrefType.PUBMED, CitationXrefType.typeOf("PubMed"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
    void forOthersWillReturnUnknown(String val) {
      assertThrows(IllegalArgumentException.class, () -> CitationXrefType.typeOf(val));
    }

    @Test
    void forNullWillReturnUnknown() {
      assertThrows(IllegalArgumentException.class, () -> CitationXrefType.typeOf(null));
    }
  }
}