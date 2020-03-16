package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class FreeTextCommentTest {

    @Test
    void testFreeTextSimple() {

        FreeTextComment comment =
                new FreeTextCommentBuilder().commentType(CommentType.DISRUPTION_PHENOTYPE).build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE", jsonNode.get("commentType").asText());
    }

    @Test
    void testFreeTextComplete() {
        FreeTextComment comment = getFreeTextComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("DISRUPTION PHENOTYPE", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("Isoform 4", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("texts"));
        assertEquals(1, jsonNode.get("texts").size());
        JsonNode valueEvidence = jsonNode.get("texts").get(0);
        ValidateJson.validateValueEvidence(
                valueEvidence, "value", "ECO:0000256", "PIRNR", "PIRNR001360");
    }

    public static FreeTextComment getFreeTextComment() {
        return new FreeTextCommentBuilder()
                .commentType(CommentType.DISRUPTION_PHENOTYPE)
                .molecule("Isoform 4")
                .textsSet(
                        CreateUtils.createEvidencedValueList(
                                "value", "ECO:0000256|PIRNR:PIRNR001360"))
                .build();
    }
}
