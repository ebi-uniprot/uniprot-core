package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureLocation;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.FeatureXDbType;
import org.uniprot.core.uniprot.feature.builder.AlternativeSequenceBuilder;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class FeatureTest {

    @Test
    void testFeatureSimple() {
        Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).build();

        ValidateJson.verifyJsonRoundTripParser(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(FeatureType.CHAIN.toDisplayName(), jsonNode.get("type").asText());
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
        assertEquals(FeatureType.CHAIN.toDisplayName(), jsonNode.get("type").asText());

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
        assertEquals("description value", jsonNode.get("description").asText());

        assertNotNull(jsonNode.get("featureId"));
        assertEquals("id value", jsonNode.get("featureId").asText());

        assertNotNull(jsonNode.get("alternativeSequence"));
        JsonNode alternativeSequence = jsonNode.get("alternativeSequence");
        assertNotNull(alternativeSequence.get("originalSequence"));
        assertEquals("original value", alternativeSequence.get("originalSequence").asText());
        assertNotNull(alternativeSequence.get("alternativeSequences"));
        assertEquals(1, alternativeSequence.get("alternativeSequences").size());
        assertEquals(
                "alternative value",
                alternativeSequence.get("alternativeSequences").get(0).asText());

        assertNotNull(jsonNode.get("dbXref"));
        JsonNode dbXref = jsonNode.get("dbXref");
        assertNotNull(dbXref.get("databaseType"));
        assertEquals("dbSNP", dbXref.get("databaseType").asText());
        assertNotNull(dbXref.get("id"));
        assertEquals("db id", dbXref.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");
    }

    public static Feature getFeature() {
        AlternativeSequence alternativeSequence =
                new AlternativeSequenceBuilder()
                        .original("original value")
                        .alternative("alternative value")
                        .build();

        DBCrossReference<FeatureXDbType> xrefs =
                new DBCrossReferenceBuilder<FeatureXDbType>()
                        .databaseType(FeatureXDbType.DBSNP)
                        .id("db id")
                        .build();

        FeatureLocation location =
                new FeatureLocation(
                        "sequence 1", 2, 8, PositionModifier.EXACT, PositionModifier.EXACT);
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        return new FeatureBuilder()
                .type(FeatureType.CHAIN)
                .alternativeSequence(alternativeSequence)
                .dbXref(xrefs)
                .description("description value")
                .evidences(evidences)
                .featureId("id value")
                .location(location)
                .build();
    }
}
