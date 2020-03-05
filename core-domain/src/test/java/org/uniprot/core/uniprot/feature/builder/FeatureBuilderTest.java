package org.uniprot.core.uniprot.feature.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureDatabase;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureLocation;
import org.uniprot.core.uniprot.feature.FeatureType;

class FeatureBuilderTest {

    @Test
    void canSetType() {
        Feature obj = new FeatureBuilder().type(FeatureType.CHAIN).build();
        assertEquals(FeatureType.CHAIN, obj.getType());
    }

    @Test
    void canSetLocation() {
        FeatureLocation range = new FeatureLocation(1, 2);
        Feature obj = new FeatureBuilder().location(range).build();
        assertEquals(range, obj.getLocation());
        assertTrue(obj.hasLocation());
    }

    @Test
    void canSetDescriptionFromString() {
        Feature obj = new FeatureBuilder().description("des").build();
        assertEquals("des", obj.getDescription().getValue());
        assertTrue(obj.hasDescription());
    }

    @Test
    void canSetFeatureId() {
        FeatureId id = new FeatureIdBuilder("id").build();
        Feature obj = new FeatureBuilder().featureId(id).build();
        assertEquals(id, obj.getFeatureId());
    }

    @Test
    void canSetFeatureIdFromString() {
        Feature obj = new FeatureBuilder().featureId("id").build();
        assertEquals("id", obj.getFeatureId().getValue());
    }

    @Test
    void canSetAlternativeSequence() {
        AlternativeSequence org = new AlternativeSequenceBuilder().original("org").build();
        Feature obj = new FeatureBuilder().alternativeSequence(org).build();
        assertEquals(org, obj.getAlternativeSequence());
    }

    @Test
    void canSetDbXref() {
        CrossReference<FeatureDatabase> xrefs =
                new CrossReferenceBuilder<FeatureDatabase>()
                        .database(FeatureDatabase.DBSNP)
                        .id("db id")
                        .build();
        Feature obj = new FeatureBuilder().featureCrossReference(xrefs).build();
        assertEquals(xrefs, obj.getFeatureCrossReference());
        assertTrue(obj.hasFeatureCrossReference());
    }

    @Test
    void canAddSingleEvidence() {
        Feature obj = new FeatureBuilder().evidencesAdd(createEvidence()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        Feature obj = new FeatureBuilder().evidencesAdd(null).build();
        assertNotNull(obj.getEvidences());
        assertTrue(obj.getEvidences().isEmpty());
        assertFalse(obj.hasEvidences());
    }

    @Test
    void evidences_willConvertUnModifiable_toModifiable() {
        Feature obj =
                new FeatureBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(createEvidence())
                        .build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canAddListEvidences() {
        Feature obj = new FeatureBuilder().evidencesSet(createEvidences()).build();
        assertNotNull(obj.getEvidences());
        assertFalse(obj.getEvidences().isEmpty());
        assertTrue(obj.hasEvidences());
    }

    @Test
    void canCreateBuilderFromInstance() {
        Feature obj = new FeatureBuilder().build();
        FeatureBuilder builder = FeatureBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Feature obj = new FeatureBuilder().build();
        Feature obj2 = new FeatureBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
