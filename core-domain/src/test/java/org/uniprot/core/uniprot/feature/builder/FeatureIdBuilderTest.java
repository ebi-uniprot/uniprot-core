package org.uniprot.core.uniprot.feature.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.feature.FeatureId;

class FeatureIdBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        FeatureId obj = new FeatureIdBuilder("1").build();
        FeatureIdBuilder builder = new FeatureIdBuilder("2").from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        FeatureId obj = new FeatureIdBuilder("1").build();
        FeatureId obj2 = new FeatureIdBuilder("1").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
