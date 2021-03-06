package org.uniprot.core.cv.xdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

class UniProtKBDatabaseCategoryTest {

    @ParameterizedTest
    @EnumSource(UniProtDatabaseCategory.class)
    void getDisplay_toDisplay_areSame(UniProtDatabaseCategory category) {
        assertSame(category.getDisplayName(), category.getDisplayName());
    }

    @ParameterizedTest
    @EnumSource(UniProtDatabaseCategory.class)
    void ontology_proteomes_unknow_areNotSearchAble(UniProtDatabaseCategory category) {
        if (category == UniProtDatabaseCategory.UNKNOWN
                || category == UniProtDatabaseCategory.GENE_ONTOLOGY_DATABASES
                || category == UniProtDatabaseCategory.PROTEOMES_DATABASES)
            assertFalse(category.isSearchable());
        else assertTrue(category.isSearchable());
    }

    @ParameterizedTest
    @EnumSource(UniProtDatabaseCategory.class)
    void everyCategoryShouldHaveName(UniProtDatabaseCategory category) {
        assertNotNull(category.getName());
    }

    @Test
    void typeOfNeedNameAsParam() {
        assertEquals(
                UniProtDatabaseCategory.ORGANISM_SPECIFIC_DATABASES,
                UniProtDatabaseCategory.typeOf("ORG"));
        assertEquals(
                UniProtDatabaseCategory.SEQUENCE_DATABASES, UniProtDatabaseCategory.typeOf("SEQ"));
        assertEquals(
                UniProtDatabaseCategory.FAMILY_AND_DOMAIN_DATABASES,
                UniProtDatabaseCategory.typeOf("FMD"));
    }

    @Test
    void matchIsCaseInsencetive() {
        assertEquals(
                UniProtDatabaseCategory.ORGANISM_SPECIFIC_DATABASES,
                UniProtDatabaseCategory.typeOf("org"));

        assertEquals(
                UniProtDatabaseCategory.SEQUENCE_DATABASES, UniProtDatabaseCategory.typeOf("sEq"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  ", "", "FMG", "not exist"})
    void typeOfName_existMatch(String val) {
        assertThrows(IllegalArgumentException.class, () -> UniProtDatabaseCategory.typeOf(val));
    }

    @Test
    void typeOfName_nullVal() {
        assertThrows(IllegalArgumentException.class, () -> UniProtDatabaseCategory.typeOf(null));
    }
}
