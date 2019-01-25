package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.AlternativeSequenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.FeatureBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author lgonzales
 */
public class FeatureTest {

    @Test
    public void testFeatureSimple() {
        Feature feature = new FeatureBuilder()
                .type(FeatureType.CHAIN)
                .build();

        ValidateJson.verifyJsonRoundTripParser(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(FeatureType.CHAIN.getDisplayName(),jsonNode.get("type").asText());
    }

    @Test
    public void testFeatureComplete() {
        Feature feature = getFeature();

        ValidateJson.verifyJsonRoundTripParser(feature);
        ValidateJson.verifyEmptyFields(feature);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(feature);
        assertNotNull(jsonNode.get("type"));
        assertEquals(FeatureType.CHAIN.getDisplayName(),jsonNode.get("type").asText());

        assertNotNull(jsonNode.get("location"));

        assertNotNull(jsonNode.get("location").get("start"));
        JsonNode start = jsonNode.get("location").get("start");
        assertNotNull(start.get("value"));
        assertEquals(2,start.get("value").asInt());
        assertNotNull(start.get("modifier"));
        assertEquals("EXACT",start.get("modifier").asText());

        assertNotNull(jsonNode.get("location").get("end"));
        JsonNode end = jsonNode.get("location").get("end");
        assertNotNull(end.get("value"));
        assertEquals(8,end.get("value").asInt());
        assertNotNull(end.get("modifier"));
        assertEquals("EXACT",end.get("modifier").asText());

        assertNotNull(jsonNode.get("description"));
        assertEquals("description value",jsonNode.get("description").asText());

        assertNotNull(jsonNode.get("featureId"));
        assertEquals("id value",jsonNode.get("featureId").asText());

        assertNotNull(jsonNode.get("alternativeSequence"));
        JsonNode alternativeSequence = jsonNode.get("alternativeSequence");
        assertNotNull(alternativeSequence.get("originalSequence"));
        assertEquals("original value",alternativeSequence.get("originalSequence").asText());
        assertNotNull(alternativeSequence.get("alternativeSequences"));
        assertEquals(1,alternativeSequence.get("alternativeSequences").size());
        assertEquals("alternative value",alternativeSequence.get("alternativeSequences").get(0).asText());

        assertNotNull(jsonNode.get("dbXref"));
        JsonNode dbXref = jsonNode.get("dbXref");
        assertNotNull(dbXref.get("databaseType"));
        assertEquals("dbSNP",dbXref.get("databaseType").asText());
        assertNotNull(dbXref.get("id"));
        assertEquals("db id",dbXref.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000269","PubMed","11389730");
    }

    static Feature getFeature(){
        AlternativeSequence alternativeSequence = new AlternativeSequenceBuilder()
                .original("original value")
                .alternative("alternative value")
                .build();

        DBCrossReference<FeatureXDbType> xrefs = new DBCrossReferenceBuilder<FeatureXDbType>()
                .databaseType(FeatureXDbType.DBSNP)
                .id("db id")
                .build();

        Range location = Range.create(2,8);
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
