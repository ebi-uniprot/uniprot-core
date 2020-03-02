package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.builder.CrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.ReactionDatabase;
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
                                new CrossReferenceBuilder<ReactionDatabase>()
                                        .databaseType(ReactionDatabase.RHEA)
                                        .id("RHEA:123")
                                        .build())
                        .evidencesSet(evidences)
                        .build();

        assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, reaction.getDirectionType());
        assertEquals("RHEA:123", reaction.getReactionReference().getId());
    }
}
