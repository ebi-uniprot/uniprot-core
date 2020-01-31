package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class UniProtDBCrossReferenceTest {

    @Test
    void testUniProtDBCrossReferenceSimple() {
        UniProtXDbType opType = new UniProtXDbType("PIR");
        UniProtDBCrossReference dbCrossReference =
                new UniProtDBCrossReferenceBuilder().databaseType(opType).id("S61096").build();

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("databaseType"));
        assertEquals("PIR", jsonNode.get("databaseType").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("S61096", jsonNode.get("id").asText());
    }

    @Test
    void testUniProtDBCrossReferenceComplete() {

        UniProtDBCrossReference dbCrossReference = getUniProtDBCrossReference();

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);
        ValidateJson.verifyEmptyFields(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("databaseType"));
        assertEquals("Ensembl", jsonNode.get("databaseType").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("id value", jsonNode.get("id").asText());

        assertNotNull(jsonNode.get("isoformId"));
        assertEquals("Q9NXB0-1", jsonNode.get("isoformId").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");

        assertNotNull(jsonNode.get("properties"));
        assertEquals(1, jsonNode.get("properties").size());

        JsonNode property = jsonNode.get("properties").get(0);
        assertNotNull(property.get("key"));
        assertEquals("ProteinId", property.get("key").asText());

        assertNotNull(property.get("value"));
        assertEquals("description value", property.get("value").asText());
    }

    public static UniProtDBCrossReference getUniProtDBCrossReference() {
        Property property = new Property("ProteinId", "description value");
        return new UniProtDBCrossReferenceBuilder()
                .id("id value")
                .isoformId("Q9NXB0-1")
                .propertiesAdd(property)
                .databaseType(new UniProtXDbType("Ensembl"))
                .addEvidence(CreateUtils.createEvidence("ECO:0000269|PubMed:11389730"))
                .build();
    }
}
