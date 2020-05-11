package org.uniprot.core.uniprotkb.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Absorption;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.List;

class AbsorptionImplTest {
    @Test
    void testAbsorptionImpl() {
        Note note = createNote();
        List<Evidence> evidences = createEvidences();
        Absorption absorption =
                new AbsorptionBuilder().evidencesSet(evidences).note(note).max(32).build();
        assertEquals(32, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertEquals(note, absorption.getNote());
        assertEquals(evidences, absorption.getEvidences());
    }

    @Test
    void testAbsorptionImplNoNote() {
        Note note = null;
        List<Evidence> evidences = null;
        Absorption absorption =
                new AbsorptionBuilder()
                        .approximate(true)
                        .evidencesSet(evidences)
                        .note(note)
                        .max(35)
                        .build();
        assertEquals(35, absorption.getMax());
        assertTrue(absorption.isApproximate());
        assertEquals(note, absorption.getNote());
        assertTrue(absorption.getEvidences().isEmpty());
    }

    @Test
    void zeroIsNotValidMax() {
        Absorption obj = new AbsorptionBuilder().max(0).build();
        assertFalse(obj.hasMax());
    }

    @Test
    void textMissingFromNoteIsNotValid() {
        Note note = new NoteImpl();
        Absorption obj = new AbsorptionBuilder().note(note).build();
        assertFalse(obj.hasNote());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Absorption obj = new AbsorptionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Absorption impl = new AbsorptionImpl(10, false, new NoteImpl(), createEvidences());
        Absorption obj = AbsorptionBuilder.from(impl).build();

        assertTrue(impl.hasEvidences());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
