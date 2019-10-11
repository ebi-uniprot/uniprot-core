package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.uniprot.core.ObjectsForTests.createEvidences;
import static org.uniprot.core.ObjectsForTests.createNote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.comment.builder.CofactorCommentBuilder;

class CofactorCommentImplTest {
    @Test
    void testCofactorCommentImpl() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceBuilder<CofactorReferenceType>()
                        .databaseType(CofactorReferenceType.CHEBI)
                        .id("CHEBI:324")
                        .build();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidences(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = "Some molecule";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testCofactorCommentImplNoMolecule() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidences(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = "";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
        assertEquals(null, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testCofactorCommentImplNoNote() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidences(createEvidences())
                        .build();
        DBCrossReference<CofactorReferenceType> reference2 =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:31324");
        Cofactor cofactor2 =
                new CofactorBuilder()
                        .name("Some name")
                        .reference(reference2)
                        .evidences(createEvidences())
                        .build();
        ;
        List<Cofactor> cofactors = Arrays.asList(cofactor, cofactor2);

        Note note = null;
        String molecule = "Some molecule";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(2, comment.getCofactors().size());
        assertEquals(cofactor2, comment.getCofactors().get(1));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testCofactorCommentImplNoCofactor() {

        List<Cofactor> cofactors = Collections.emptyList();

        Note note = createNote();
        String molecule = "Some molecule";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactors(cofactors)
                        .note(note)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }
}
