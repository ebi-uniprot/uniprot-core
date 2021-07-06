package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.comment.impl.InteractantBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionCommentBuilder;

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
    void testInteractionComment() {
        InteractionComment comment = getInteractionComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("INTERACTION", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("interactions"));
        assertEquals(2, jsonNode.get("interactions").size());
        JsonNode interaction = jsonNode.get("interactions").get(0);

        JsonNode interactantOne = interaction.get("interactantOne");
        JsonNode interactantTwo = interaction.get("interactantTwo");
        assertNotNull(interactantOne);
        assertNotNull(interactantTwo);

        assertNotNull(interactantOne.get("chainId"));
        assertEquals("PROC_12344", interactantOne.get("chainId").asText());
        assertEquals("EBI-0001", interactantOne.get("intActId").asText());

        assertEquals("P12345-1", interactantTwo.get("uniProtKBAccession").asText());
        assertEquals("gene1", interactantTwo.get("geneName").asText());
        assertEquals("EBI-0002", interactantTwo.get("intActId").asText());
        assertNotNull(interaction.get("numberOfExperiments"));
        assertEquals(3, interaction.get("numberOfExperiments").asInt());
        assertTrue(interaction.get("organismDiffer").asBoolean());

        JsonNode interaction2 = jsonNode.get("interactions").get(1);
        interactantOne = interaction2.get("interactantOne");
        interactantTwo = interaction2.get("interactantTwo");

        assertNotNull(interactantOne);
        assertNotNull(interactantTwo);

        assertEquals("P12345", interactantOne.get("uniProtKBAccession").asText());
        assertEquals("EBI-00011", interactantOne.get("intActId").asText());

        assertEquals("P12346", interactantTwo.get("uniProtKBAccession").asText());
        assertEquals("gene1", interactantTwo.get("geneName").asText());
        assertEquals("EBI-00012", interactantTwo.get("intActId").asText());
        assertNotNull(interactantTwo.get("chainId"));
        assertEquals("PROC_123454", interactantTwo.get("chainId").asText());

        assertNotNull(interaction2.get("numberOfExperiments"));
        assertEquals(6, interaction2.get("numberOfExperiments").asInt());
        assertTrue(interaction2.get("organismDiffer").asBoolean());
    }

    public static InteractionComment getInteractionComment() {
        InteractionBuilder builder = new InteractionBuilder();
        Interactant interactant1 = createInteractant("PROC_12344", "EBI-0001", "P12345", "gen");
        Interactant interactant2 = createInteractant("PROC_12347", "EBI-0002", "P12345-1", "gene1");

        Interaction interaction1 =
                builder.interactantOne(interactant1)
                        .interactantTwo(interactant2)
                        .numberOfExperiments(3)
                        .isOrganismDiffer(true)
                        .build();

        InteractionBuilder builder2 = new InteractionBuilder();
        Interactant interactant21 = createInteractant("PROC_12345", "EBI-00011", "P12345", "gen");
        Interactant interactant22 =
                createInteractant("PROC_123454", "EBI-00012", "P12346", "gene1");

        Interaction interaction2 =
                builder2.interactantOne(interactant21)
                        .interactantTwo(interactant22)
                        .numberOfExperiments(6)
                        .isOrganismDiffer(true)
                        .build();
        List<Interaction> interactions = new ArrayList<>();
        interactions.add(interaction1);
        interactions.add(interaction2);
        InteractionCommentBuilder commentBuilder = new InteractionCommentBuilder();
        return commentBuilder.interactionsSet(interactions).build();
    }

    private static Interactant createInteractant(
            String chainId, String intActId, String accession, String geneName) {
        InteractantBuilder builder = new InteractantBuilder();
        builder.chainId(chainId).geneName(geneName).intActId(intActId);
        if (accession != null) builder.uniProtKBAccession(accession);
        return builder.build();
    }
}
