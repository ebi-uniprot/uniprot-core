package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;

import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

class CofactorCommentBuilderTest {
    @Test
    void testNewInstance() {
        CofactorCommentBuilder builder1 = new CofactorCommentBuilder();
        CofactorCommentBuilder builder2 = new CofactorCommentBuilder();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1 == builder2);
    }

    @Test
    void testSetMolecule() {
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        String molecule = "some mol";
        CofactorComment comment =
                builder.molecule(molecule)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertFalse(comment.getNote() != null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testSetCofactors() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = singletonList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        String molecule = "some mol";
        CofactorComment comment =
                builder.molecule(molecule)
                        .cofactors(cofactors)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertFalse(comment.getNote() != null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testSetNote() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();
        String molecule = "";
        CofactorComment comment =
                builder.molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
        assertFalse(comment.getMolecule() != null);
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testCreateCofactorReference() {
        CofactorReferenceType type = CofactorReferenceType.CHEBI;
        String referenceId = "CHEBI:314";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(type, referenceId);
        assertEquals(type, reference.getDatabaseType());
        assertEquals(referenceId, reference.getId());
    }

    @Test
    void testCreateCofactor() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(2, cofactor.getEvidences().size());
    }

    private Cofactor createCofactor(String name, DBCrossReference<CofactorReferenceType> reference, List<Evidence> evidences) {
        return new CofactorBuilder()
                .name(name)
                .reference(reference)
                .evidences(evidences)
                .build();
    }
}
