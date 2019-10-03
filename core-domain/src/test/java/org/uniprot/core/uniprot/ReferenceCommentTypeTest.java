package org.uniprot.core.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ReferenceCommentTypeTest {

    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(ReferenceCommentType.STRAIN, ReferenceCommentType.typeOf("strain"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(ReferenceCommentType.PLASMID, ReferenceCommentType.typeOf("PLASMID"));
        }

        @Test
        void canConvertMixCase() {
            assertEquals(ReferenceCommentType.TISSUE, ReferenceCommentType.typeOf("tIssUE"));
        }

        @ParameterizedTest
        @ValueSource(strings = {"uniprotkbid", "UNIPARCID", "", "abc", "  "})
        void willThrowException(String val) {
            assertThrows(IllegalArgumentException.class, () -> ReferenceCommentType.typeOf(val));
        }

        @Test
        void willThrowException_null() {
            assertThrows(IllegalArgumentException.class, () -> ReferenceCommentType.typeOf(null));
        }
    }

    @Test
    void getValue_displayName_areSame() {
        assertSame(
                ReferenceCommentType.TRANSPOSON.getValue(),
                ReferenceCommentType.TRANSPOSON.toDisplayName());
    }
}
