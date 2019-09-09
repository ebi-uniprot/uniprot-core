package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.comment.Absorption;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.AbsorptionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;
import static org.uniprot.core.uniprot.comment.impl.ImplTestHelper.createNote;

class AbsorptionImplTest {
    @Test
    void testAbsorptionImpl() {
        Note note = createNote();
        List<Evidence> evidences = createEvidences();
        Absorption absorption = new AbsorptionBuilder()
                .evidences(evidences)
                .note(note)
                .max(32)
                .build();
        assertEquals(32, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertEquals(note, absorption.getNote());
        assertEquals(evidences, absorption.getEvidences());
    }

    @Test
    void testAbsorptionImplNoNote() {
        Note note = null;
        List<Evidence> evidences = null;
        Absorption absorption = new AbsorptionBuilder()
                .approximate(true)
                .evidences(evidences)
                .note(note)
                .max(35)
                .build();
        assertEquals(35, absorption.getMax());
        assertTrue(absorption.isApproximate());
        assertEquals(note, absorption.getNote());
        assertTrue(absorption.getEvidences().isEmpty());
    }
}
