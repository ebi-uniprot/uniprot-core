package org.uniprot.core.taxonomy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TaxonomyRankTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(TaxonomyRank.SUPERORDER.getName(), TaxonomyRank.SUPERORDER.getDisplayName());
    }

    @Test
    void typeOfCanConvertFromName() {
        assertEquals(TaxonomyRank.NO_RANK, TaxonomyRank.typeOf("No Rank"));
        assertEquals(TaxonomyRank.FAMILY, TaxonomyRank.typeOf("family"));
        assertEquals(TaxonomyRank.CLASS, TaxonomyRank.typeOf("CLASS"));
        assertEquals(TaxonomyRank.REALM, TaxonomyRank.typeOf("realm"));
        assertEquals(TaxonomyRank.DOMAIN, TaxonomyRank.typeOf("domain"));
        assertEquals(TaxonomyRank.SUBVARIETY, TaxonomyRank.typeOf("subvariety"));
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "not exit"})
    void typeOfWillThrowException_notMatched(String val) {
        assertThrows(IllegalArgumentException.class, () -> TaxonomyRank.typeOf(val));
    }
}
