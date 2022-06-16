package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.feature.impl.FeatureDescriptionBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureDatabase;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class UniProtKBFeatureFeatureImplTest {

    @Test
    void testSimple() {
        FeatureLocation location =
                new FeatureLocation(null, 32, 50, PositionModifier.EXACT, PositionModifier.UNSURE);
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.ACT_SITE)
                        .location(location)
                        .description("Some description")
                        .evidencesSet(createEvidences())
                        .build();
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(UniprotKBFeatureType.ACT_SITE, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertFalse(feature.hasFeatureId());
    }

    @Test
    void testWithFeatureId() {
        FeatureLocation location = new FeatureLocation(32, 96);
        FeatureId featureId = new FeatureIdImpl("PRO_324");
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(location)
                        .description("Some chain description")
                        .featureId(featureId)
                        .evidencesSet(createEvidences())
                        .build();

        assertEquals(location, feature.getLocation());
        assertEquals("Some chain description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(UniprotKBFeatureType.CHAIN, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertNull(feature.getAlternativeSequence());
        assertNull(feature.getFeatureCrossReference());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBFeature obj = new UniProtKBFeatureImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<UniprotKBFeatureDatabase> xrefs =
                new CrossReferenceBuilder<UniprotKBFeatureDatabase>()
                        .database(UniprotKBFeatureDatabase.DBSNP)
                        .id("db id")
                        .build();
        UniProtKBFeature impl =
                new UniProtKBFeatureImpl(
                        UniprotKBFeatureType.ZN_FING,
                        new FeatureLocation(1, 2),
                        new FeatureDescriptionBuilder("abc").build(),
                        new FeatureIdBuilder("1").build(),
                        new AlternativeSequenceBuilder().build(),
                        null,
                        xrefs,
                        createEvidences());
        UniProtKBFeature obj = UniProtKBFeatureBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
