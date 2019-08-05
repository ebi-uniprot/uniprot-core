package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
/**
 *
 * @author lgonzales
 */
public class UniProtAccessionTest {

    @Test
    public void testUniProtAccession() {
        UniProtAccession uniProtAccession = getUniProtAccession();
        ValidateJson.verifyJsonRoundTripParser(uniProtAccession);
        ValidateJson.verifyEmptyFields(uniProtAccession);


        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(uniProtAccession);
        assertEquals("P21802",jsonNode.asText());
    }

    public static UniProtAccession getUniProtAccession() {
        return new UniProtAccessionBuilder("P21802").build();
    }
}
