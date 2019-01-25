package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class InteractionCommentTest {

    @Test
    public void testInteractionSimple() {

        InteractionComment comment = new InteractionCommentBuilder()
                .build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION",jsonNode.get("commentType").asText());

    }

    @Test
    public void testInteractionCommentComplete() {
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
