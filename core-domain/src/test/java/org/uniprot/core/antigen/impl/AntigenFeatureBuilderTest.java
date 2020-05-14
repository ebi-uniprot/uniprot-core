package org.uniprot.core.antigen.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.antigen.AntigenFeature;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
class AntigenFeatureBuilderTest {

    @Test
    void canSetMatchScore() {
        AntigenFeature obj = new AntigenFeatureBuilder().matchScore(10).build();
        assertNotNull(obj);
        assertEquals(10, obj.getMatchScore());
        assertTrue(obj.hasMatchScore());
    }

    @Test
    void canSetAntigenSequence() {
        String antigenSequence = "AAAAA";
        AntigenFeature feature =
                new AntigenFeatureBuilder().antigenSequence(antigenSequence).build();
        assertEquals(antigenSequence, feature.getAntigenSequence());
        assertTrue(feature.hasAntigenSequence());
    }

    @Test
    void canCreateBuilderFromInstance() {
        AntigenFeature obj = new AntigenFeatureBuilder().build();
        AntigenFeatureBuilder builder = AntigenFeatureBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        AntigenFeature obj = new AntigenFeatureBuilder().build();
        AntigenFeature obj2 = new AntigenFeatureBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
