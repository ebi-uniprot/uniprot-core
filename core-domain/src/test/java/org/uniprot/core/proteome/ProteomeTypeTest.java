package org.uniprot.core.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProteomeTypeTest {
    @Test
    void getName_toDisplayName_areSame() {
        assertSame(ProteomeType.REDUNDANT.getName(), ProteomeType.REDUNDANT.getDisplayName());
    }
}
