package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteImplTest {

    @Test
    void testNoteImpl() {
        List<EvidencedValue> texts = new ArrayList<>();
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
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
        TestHelper.verifyJson(note);
    }

    @Test
    void testNoteImplEmpty() {
        List<EvidencedValue> texts = new ArrayList<>();
        Note note = new NoteBuilder(texts).build();
        assertEquals(texts, note.getTexts());
        TestHelper.verifyJson(note);
    }
}
