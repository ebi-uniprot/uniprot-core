package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ECNumber;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class CatalyticActivityCommentTest {

    @Test
    void testCatalyticActivitySimple() {

        CatalyticActivityComment comment = new CatalyticActivityCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("CATALYTIC ACTIVITY", jsonNode.get("commentType").asText());
    }

    @Test
    void testCatalyticActivityComplete() {
        CatalyticActivityComment comment = getCatalyticActivityComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("CATALYTIC ACTIVITY", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("Isoform 3", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("reaction"));
        JsonNode reaction = jsonNode.get("reaction");
        assertNotNull(reaction.get("name"));
        assertEquals("some reaction", reaction.get("name").asText());
        assertNotNull(reaction.get("reactionCrossReferences"));
        assertEquals(1, reaction.get("reactionCrossReferences").size());
        JsonNode reactionCrossReferences = reaction.get("reactionCrossReferences").get(0);
        assertNotNull(reactionCrossReferences.get("database"));
        assertEquals("ChEBI", reactionCrossReferences.get("database").asText());
        assertNotNull(reactionCrossReferences.get("id"));
        assertEquals("ChEBI:3243", reactionCrossReferences.get("id").asText());
        assertNotNull(reaction.get("ecNumber"));
        assertEquals("1.2.4.5", reaction.get("ecNumber").asText());
        assertNotNull(reaction.get("evidences"));
        assertEquals(1, reaction.get("evidences").size());
        ValidateJson.validateEvidence(
                reaction.get("evidences").get(0), "ECO:0000256", "PIRNR", "PIRNR001361");

        assertNotNull(jsonNode.get("physiologicalReactions"));
        assertEquals(1, jsonNode.get("physiologicalReactions").size());
        JsonNode physiologicalReactions = jsonNode.get("physiologicalReactions").get(0);
        assertNotNull(physiologicalReactions.get("directionType"));
        assertEquals("right-to-left", physiologicalReactions.get("directionType").asText());
        assertNotNull(physiologicalReactions.get("reactionCrossReference"));
        reactionCrossReferences = physiologicalReactions.get("reactionCrossReference");
        assertNotNull(reactionCrossReferences.get("database"));
        assertEquals("Rhea", reactionCrossReferences.get("database").asText());
        assertNotNull(reactionCrossReferences.get("id"));
        assertEquals("RHEA:313", reactionCrossReferences.get("id").asText());
        assertNotNull(physiologicalReactions.get("evidences"));
        assertEquals(1, physiologicalReactions.get("evidences").size());
        ValidateJson.validateEvidence(
                physiologicalReactions.get("evidences").get(0),
                "ECO:0000313",
                "Ensembl",
                "ENSP0001324");
    }

    public static CatalyticActivityComment getCatalyticActivityComment() {
        return new CatalyticActivityCommentBuilder()
                .molecule("Isoform 3")
                .physiologicalReactionsSet(createPhyReactions())
                .reaction(createReaction())
                .build();
    }

    private static List<PhysiologicalReaction> createPhyReactions() {
        List<Evidence> evidences =
                CreateUtils.createEvidenceList("ECO:0000313|Ensembl:ENSP0001324");

        List<PhysiologicalReaction> phyReactions = new ArrayList<>();
        phyReactions.add(
                new PhysiologicalReactionBuilder()
                        .directionType(PhysiologicalDirectionType.RIGHT_TO_LEFT)
                        .reactionCrossReference(
                                new CrossReferenceBuilder<ReactionDatabase>()
                                        .database(ReactionDatabase.RHEA)
                                        .id("RHEA:313")
                                        .build())
                        .evidencesSet(evidences)
                        .build());
        return phyReactions;
    }

    private static Reaction createReaction() {
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        String name = "some reaction";
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        return new ReactionBuilder()
                .name(name)
                .reactionCrossReferencesAdd(
                        new CrossReferenceBuilder<ReactionDatabase>()
                                .database(ReactionDatabase.CHEBI)
                                .id("ChEBI:3243")
                                .build())
                .ecNumber(ecNumber)
                .evidencesSet(evidences)
                .build();
    }
}
