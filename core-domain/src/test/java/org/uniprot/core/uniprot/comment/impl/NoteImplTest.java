package org.uniprot.core.uniprot.comment.impl;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueImpl;

class NoteImplTest {

    @Test
    void testNoteImpl() {
        List<EvidencedValue> texts = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        texts.add(new EvidencedValueBuilder("value 1", evidences).build());
        texts.add(new EvidencedValueBuilder("value2", emptyList()).build());

        Note note = new NoteBuilder(texts).build();
        assertEquals(texts, note.getTexts());
    }

    @Test
    void testNoteImplEmpty() {
        List<EvidencedValue> texts = new ArrayList<>();
        Note note = new NoteBuilder(texts).build();
        assertEquals(texts, note.getTexts());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Note obj = new NoteImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        EvidencedValue ev1 = new EvidencedValueImpl("1", createEvidences());
        EvidencedValue ev2 = new EvidencedValueImpl("1", null);
        List<EvidencedValue> evidencedValues = Arrays.asList(ev1, ev2);

        Note impl = new NoteImpl(evidencedValues);
        Note obj = new NoteBuilder(null).from(impl).build();

        assertTrue(impl.isValid());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
