package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.FeatureId;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class UniProtKBFeatureBuilderTest {

    @Test
    void canSetAlternativeSequence() {
        AlternativeSequence org = new AlternativeSequenceBuilder().original("org").build();
        UniProtKBFeature obj =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.VARIANT)
                        .alternativeSequence(org)
                        .build();
        assertEquals(org, obj.getAlternativeSequence());
        assertTrue(obj.hasAlternativeSequence());
    }

    @Test
    void canSetFeatureIdFromString() {
        UniProtKBFeature obj = new UniProtKBFeatureBuilder().featureId("id").build();
        assertEquals("id", obj.getFeatureId().getValue());
    }

    @Test
    void canSetFeatureId() {
        FeatureId id = new FeatureIdBuilder("id").build();
        UniProtKBFeature obj = new UniProtKBFeatureBuilder().featureId(id).build();
        assertEquals(id, obj.getFeatureId());
    }

    @Test
    void canSetLigand() {
        Ligand ligand = new LigandBuilder().name("Some name").id("ChEBI:CHEBI:5432").build();

        UniProtKBFeature obj = new UniProtKBFeatureBuilder().ligand(ligand).build();
        assertEquals(ligand, obj.getLigand());
    }

    @Test
    void canSetLigandPart() {
        LigandPart ligandPart =
                new LigandPartBuilder().name("Some name").id("ChEBI:CHEBI:5432").build();

        UniProtKBFeature obj = new UniProtKBFeatureBuilder().ligandPart(ligandPart).build();
        assertEquals(ligandPart, obj.getLigandPart());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBFeature obj = new UniProtKBFeatureBuilder().build();
        UniProtKBFeatureBuilder builder = UniProtKBFeatureBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBFeature obj = new UniProtKBFeatureBuilder().build();
        UniProtKBFeature obj2 = new UniProtKBFeatureBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
