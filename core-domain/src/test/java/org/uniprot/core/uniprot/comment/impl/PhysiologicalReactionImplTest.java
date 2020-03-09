package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.crossReference;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.ReactionDatabase;

class PhysiologicalReactionImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        PhysiologicalReaction obj = new PhysiologicalReactionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        PhysiologicalReaction impl =
                new PhysiologicalReactionImpl(
                        PhysiologicalDirectionType.RIGHT_TO_LEFT,
                        crossReference(ReactionDatabase.RHEA, "RHEA:123"),
                        createEvidences());
        PhysiologicalReaction obj = PhysiologicalReactionBuilder.from(impl).build();

        assertTrue(impl.hasDirectionType());
        assertTrue(impl.hasReactionCrossReference());
        assertTrue(impl.hasEvidences());
        assertTrue(impl.getEvidences().size() > 0);

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
