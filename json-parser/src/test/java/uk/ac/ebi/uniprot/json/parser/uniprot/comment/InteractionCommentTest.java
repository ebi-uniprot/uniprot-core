package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtAccessionImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class InteractionCommentTest {

    @Test
    public void testInteractionSimple() {

        InteractionComment comment = InteractionCommentBuilder.newInstance()
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
        List<Interaction> interactions = Collections.singletonList(
                InteractionBuilder.newInstance()
                .interactionType(InteractionType.BINARY)
                .geneName("gene name")
                .numberOfExperiments(10)
                .firstInteractor(InteractionBuilder.createInteractor("first"))
                .secondInteractor(InteractionBuilder.createInteractor("second"))
                .uniProtAccession(new UniProtAccessionImpl("P12345"))
                .build());

        return InteractionCommentBuilder.newInstance()
                .interactions(interactions)
                .build();
    }

    private static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001360"));
        return evidences;
    }

    private static List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value", createEvidences()));
        return evidencedValues;
    }
}
