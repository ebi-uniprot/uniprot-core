package uk.ac.ebi.uniprot.json.parser.uniprot.comment;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.PhysiologicalReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ReactionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;
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
public class CatalyticActivityCommentTest {


    @Test
    public void testCatalyticActivitySimple() {

        CatalyticActivityComment comment = CatalyticActivityCommentBuilder.newInstance().build();
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
        return CatalyticActivityCommentBuilder.newInstance()
                    .physiologicalReactions(createPhyReactions())
                    .reaction(createReaction())
                    .build();
    }


    private static List<PhysiologicalReaction> createPhyReactions() {
        List<Evidence> evidences = Collections.singletonList(new EvidenceImpl(EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"));

        List<PhysiologicalReaction> phyReactions = new ArrayList<>();
        phyReactions.add(new PhysiologicalReactionImpl(PhysiologicalDirectionType.RIGHT_TO_LEFT,
                new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:313"),
                evidences
        ));
        return phyReactions;
    }

    private static Reaction createReaction() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
        ));
        String name = "some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        return new ReactionImpl(name, references, ecNumber, evidences);
    }
}
