package org.uniprot.core.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.feature.FeatureDescription;

/**
 * @author lgonzales
 * @since 06/05/2020
 */
class FeatureDescriptionBuilderTest {

    @Test
    void nullWillIgnoreInValue() {
        FeatureDescription obj = new FeatureDescriptionBuilder(null).build();
        assertTrue(obj.getValue().isEmpty());
    }

    @Test
    void canCreateBuilderFromInstance() {
        FeatureDescription obj = new FeatureDescriptionBuilder("value value").build();
        FeatureDescriptionBuilder builder = FeatureDescriptionBuilder.from(obj);
        assertNotNull(builder);
        FeatureDescription objFrom = builder.build();
        assertEquals(obj, objFrom);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        FeatureDescription obj = new FeatureDescriptionBuilder("value value").build();
        FeatureDescription obj2 = FeatureDescriptionBuilder.from(obj).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
