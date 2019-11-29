package org.uniprot.core.uniprot.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;

class FeatureLocationTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        FeatureLocation obj = new FeatureLocation();
        assertNotNull(obj);
    }

    @Test
    void canCreateWithOutSequence() {
        FeatureLocation obj =
                new FeatureLocation(null, null, PositionModifier.EXACT, PositionModifier.UNSURE);

        assertNull(obj.getSequence());

        assertNotNull(obj.getStart());
        assertNull(obj.getStart().getValue());
        assertEquals(PositionModifier.UNKNOWN, obj.getStart().getModifier());

        assertNotNull(obj.getEnd());
        assertNull(obj.getEnd().getValue());
        assertEquals(PositionModifier.UNKNOWN, obj.getEnd().getModifier());
    }
}
