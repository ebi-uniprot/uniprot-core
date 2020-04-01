package org.uniprot.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PositionModifierTest {

    @ParameterizedTest
    @EnumSource(PositionModifier.class)
    void toDisplayName_returnSameConst(PositionModifier enm) {
        assertEquals(enm.toString(), enm.getDisplayName());
    }
}
