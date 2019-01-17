package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation2.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.ReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

class CatalyticActivityCommentImplTest {
    @Test
    void testAll() {
        Reaction reaction = createReaction();
        List<PhysiologicalReaction> phyReactions = createPhyReaction();
        CatalyticActivityComment comment = new CatalyticActivityCommentImpl(reaction, phyReactions);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        assertEquals(reaction, comment.getReaction());
        assertEquals(phyReactions, comment.getPhysiologicalReactions());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testOnlyReaction() {
        Reaction reaction = createReaction();

        CatalyticActivityComment comment = new CatalyticActivityCommentImpl(reaction, Collections.emptyList());
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        assertEquals(reaction, comment.getReaction());
        assertTrue(comment.getPhysiologicalReactions().isEmpty());
        TestHelper.verifyJson(comment);
    }

    private List<PhysiologicalReaction> createPhyReaction() {
        List<PhysiologicalReaction> phyReactions = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        phyReactions.add(new PhysiologicalReactionBuilder()
                                 .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                                 .reactionReference(new DBCrossReferenceBuilder<ReactionReferenceType>()
                                                            .databaseType(ReactionReferenceType.RHEA)
                                                            .id("RHEA:123")
                                                            .build())
                                 .evidences(evidences).build()
        );
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

    private Reaction createReaction() {
        List<Evidence> evidences = createEvidences();
        String name = "some reaction";
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
        references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
        ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
        return new ReactionBuilder().name(name).references(references).ecNumber(ecNumber).evidences(evidences).build();
    }
}
