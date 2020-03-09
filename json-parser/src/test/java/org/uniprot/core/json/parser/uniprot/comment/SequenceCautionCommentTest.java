package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.impl.SequenceCautionCommentBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class SequenceCautionCommentTest {

    @Test
    void testSequenceCautionSimple() {

        SequenceCautionComment comment = new SequenceCautionCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SEQUENCE CAUTION", jsonNode.get("commentType").asText());
    }

    @Test
    void testSequenceCautionComplete() {

        SequenceCautionComment comment = getSequenceCautionComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);

        assertNotNull(jsonNode.get("commentType"));
        assertEquals("SEQUENCE CAUTION", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("Isoform 2", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("sequenceCautionType"));
        assertEquals("Erroneous initiation", jsonNode.get("sequenceCautionType").asText());

        assertNotNull(jsonNode.get("sequence"));
        assertEquals("sequence", jsonNode.get("sequence").asText());

        assertNotNull(jsonNode.get("note"));
        assertEquals("Text note", jsonNode.get("note").asText());

        assertNotNull(jsonNode.get("evidences"));
        assertEquals(1, jsonNode.get("evidences").size());
        ValidateJson.validateEvidence(
                jsonNode.get("evidences").get(0), "ECO:0000256", "PIRNR", "PIRNR001361");
    }

    public static SequenceCautionComment getSequenceCautionComment() {
        return new SequenceCautionCommentBuilder()
                .molecule("Isoform 2")
                .sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .sequence("sequence")
                .note("Text note")
                .evidencesSet(CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361"))
                .build();
    }
}
