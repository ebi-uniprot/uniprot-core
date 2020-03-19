package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.InteractionType;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class InteractionCommentTest {

    @Test
    void testInteractionSimple() {

        InteractionComment comment = new InteractionCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION", jsonNode.get("commentType").asText());
    }

    @Test
    void testInteractionCommentComplete() {
        InteractionComment comment = getInteractionComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("interactions"));
        assertEquals(1, jsonNode.get("interactions").size());
        JsonNode interaction = jsonNode.get("interactions").get(0);

        assertNotNull(interaction.get("type"));
        assertEquals("BINARY", interaction.get("type").asText());
        assertNotNull(interaction.get("uniProtkbAccession"));
        assertEquals("P12345", interaction.get("uniProtkbAccession").asText());
        assertNotNull(interaction.get("geneName"));
        assertEquals("gene name", interaction.get("geneName").asText());
        assertNotNull(interaction.get("numberOfExperiments"));
        assertEquals(10, interaction.get("numberOfExperiments").asInt());
        assertNotNull(interaction.get("firstInteractor"));
        assertEquals("first", interaction.get("firstInteractor").asText());
        assertNotNull(interaction.get("secondInteractor"));
        assertEquals("second", interaction.get("secondInteractor").asText());
    }

    public static InteractionComment getInteractionComment() {
        Interaction interaction =
                new InteractionBuilder()
                        .interactionType(InteractionType.BINARY)
                        .geneName("gene name")
                        .numberOfExperiments(10)
                        .firstInteractant("first")
                        .secondInteractant("second")
                        .uniProtAccession(new UniProtKBAccessionBuilder("P12345").build())
                        .build();

        return new InteractionCommentBuilder().interactionsAdd(interaction).build();
    }
}
