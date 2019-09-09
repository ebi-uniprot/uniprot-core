package org.uniprot.core.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.comment.Interaction;
import org.uniprot.core.uniprot.comment.InteractionComment;
import org.uniprot.core.uniprot.comment.InteractionType;
import org.uniprot.core.uniprot.comment.builder.InteractionBuilder;
import org.uniprot.core.uniprot.comment.builder.InteractionCommentBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class InteractionCommentTest {

    @Test
    void testInteractionSimple() {

        InteractionComment comment = new InteractionCommentBuilder()
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION",jsonNode.get("commentType").asText());

    }

    @Test
    void testInteractionCommentComplete() {
        InteractionComment comment = getInteractionComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("interactions"));
        assertEquals(1,jsonNode.get("interactions").size());
        JsonNode interaction = jsonNode.get("interactions").get(0);

        assertNotNull(interaction.get("type"));
        assertEquals("BINARY",interaction.get("type").asText());
        assertNotNull(interaction.get("uniProtAccession"));
        assertEquals("P12345",interaction.get("uniProtAccession").asText());
        assertNotNull(interaction.get("geneName"));
        assertEquals("gene name",interaction.get("geneName").asText());
        assertNotNull(interaction.get("numberOfExperiments"));
        assertEquals(10,interaction.get("numberOfExperiments").asInt());
        assertNotNull(interaction.get("firstInteractor"));
        assertEquals("first",interaction.get("firstInteractor").asText());
        assertNotNull(interaction.get("secondInteractor"));
        assertEquals("second",interaction.get("secondInteractor").asText());
    }

    public static InteractionComment getInteractionComment(){
        Interaction interaction = new InteractionBuilder()
                .interactionType(InteractionType.BINARY)
                .geneName("gene name")
                .numberOfExperiments(10)
                .firstInteractor("first")
                .secondInteractor("second")
                .uniProtAccession(new UniProtAccessionImpl("P12345"))
                .build();

        return new InteractionCommentBuilder()
                .addInteraction(interaction)
                .build();
    }
}
