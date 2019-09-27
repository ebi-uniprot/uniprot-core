package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class PhysiologicalDirectionImplTest {

    @Test
    void testCreate() {
        List<Evidence> evidences = createEvidences();
        PhysiologicalReactionImpl reaction =
                new PhysiologicalReactionBuilder()
                        .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                        .reactionReference(
                                new DBCrossReferenceBuilder<ReactionReferenceType>()
                                        .databaseType(ReactionReferenceType.RHEA)
                                        .id("RHEA:123")
                                        .build())
                        .evidences(evidences)
                        .build();

        assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, reaction.getDirectionType());
        assertEquals("RHEA:123", reaction.getReactionReference().getId());
    }
}
