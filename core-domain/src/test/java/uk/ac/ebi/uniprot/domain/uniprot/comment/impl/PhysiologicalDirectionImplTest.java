package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;


class PhysiologicalDirectionImplTest {

    @Test
    void testCreate() {
        List<Evidence> evidences = createEvidences();
        PhysiologicalReactionImpl reaction = new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                .reactionReference(new DBCrossReferenceBuilder<ReactionReferenceType>()
                                           .databaseType(ReactionReferenceType.RHEA)
                                           .id("RHEA:123").build())
                .evidences(evidences)
                .build();

        assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, reaction.getDirectionType());
        assertEquals("RHEA:123", reaction.getReactionReference().getId());
        TestHelper.verifyJson(reaction);
    }

}
