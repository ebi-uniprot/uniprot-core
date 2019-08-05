package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.uniprot.core.uniprot.ProteinExistence;
import org.uniprot.core.uniprot.UniProtEntryType;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
/**
 *
 * @author lgonzales
 */
public class UniprotEntryEnumTest {

    @Test
    public void testProteinExistenceEnum() {
        ProteinExistence proteinExistence = ProteinExistence.PROTEIN_LEVEL;
        ValidateJson.verifyJsonRoundTripParser(proteinExistence);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(proteinExistence);
        assertEquals("1: Evidence at protein level",jsonNode.asText());
    }

    @Test
    public void testEntryTypeEnum() {
        UniProtEntryType entryType = UniProtEntryType.SWISSPROT;
        ValidateJson.verifyJsonRoundTripParser(entryType);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(entryType);
        assertEquals("Swiss-Prot",jsonNode.asText());
    }

}
