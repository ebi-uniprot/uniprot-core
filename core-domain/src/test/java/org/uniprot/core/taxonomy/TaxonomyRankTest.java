package org.uniprot.core.taxonomy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxonomyRankTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(TaxonomyRank.SUPERORDER.getName(), TaxonomyRank.SUPERORDER.toDisplayName());
    }
}
