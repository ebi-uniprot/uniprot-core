package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.comment.impl.PhysiologicalReactionImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;


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
