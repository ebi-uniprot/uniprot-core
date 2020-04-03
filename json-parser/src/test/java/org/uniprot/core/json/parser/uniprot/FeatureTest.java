package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureDatabase;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class FeatureTest {

    @Test
    void testFeatureSimple() {
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).build();

        ValidateJson.verifyJsonRoundTripParser(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(FeatureType.CHAIN.getDisplayName(), jsonNode.get("type").asText());
    }

    @Test
    void testFeatureExact() {
        FeatureLocation location = new FeatureLocation(2, 8);
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).location(location).build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureOut() {
        FeatureLocation location =
                new FeatureLocation(
                        "seq1", 2, 8, PositionModifier.OUTSIDE, PositionModifier.OUTSIDE);
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).location(location).build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureUnsure() {
        FeatureLocation location =
                new FeatureLocation(2, 8, PositionModifier.UNSURE, PositionModifier.UNSURE);
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).location(location).build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureUnknow() {
        FeatureLocation location =
                new FeatureLocation(
                        "seqId", -1, -1, PositionModifier.UNKNOWN, PositionModifier.UNKNOWN);
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).location(location).build();

        ValidateJson.verifyJsonRoundTripParser(feature);
    }

    @Test
    void testFeatureComplete() {
        Feature feature = getFeature();

        ValidateJson.verifyJsonRoundTripParser(feature);
        ValidateJson.verifyEmptyFields(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(FeatureType.CHAIN.getDisplayName(), jsonNode.get("type").asText());

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
        assertEquals("description value CHAIN", jsonNode.get("description").asText());

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

        assertNotNull(jsonNode.get("featureCrossReference"));
        JsonNode dbXref = jsonNode.get("featureCrossReference");
        assertNotNull(dbXref.get("database"));
        assertEquals("dbSNP", dbXref.get("database").asText());
        assertNotNull(dbXref.get("id"));
        assertEquals("db id", dbXref.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");
    }

    public static Feature getFeature() {
        return getFeature(FeatureType.CHAIN);
    }

    public static Feature getFeature(FeatureType featureType) {
        AlternativeSequence alternativeSequence =
                new AlternativeSequenceBuilder()
                        .original("original value")
                        .alternativeSequencesAdd("alternative value")
                        .build();

        CrossReference<FeatureDatabase> xrefs =
                new CrossReferenceBuilder<FeatureDatabase>()
                        .database(FeatureDatabase.DBSNP)
                        .id("db id")
                        .build();

        FeatureLocation location =
                new FeatureLocation(
                        "sequence 1", 2, 8, PositionModifier.EXACT, PositionModifier.EXACT);
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        return new FeatureBuilder()
                .type(featureType)
                .alternativeSequence(alternativeSequence)
                .featureCrossReference(xrefs)
                .description("description value " + featureType)
                .evidencesSet(evidences)
                .featureId("id value " + featureType)
                .location(location)
                .build();
    }
}
