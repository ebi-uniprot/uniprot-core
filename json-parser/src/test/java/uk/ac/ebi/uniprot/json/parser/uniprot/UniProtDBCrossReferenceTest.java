package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
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

        assertNotNull(jsonNode.get("properties"));
        assertEquals(2,jsonNode.get("properties").size());

        JsonNode property = jsonNode.get("properties").get(0);
        assertNotNull(property.get("key"));
        assertEquals("ProteinId",property.get("key").asText());

        assertNotNull(property.get("value"));
        assertEquals("description value",property.get("value").asText());

        property = jsonNode.get("properties").get(1);
        assertNotNull(property.get("key"));
        assertEquals("GeneId",property.get("key").asText());

        assertNotNull(property.get("value"));
        assertEquals("third value",property.get("value").asText());
    }

    static UniProtDBCrossReference getUniProtDBCrossReference() {
        return UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference("Ensembl",
                "id value",
                "description value",
                "third value",
                "fourth value",
                "Q9NXB0-1");
    }
}
