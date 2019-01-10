package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class KeywordTest {

    @Test
    public void testKeywordSimple() {
        Keyword keyword = UniProtFactory.INSTANCE.createKeyword(null,null,null);
        ValidateJson.verifyJsonRoundTripParser(keyword);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(keyword);

        assertNotNull(jsonNode.get("id"));
        assertEquals("KW-00000",jsonNode.get("id").asText());
    }
    @Test
    public void testKeywordComplete() {
        Keyword keyword = getKeyword();
        ValidateJson.verifyJsonRoundTripParser(keyword);
        ValidateJson.verifyEmptyFields(keyword);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(keyword);

        assertNotNull(jsonNode.get("value"));
        assertEquals("keyword value",jsonNode.get("value").asText());

        assertNotNull(jsonNode.get("id"));
        assertEquals("KW-11111",jsonNode.get("id").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000255","PROSITE-ProRule","PRU10025");
    }

    static Keyword getKeyword() {
        return UniProtFactory.INSTANCE.createKeyword("KW-11111","keyword value",
                CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10025"));
    }


}
