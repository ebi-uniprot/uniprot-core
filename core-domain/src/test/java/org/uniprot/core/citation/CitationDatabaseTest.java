package org.uniprot.core.citation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CitationDatabaseTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(CitationDatabase.AGRICOLA.getName(), CitationDatabase.AGRICOLA.getDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(CitationDatabase.DOI, CitationDatabase.typeOf("doi"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(CitationDatabase.AGRICOLA, CitationDatabase.typeOf("AGRICOLA"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(CitationDatabase.PUBMED, CitationDatabase.typeOf("PubMed"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprot", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> CitationDatabase.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> CitationDatabase.typeOf(null));
        }
    }
}
