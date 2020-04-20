package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        PositionalFeature positionalFeature = new PositionalFeatureImpl();
        assertNotNull(positionalFeature);
        assertNull(positionalFeature.getPattern());
        assertNull(positionalFeature.getPosition());
        assertNull(positionalFeature.getType());
        assertNull(positionalFeature.getValue());
        assertFalse(positionalFeature.isInGroup());
    }

    @Test
    void testCreateObject() {
        String pattern = "sample pattern";
        Range range = new FeatureLocation(1, 2);
        String type = "sample type";
        String value = "sample valaue";
        boolean inGroup = true;
        PositionalFeature positionalFeature =
                new PositionalFeatureImpl(range, pattern, inGroup, value, type);
        assertNotNull(positionalFeature);
        assertEquals(range, positionalFeature.getPosition());
        assertEquals(pattern, positionalFeature.getPattern());
        assertEquals(inGroup, positionalFeature.isInGroup());
        assertEquals(value, positionalFeature.getValue());
        assertEquals(type, positionalFeature.getType());
    }
}
