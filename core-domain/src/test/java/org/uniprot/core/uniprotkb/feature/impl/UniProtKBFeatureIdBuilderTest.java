package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeatureId;

class UniProtKBFeatureIdBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBFeatureId obj = new UniProtKBFeatureIdBuilder("1").build();
        UniProtKBFeatureIdBuilder builder = UniProtKBFeatureIdBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBFeatureId obj = new UniProtKBFeatureIdBuilder("1").build();
        UniProtKBFeatureId obj2 = new UniProtKBFeatureIdBuilder("1").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
