package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;

import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.uniprot.comment.builder.DiseaseCommentBuilder;

class DiseaseCommentImplTest {

    private Disease disease =
            new DiseaseBuilder()
                    .diseaseId("someId")
                    .diseaseAc("I-00742")
                    .acronym("someAcron")
                    .evidences(createEvidences())
                    .description("some description")
                    .reference(new DBCrossReferenceImpl<>(DiseaseReferenceType.MIM, "3124"))
                    .build();;

    @Test
    void testDiseaseCommentImpl() {
        Note note = createNote();

        String molecule = "isoform 1";

        DiseaseComment comment = new DiseaseCommentImpl(molecule, disease, note);
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.DISEASE, comment.getCommentType());
        assertEquals(disease, comment.getDisease());
        assertEquals(molecule, comment.getMolecule());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        DiseaseComment obj = new DiseaseCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DiseaseComment impl = new DiseaseCommentImpl("molecule", disease, createNote());
        DiseaseComment obj = DiseaseCommentBuilder.from(impl).build();

        assertTrue(impl.hasDefinedDisease());
        assertTrue(impl.hasNote());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
