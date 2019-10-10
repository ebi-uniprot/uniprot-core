package org.uniprot.core.uniprot.description;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FlagTypeTest {
    @Test
    void getValueAndToDisplay_areSame() {
        assertSame(FlagType.FRAGMENT.getValue(), FlagType.FRAGMENT.toDisplayName());
    }
}
