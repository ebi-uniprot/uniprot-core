package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureDatabase;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class FeatureTest {

    @Test
    void testFeatureSimple() {
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder().type(UniprotKBFeatureType.CHAIN).build();

        ValidateJson.verifyJsonRoundTripParser(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(UniprotKBFeatureType.CHAIN.getDisplayName(), jsonNode.get("type").asText());
    }

    @Test
    void testFeatureExact() {
        FeatureLocation location = new FeatureLocation(2, 8);
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(location)
                        .build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureOut() {
        FeatureLocation location =
                new FeatureLocation(
                        "seq1", 2, 8, PositionModifier.OUTSIDE, PositionModifier.OUTSIDE);
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(location)
                        .build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureUnsure() {
        FeatureLocation location =
                new FeatureLocation(2, 8, PositionModifier.UNSURE, PositionModifier.UNSURE);
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(location)
                        .build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureUnknow() {
        FeatureLocation location =
                new FeatureLocation(
                        "seqId", -1, -1, PositionModifier.UNKNOWN, PositionModifier.UNKNOWN);
        UniProtKBFeature feature =
                new UniProtKBFeatureBuilder()
                        .type(UniprotKBFeatureType.CHAIN)
                        .location(location)
                        .build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureComplete() {
        UniProtKBFeature feature = getFeature();

        ValidateJson.verifyJsonRoundTripParser(feature);
        ValidateJson.verifyEmptyFields(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(UniprotKBFeatureType.CHAIN.getDisplayName(), jsonNode.get("type").asText());

        assertNotNull(jsonNode.get("location"));

        assertNotNull(jsonNode.get("location").get("start"));
        JsonNode start = jsonNode.get("location").get("start");
        assertNotNull(start.get("value"));
        assertEquals(2, start.get("value").asInt());
        assertNotNull(start.get("modifier"));
        assertEquals("EXACT", start.get("modifier").asText());

        assertNotNull(jsonNode.get("location").get("end"));
        JsonNode end = jsonNode.get("location").get("end");
        assertNotNull(end.get("value"));
        assertEquals(8, end.get("value").asInt());
        assertNotNull(end.get("modifier"));
        assertEquals("EXACT", end.get("modifier").asText());

        assertNotNull(jsonNode.get("description"));
        assertEquals("description value 123", jsonNode.get("description").asText());

        assertNotNull(jsonNode.get("featureId"));
        assertEquals("id value CHAIN", jsonNode.get("featureId").asText());

        assertNotNull(jsonNode.get("alternativeSequence"));
        JsonNode alternativeSequence = jsonNode.get("alternativeSequence");
        assertNotNull(alternativeSequence.get("originalSequence"));
        assertEquals("original value", alternativeSequence.get("originalSequence").asText());
        assertNotNull(alternativeSequence.get("alternativeSequences"));
        assertEquals(1, alternativeSequence.get("alternativeSequences").size());
        assertEquals(
                "alternative value",
                alternativeSequence.get("alternativeSequences").get(0).asText());

        assertNotNull(jsonNode.get("featureCrossReferences"));
        JsonNode dbXrefs = jsonNode.get("featureCrossReferences");
        assertEquals(1, dbXrefs.size());
        JsonNode dbXref = dbXrefs.get(0);
        assertNotNull(dbXref.get("database"));
        assertEquals("dbSNP", dbXref.get("database").asText());
        assertNotNull(dbXref.get("id"));
        assertEquals("db id", dbXref.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");
    }

    public static UniProtKBFeature getFeature() {
        return getFeature(UniprotKBFeatureType.CHAIN);
    }

    public static UniProtKBFeature getFeature(UniprotKBFeatureType featureType) {
        AlternativeSequence alternativeSequence =
                new AlternativeSequenceBuilder()
                        .original("original value")
                        .alternativeSequencesAdd("alternative value")
                        .build();

        CrossReference<UniprotKBFeatureDatabase> xrefs =
                new CrossReferenceBuilder<UniprotKBFeatureDatabase>()
                        .database(UniprotKBFeatureDatabase.DBSNP)
                        .id("db id")
                        .build();

        FeatureLocation location =
                new FeatureLocation(
                        "sequence 1", 2, 8, PositionModifier.EXACT, PositionModifier.EXACT);
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        Ligand ligand =
                new LigandBuilder()
                        .name("Ca(2+)")
                        .id("ChEBICHEBI:3214")
                        .label("A1")
                        .note("Some note")
                        .build();
        LigandPart ligandPart =
                new LigandPartBuilder()
                        .name("Cu(2+)")
                        .id("ChEBICHEBI:3314")
                        .label("A2")
                        .note("Some note")
                        .build();
        return new UniProtKBFeatureBuilder()
                .type(featureType)
                .alternativeSequence(alternativeSequence)
                .featureCrossReferenceAdd(xrefs)
                .description("description value " + 123)
                .evidencesSet(evidences)
                .featureId("id value " + featureType)
                .location(location)
                .ligand(ligand)
                .ligandPart(ligandPart)
                .build();
    }
}
