package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureDatabase;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;

class FeatureImplTest {

    @Test
    void testSimple() {
        FeatureLocation location =
                new FeatureLocation(null, 32, 50, PositionModifier.EXACT, PositionModifier.UNSURE);
        Feature feature =
                new FeatureBuilder()
                        .type(FeatureType.ACT_SITE)
                        .location(location)
                        .description("Some description")
                        .evidencesSet(createEvidences())
                        .build();
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.ACT_SITE, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertFalse(feature.hasFeatureId());
    }

    @Test
    void testWithFeatureId() {
        FeatureLocation location = new FeatureLocation(32, 96);
        FeatureId featureId = new FeatureIdImpl("PRO_324");
        Feature feature =
                new FeatureBuilder()
                        .type(FeatureType.CHAIN)
                        .location(location)
                        .description("Some chain description")
                        .featureId(featureId)
                        .evidencesSet(createEvidences())
                        .build();

        assertEquals(location, feature.getLocation());
        assertEquals("Some chain description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.CHAIN, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertNull(feature.getAlternativeSequence());
        assertNull(feature.getFeatureCrossReference());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Feature obj = new FeatureImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<FeatureDatabase> xrefs =
                new CrossReferenceBuilder<FeatureDatabase>()
                        .database(FeatureDatabase.DBSNP)
                        .id("db id")
                        .build();
        Feature impl =
                new FeatureImpl(
                        FeatureType.ZN_FING,
                        new FeatureLocation(1, 2),
                        new FeatureDescriptionImpl("abc"),
                        new FeatureIdBuilder("1").build(),
                        new AlternativeSequenceBuilder().build(),
                        xrefs,
                        createEvidences());
        Feature obj = FeatureBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
