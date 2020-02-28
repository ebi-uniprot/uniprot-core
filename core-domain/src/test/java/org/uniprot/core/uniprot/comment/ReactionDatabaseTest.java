package org.uniprot.core.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReactionDatabaseTest {
    @Test
    void getName_displayName_areSame() {
        assertSame(ReactionDatabase.CHEBI.getName(), ReactionDatabase.CHEBI.toDisplayName());
    }

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(ReactionDatabase.RHEA, ReactionDatabase.typeOf("rhea"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ReactionDatabase.CHEBI, ReactionDatabase.typeOf("CHEBI"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ReactionDatabase.RHEA, ReactionDatabase.typeOf("RheA"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willBeUnknown(String val) {
            assertEquals(ReactionDatabase.UNKNOWN, ReactionDatabase.typeOf(val));
        }

        @Test
        void willBeUnknown_null() {
            assertEquals(ReactionDatabase.UNKNOWN, ReactionDatabase.typeOf(null));
        }
    }
}
