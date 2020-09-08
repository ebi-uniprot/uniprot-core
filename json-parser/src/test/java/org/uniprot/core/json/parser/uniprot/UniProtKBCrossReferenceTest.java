package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.cv.xdb.UniProtKBDatabaseImpl;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
class UniProtKBCrossReferenceTest {

    @Test
    void testUniProtDBCrossReferenceSimple() {
        UniProtKBDatabase opType = new UniProtKBDatabaseImpl("PIR");
        UniProtKBCrossReference dbCrossReference =
                new UniProtCrossReferenceBuilder().database(opType).id("S61096").build();

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("database"));
        assertEquals("PIR", jsonNode.get("database").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("S61096", jsonNode.get("id").asText());
    }

    @Test
    void testUniProtDBCrossReferenceComplete() {

        UniProtKBCrossReference dbCrossReference = getUniProtDBCrossReference();

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);
        ValidateJson.verifyEmptyFields(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("database"));
        assertEquals("Ensembl", jsonNode.get("database").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("idEnsembl", jsonNode.get("id").asText());

        assertNotNull(jsonNode.get("isoformId"));
        assertEquals("Q9NXB0-1", jsonNode.get("isoformId").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000269", "PubMed", "11389730");

        assertNotNull(jsonNode.get("properties"));
        assertEquals(2, jsonNode.get("properties").size());

        JsonNode property = jsonNode.get("properties").get(0);
        assertNotNull(property.get("key"));
        assertEquals("ProteinId", property.get("key").asText());

        assertNotNull(property.get("value"));
        assertEquals("description value", property.get("value").asText());
    }

    public static UniProtKBCrossReference getUniProtDBCrossReference() {
        return getUniProtDBCrossReference("Ensembl");
    }

    public static UniProtKBCrossReference getUniProtDBGOCrossReferences(
            String termType, String evidenceType) {
        Property property = new Property("GoTerm", termType + ":nucleus");
        Property property2 = new Property("GoEvidenceType", evidenceType + ":UniProtKB");
        return new UniProtCrossReferenceBuilder()
                .id("GO:0000123")
                .isoformId("Q9NXB0-1")
                .propertiesAdd(property)
                .propertiesAdd(property2)
                .database(new UniProtKBDatabaseImpl("GO"))
                .evidencesAdd(CreateUtils.createEvidence("ECO:0000269|PubMed:11389730"))
                .build();
    }

    public static UniProtKBCrossReference getUniProtDBCrossReference(String databaseName) {
        Property property = new Property("ProteinId", "description value");
        Property property2 = new Property("GeneId", "Model");

        return new UniProtCrossReferenceBuilder()
                .id("id" + databaseName)
                .isoformId("Q9NXB0-1")
                .propertiesAdd(property)
                .propertiesAdd(property2)
                .database(new UniProtKBDatabaseImpl(databaseName))
                .evidencesAdd(CreateUtils.createEvidence("ECO:0000269|PubMed:11389730"))
                .build();
    }
}
