package org.uniprot.core.taxonomy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxonomyInactiveReasonTypeTest {
    @Test
    void name_toDisplayName_areSame() {
        assertSame(
                TaxonomyInactiveReasonType.MERGED.name(),
                TaxonomyInactiveReasonType.MERGED.getDisplayName());
    }
}
