package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprotkb.comment.ReactionDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class PhysiologicalDirectionImplTest {

    @Test
    void testCreate() {
        List<Evidence> evidences = createEvidences();
        PhysiologicalReactionImpl reaction =
                new PhysiologicalReactionBuilder()
                        .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                        .reactionCrossReference(
                                new CrossReferenceBuilder<ReactionDatabase>()
                                        .database(ReactionDatabase.RHEA)
                                        .id("RHEA:123")
                                        .build())
                        .evidencesSet(evidences)
                        .build();

        assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, reaction.getDirectionType());
        assertEquals("RHEA:123", reaction.getReactionCrossReference().getId());
    }
}
