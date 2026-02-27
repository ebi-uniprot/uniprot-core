package org.uniprot.core.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UniParcDatabaseTest {
    @Nested
    class typeOf {

        @Test
        void canConvertLowerCase() {
            assertEquals(UniParcDatabase.ENSEMBL_VERTEBRATE, UniParcDatabase.typeOf("ensembl"));
        }

        @Test
        void canConvertUpperCase() {
            assertEquals(UniParcDatabase.IPI, UniParcDatabase.typeOf("IPI"));
        }

        @Test
        void canConvertBothCases() {
            assertEquals(UniParcDatabase.SGD, UniParcDatabase.typeOf("SgD"));
        }

        @ParameterizedTest
        @ValueSource(
                strings = {"evidenceat protein level", "UNIPARCID", "", "abc", "  ", "SwissProt"})
        void forOthersWillReturnUnknown(String val) {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabase.typeOf(val));
        }

        @Test
        void forNullWillReturnUnknown() {
            assertThrows(IllegalArgumentException.class, () -> UniParcDatabase.typeOf(null));
        }
    }

    @Test
    void getName_toDisplayName_areSame() {
        assertSame(UniParcDatabase.VEGA.getName(), UniParcDatabase.VEGA.getDisplayName());
    }

    @Test
    void canTellWhichDataBaseAlive() {
        assertTrue(UniParcDatabase.TAIR_ARABIDOPSIS.isAlive());
    }

    @Test
    void hasUrl() {
        assertEquals("https://www.ensembl.org/id/%id", UniParcDatabase.ENSEMBL_VERTEBRATE.getUrl());
    }

    @Test
    void hasNoUrl() {
        assertEquals("", UniParcDatabase.VECTORBASE.getUrl());
    }

    @Test
    void isSource() {
        assertTrue( UniParcDatabase.EMBL.isSource());
    }

    @Test
    void isNotSource() {
        assertFalse( UniParcDatabase.FLYBASE.isSource());
    }

    @Test
    void canGetIndex() {
        assertEquals(100, UniParcDatabase.SWISSPROT.getIndex());
    }

    @Test
    void uniprotDatabaseIsTheLowestIndex() {
        List<UniParcDatabase> sortedValues =
                Arrays.stream(UniParcDatabase.values())
                        .sorted(
                                (db1, db2) -> {
                                    if (db1.getIndex() == db2.getIndex()) {
                                        return db1.getName().compareTo(db2.getName());
                                    } else {
                                        return db1.getIndex() - db2.getIndex();
                                    }
                                })
                        .collect(Collectors.toList());
        assertNotNull(sortedValues);
        assertEquals(UniParcDatabase.SWISSPROT, sortedValues.get(0));
        assertEquals(UniParcDatabase.TREMBL, sortedValues.get(1));
        assertEquals(UniParcDatabase.SWISSPROT_VARSPLIC, sortedValues.get(2));
    }

    @Test
    void checkOtherIndexesAreSorted() {
        List<UniParcDatabase> sortedValuesByIndex =
                Arrays.stream(UniParcDatabase.values())
                        .filter(
                                db ->
                                        !db.equals(UniParcDatabase.SWISSPROT)
                                                && !db.equals(UniParcDatabase.SWISSPROT_VARSPLIC)
                                                && !db.equals(UniParcDatabase.TREMBL))
                        .sorted(Comparator.comparingInt(UniParcDatabase::getIndex))
                        .collect(Collectors.toList());

        List<UniParcDatabase> sortedValuesByName =
                Arrays.stream(UniParcDatabase.values())
                        .filter(
                                db ->
                                        !db.equals(UniParcDatabase.SWISSPROT)
                                                && !db.equals(UniParcDatabase.SWISSPROT_VARSPLIC)
                                                && !db.equals(UniParcDatabase.TREMBL))
                        .sorted(Comparator.comparing(this::getCleanDisplayName))
                        .collect(Collectors.toList());

        assertEquals(sortedValuesByIndex, sortedValuesByName);
    }

    private String getCleanDisplayName(UniParcDatabase db1) {
        return db1.getDisplayName().toLowerCase().replaceAll("_", "").replaceAll(" ", "");
    }
}
