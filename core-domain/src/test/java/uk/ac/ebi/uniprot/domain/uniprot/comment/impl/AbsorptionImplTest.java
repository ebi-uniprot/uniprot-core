package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.AbsorptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ImplTestHelper.createNote;

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
        TestHelper.verifyJson(absorption);
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
        TestHelper.verifyJson(absorption);
    }
}
