package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class SequenceTest {

    @Test
    public void testUniProtDBCrossReferenceComplete() {
        Sequence sequence = getSequence();

        ValidateJson.verifyJsonRoundTripParser(sequence);
        ValidateJson.verifyEmptyFields(sequence);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(sequence);
        assertNotNull(jsonNode.get("value"));
        assertEquals("SAPSQDFMRF",jsonNode.get("value").asText());

        assertNotNull(jsonNode.get("length"));
        assertEquals(10,jsonNode.get("length").asInt());

        assertNotNull(jsonNode.get("molWeight"));
        assertEquals(1185,jsonNode.get("molWeight").asInt());

        assertNotNull(jsonNode.get("crc64"));
        assertEquals("3997D999CAB6C5A7",jsonNode.get("crc64").asText());

        assertNotNull(jsonNode.get("md5"));
        assertEquals("B1D4A86C222D0ED5500ABE909DD36218",jsonNode.get("md5").asText());
    }

    static Sequence getSequence() {
        return UniProtFactory.INSTANCE.createSequence("SAPSQDFMRF");
    }

}
