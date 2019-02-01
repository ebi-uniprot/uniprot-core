package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.ReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.CreateUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class CatalyticActivityCommentTest {


    @Test
    public void testCatalyticActivitySimple() {

        CatalyticActivityComment comment = new CatalyticActivityCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("CATALYTIC ACTIVITY",jsonNode.get("commentType").asText());
    }


    @Test
    public void testCatalyticActivityComplete() {
        CatalyticActivityComment comment = getCatalyticActivityComment();


        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("CATALYTIC ACTIVITY",jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("reaction"));
        JsonNode reaction = jsonNode.get("reaction");
        assertNotNull(reaction.get("name"));
        assertEquals("some reaction",reaction.get("name").asText());
        assertNotNull(reaction.get("reactionReferences"));
        assertEquals(1,reaction.get("reactionReferences").size());
        JsonNode reactionReferences = reaction.get("reactionReferences").get(0);
        assertNotNull(reactionReferences.get("databaseType"));
        assertEquals("ChEBI",reactionReferences.get("databaseType").asText());
        assertNotNull(reactionReferences.get("id"));
        assertEquals("ChEBI:3243",reactionReferences.get("id").asText());
        assertNotNull(reaction.get("ecNumber"));
        assertEquals("1.2.4.5",reaction.get("ecNumber").asText());
        assertNotNull(reaction.get("evidences"));
        assertEquals(1,reaction.get("evidences").size());
        ValidateJson.validateEvidence(reaction.get("evidences").get(0),"ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(jsonNode.get("physiologicalReactions"));
        assertEquals(1,jsonNode.get("physiologicalReactions").size());
        JsonNode physiologicalReactions = jsonNode.get("physiologicalReactions").get(0);
        assertNotNull(physiologicalReactions.get("directionType"));
        assertEquals("right-to-left",physiologicalReactions.get("directionType").asText());
        assertNotNull(physiologicalReactions.get("reactionReference"));
        reactionReferences = physiologicalReactions.get("reactionReference");
        assertNotNull(reactionReferences.get("databaseType"));
        assertEquals("Rhea",reactionReferences.get("databaseType").asText());
        assertNotNull(reactionReferences.get("id"));
        assertEquals("RHEA:313",reactionReferences.get("id").asText());
        assertNotNull(physiologicalReactions.get("evidences"));
        assertEquals(1,physiologicalReactions.get("evidences").size());
        ValidateJson.validateEvidence(physiologicalReactions.get("evidences").get(0),"ECO:0000313","Ensembl","ENSP0001324");
    }

    public static CatalyticActivityComment getCatalyticActivityComment() {
        return new CatalyticActivityCommentBuilder()
                    .physiologicalReactions(createPhyReactions())
                    .reaction(createReaction())
                    .build();
    }


    private static List<PhysiologicalReaction> createPhyReactions() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000313|Ensembl:ENSP0001324");

        List<PhysiologicalReaction> phyReactions = new ArrayList<>();
        phyReactions.add(new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.RIGHT_TO_LEFT)
                .reactionReference(new DBCrossReferenceBuilder<ReactionReferenceType>()
                        .databaseType(ReactionReferenceType.RHEA)
                        .id("RHEA:313")
                        .build())
                .evidences(evidences)
                .build());
        return phyReactions;
    }

    private static Reaction createReaction() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        String name = "some reaction";
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        return new ReactionBuilder()
                .name(name)
                .addReactionReference(new DBCrossReferenceBuilder<ReactionReferenceType>()
                        .databaseType(ReactionReferenceType.CHEBI)
                        .id("ChEBI:3243")
                        .build())
                .ecNumber(ecNumber)
                .evidences(evidences)
                .build();
    }
}
