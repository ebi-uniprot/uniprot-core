package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.CreateUtils;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class SequenceCautionCommentTest {

    @Test
    public void testSequenceCautionSimple() {

        SequenceCautionComment comment = new SequenceCautionCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SEQUENCE CAUTION",jsonNode.get("commentType").asText());

    }

    @Test
    public void testSequenceCautionComplete() {

        SequenceCautionComment comment = getSequenceCautionComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);

        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SEQUENCE CAUTION",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("sequenceCautionType"));
        assertEquals("Erroneous initiation",jsonNode.get("sequenceCautionType").asText());

        assertNotNull(jsonNode.get("sequence"));
        assertEquals("sequence",jsonNode.get("sequence").asText());

        assertNotNull(jsonNode.get("positions"));
        assertEquals(1,jsonNode.get("positions").size());
        assertEquals("position",jsonNode.get("positions").get(0).asText());

        assertNotNull(jsonNode.get("note"));
        assertEquals("Text note",jsonNode.get("note").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1,jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(jsonNode.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001361");
    }


    public static SequenceCautionComment getSequenceCautionComment(){
        return new SequenceCautionCommentBuilder()
                .sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .sequence("sequence")
                .positions(Collections.singletonList("position"))
                .note("Text note")
                .evidences(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361"))
                .build();
    }

}
