package org.uniprot.core.uniprotkb.comment.impl;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class CofactorCommentBuilderTest {
    private final CrossReference<CofactorDatabase> reference =
            crossReference(CofactorDatabase.CHEBI, "CHEBI:324");

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
        CofactorComment comment = builder.molecule(molecule).build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertFalse(comment.getNote() != null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testSetCofactors() {
        String name = "someName";
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = singletonList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        String molecule = "some mol";
        CofactorComment comment = builder.molecule(molecule).cofactorsSet(cofactors).build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertFalse(comment.getNote() != null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testSetNote() {
        String name = "someName";
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        Note note = new NoteBuilder(createEvidenceValuesWithoutEvidences()).build();
        String molecule = "Isoform 2";
        CofactorComment comment =
                builder.molecule(molecule).cofactorsSet(cofactors).note(note).build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    void testCreateCofactorReference() {
        CofactorDatabase type = CofactorDatabase.CHEBI;
        String referenceId = "CHEBI:314";
        CrossReference<CofactorDatabase> reference =
                new CrossReferenceBuilder<CofactorDatabase>()
                        .database(type)
                        .id(referenceId)
                        .build();
        assertEquals(type, reference.getDatabase());
        assertEquals(referenceId, reference.getId());
    }

    @Test
    void testCreateCofactor() {
        String name = "someName";
        Cofactor cofactor = createCofactor(name, reference, createEvidences());
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorCrossReference());
        assertEquals(2, cofactor.getEvidences().size());
    }

    @Test
    void canAddSingleCofactor() {
        Cofactor cofactor = createCofactor("name", reference, createEvidences());
        CofactorComment obj = new CofactorCommentBuilder().cofactorsAdd(cofactor).build();
        assertNotNull(obj.getCofactors());
        assertFalse(obj.getCofactors().isEmpty());
        assertTrue(obj.hasCofactors());
    }

    @Test
    void nullCofactor_willBeIgnore() {
        CofactorComment obj = new CofactorCommentBuilder().cofactorsAdd(null).build();
        assertNotNull(obj.getCofactors());
        assertTrue(obj.getCofactors().isEmpty());
        assertFalse(obj.hasCofactors());
    }

    @Test
    void canCreateBuilderFromInstance() {
        CofactorComment obj = new CofactorCommentBuilder().build();
        CofactorCommentBuilder builder = CofactorCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        CofactorComment obj = new CofactorCommentBuilder().build();
        CofactorComment obj2 = new CofactorCommentBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    private Cofactor createCofactor(
            String name, CrossReference<CofactorDatabase> reference, List<Evidence> evidences) {
        return new CofactorBuilder()
                .name(name)
                .cofactorCrossReference(reference)
                .evidencesSet(evidences)
                .build();
    }
}
