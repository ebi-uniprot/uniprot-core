package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        PositionalFeature positionalFeature = new PositionalFeatureImpl();
        assertNotNull(positionalFeature);
        assertNull(positionalFeature.getPattern());
        assertNull(positionalFeature.getPosition());
        assertNull(positionalFeature.getType());
        assertNull(positionalFeature.getLigand());
        assertNull(positionalFeature.getLigandPart());
        assertNull(positionalFeature.getDescription());
        assertFalse(positionalFeature.isInGroup());
    }

    @Test
    void testCreateObject() {
        String pattern = "sample pattern";
        Range range = new Range(1, 2);
        String type = "sample type";
        String value = "sample valaue";
        Ligand ligand = new LigandBuilder().build();
        LigandPart ligandPart = new LigandPartBuilder().build();
        String description = "sample description";
        boolean inGroup = true;
        PositionalFeature positionalFeature =
                new PositionalFeatureImpl(
                        range, pattern, inGroup, ligand, ligandPart, description, type);
        assertNotNull(positionalFeature);
        assertEquals(range, positionalFeature.getPosition());
        assertEquals(pattern, positionalFeature.getPattern());
        assertEquals(inGroup, positionalFeature.isInGroup());
        assertEquals(ligand, positionalFeature.getLigand());
        assertEquals(ligandPart, positionalFeature.getLigandPart());
        assertEquals(description, positionalFeature.getDescription());
        assertEquals(type, positionalFeature.getType());
    }
}
