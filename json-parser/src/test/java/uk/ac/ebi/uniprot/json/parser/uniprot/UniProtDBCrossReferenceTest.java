package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.impl.UniProtDBCrossReferenceImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 *
 * @author lgonzales
 */
public class UniProtDBCrossReferenceTest {

    @Test
    public void testUniProtDBCrossReferenceSimple() {
        UniProtXDbType opType = new UniProtXDbType("PIR");
        UniProtDBCrossReference dbCrossReference = new UniProtDBCrossReferenceImpl(opType,
                "S61096", null, null);

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("databaseType"));
        assertEquals("PIR",jsonNode.get("databaseType").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("S61096",jsonNode.get("id").asText());
    }

    @Test
    public void testUniProtDBCrossReferenceComplete() {

        UniProtDBCrossReference dbCrossReference = getUniProtDBCrossReference();

        ValidateJson.verifyJsonRoundTripParser(dbCrossReference);
        ValidateJson.verifyEmptyFields(dbCrossReference);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(dbCrossReference);
        assertNotNull(jsonNode.get("databaseType"));
        assertEquals("Ensembl",jsonNode.get("databaseType").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("id value",jsonNode.get("id").asText());

        assertNotNull(jsonNode.get("isoformId"));
        assertEquals("Q9NXB0-1",jsonNode.get("isoformId").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000269","PubMed","11389730");

        assertNotNull(jsonNode.get("properties"));
        assertEquals(1,jsonNode.get("properties").size());

        JsonNode property = jsonNode.get("properties").get(0);
        assertNotNull(property.get("key"));
        assertEquals("ProteinId",property.get("key").asText());

        assertNotNull(property.get("value"));
        assertEquals("description value",property.get("value").asText());

    }

    static UniProtDBCrossReference getUniProtDBCrossReference() {
        Property property = new Property("ProteinId","description value");
        return new UniProtDBCrossReferenceBuilder()
                .id("id value")
                .isoformId("Q9NXB0-1")
                .addProperty(property)
                .databaseType(new UniProtXDbType("Ensembl"))
                .addEvidence( CreateUtils.createEvidence("ECO:0000269|PubMed:11389730"))
                .build();
    }
}
