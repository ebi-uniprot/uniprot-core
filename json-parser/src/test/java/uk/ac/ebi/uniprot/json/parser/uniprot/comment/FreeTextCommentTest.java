package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.CreateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class FreeTextCommentTest {

    @Test
    public void testFreeTextSimple() {

        FreeTextComment comment = new FreeTextCommentBuilder()
                .commentType(CommentType.DISRUPTION_PHENOTYPE)
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE",jsonNode.get("commentType").asText());

    }

    @Test
    public void testFreeTextComplete() {
        FreeTextComment comment = getFreeTextComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("texts"));
        assertEquals(1,jsonNode.get("texts").size());
        JsonNode valueEvidence = jsonNode.get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value","ECO:0000256","PIRNR","PIRNR001360");

    }

    public static FreeTextComment getFreeTextComment(){
        return new FreeTextCommentBuilder()
                .commentType(CommentType.DISRUPTION_PHENOTYPE)
                .texts(CreateUtils.createEvidencedValueList("value","ECO:0000256|PIRNR:PIRNR001360"))
                .build();
    }


}
