package org.uniprot.core.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.RnaEditingPositionBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class RnaEditingCommentTest {

    @Test
    public void testRnaEditingSimple() {

        RnaEditingComment comment = new RnaEditingCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("RNA EDITING",jsonNode.get("commentType").asText());

    }

    @Test
    public void testRnaEditingComplete() {
        RnaEditingComment comment = getRnaEditingComment();
        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("RNA EDITING",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("locationType"));
        assertEquals("Known",jsonNode.get("locationType").asText());

        assertNotNull(jsonNode.get("positions"));
        assertEquals(1,jsonNode.get("positions").size());
        JsonNode position = jsonNode.get("positions").get(0);

        assertNotNull(position.get("position"));
        assertEquals("rna position",position.get("position").asText());

        assertNotNull(position.get("evidences"));
        assertEquals(1,position.get("evidences").size());
        ValidateJson.validateEvidence(position.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(jsonNode.get("note"));
        assertNotNull(jsonNode.get("note").get("texts"));
        assertEquals(1,jsonNode.get("note").get("texts").size());
        JsonNode valueEvidence = jsonNode.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(valueEvidence,"value","ECO:0000256","PIRNR","PIRNR001361");
    }

    public static RnaEditingComment getRnaEditingComment(){
        Note note = new NoteBuilder(
                CreateUtils.createEvidencedValueList("value","ECO:0000256|PIRNR:PIRNR001361")).build();
        RnaEdPosition rnaEdPositions = new RnaEditingPositionBuilder("rna position",
                CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361")).build();

        return new RnaEditingCommentBuilder()
                .locationType(RnaEditingLocationType.Known)
                .addPosition(rnaEdPositions)
                .note(note)
                .build();
    }




}
