package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;

class SequenceCautionCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        SequenceCautionComment obj = new SequenceCautionCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        SequenceCautionComment impl =
                new SequenceCautionCommentImpl(
                        "molecule",
                        SequenceCautionType.ERRONEOUS_TERMINATION,
                        "sequ",
                        "note",
                        createEvidences());
        SequenceCautionComment obj = SequenceCautionCommentBuilder.from(impl).build();

        assertTrue(impl.hasNote());
        assertTrue(impl.hasSequence());
        assertTrue(impl.hasSequenceCautionType());
        assertTrue(impl.hasEvidences());
        assertFalse(impl.getEvidences().isEmpty());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
