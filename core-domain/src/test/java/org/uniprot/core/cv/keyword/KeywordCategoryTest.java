package org.uniprot.core.cv.keyword;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class KeywordCategoryTest {

    @ParameterizedTest
    @EnumSource(KeywordCategory.class)
    void nameAndDisplayNameAreSame(KeywordCategory category) {
        assertSame(category.getName(), category.getDisplayName());
    }

    @ParameterizedTest
    @EnumSource(KeywordCategory.class)
    void nameShouldNotBeNull(KeywordCategory category) {
        assertNotNull(category.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"BIOLOGICAL PROCESS", "diseaseEntry", "ptm"})
    void caseWillIgnoreWhileConvertingFromNameString(String val) {
        assertNotEquals(KeywordCategory.UNKNOWN, KeywordCategory.typeOf(val));
    }

    @Test
    void nullNameWillConsiderUnknown() {
        assertEquals(KeywordCategory.UNKNOWN, KeywordCategory.typeOf(null));
    }
}
