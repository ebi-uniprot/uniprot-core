package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

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
                        .evidencesSet(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = "Some molecule";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactorsSet(cofactors)
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
                        .evidencesSet(createEvidences())
                        .build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = null;
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactorsSet(cofactors)
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
        Note note = null;
        String molecule = "Some molecule";
        CofactorComment comment =
                new CofactorCommentBuilder()
                        .molecule(molecule)
                        .cofactorsSet(cofactors())
                        .note(note)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(2, comment.getCofactors().size());
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
                        .cofactorsSet(cofactors)
                        .note(note)
                        .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CofactorComment obj = new CofactorCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CofactorComment impl = new CofactorCommentImpl("mol", cofactors(), createNote());
        CofactorComment obj = CofactorCommentBuilder.from(impl).build();

        assertTrue(impl.isValid());
        assertTrue(impl.hasCofactors());
        assertTrue(impl.hasMolecule());
        assertTrue(impl.hasNote());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
