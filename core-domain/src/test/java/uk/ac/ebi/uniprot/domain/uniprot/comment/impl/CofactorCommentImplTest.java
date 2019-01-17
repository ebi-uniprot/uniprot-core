package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation2.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;
import static uk.ac.ebi.uniprot.domain.uniprot.comment.impl.ImplTestHelper.createNote;

class CofactorCommentImplTest {
    @Test
    void testCofactorCommentImpl() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceBuilder<CofactorReferenceType>()
                .databaseType(CofactorReferenceType.CHEBI)
                .id("CHEBI:324").build();
        Cofactor cofactor = new CofactorBuilder().name(name).reference(reference).evidences(createEvidences()).build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentBuilder().molecule(molecule).cofactors(cofactors).note(note)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testCofactorCommentImplNoMolecule() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
                                                                                       "CHEBI:324");
        Cofactor cofactor = new CofactorBuilder().name(name).reference(reference).evidences(createEvidences()).build();
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = createNote();
        String molecule = "";
        CofactorComment comment = new CofactorCommentBuilder().molecule(molecule).cofactors(cofactors).note(note)
                .build();
        assertEquals(null, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testCofactorCommentImplNoNote() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
                                                                                       "CHEBI:324");
        Cofactor cofactor = new CofactorBuilder().name(name).reference(reference).evidences(createEvidences()).build();
        DBCrossReference<CofactorReferenceType> reference2 = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
                                                                                        "CHEBI:31324");
        Cofactor cofactor2 = new CofactorBuilder().name("Some name").reference(reference2).evidences(createEvidences())
                .build();
        ;
        List<Cofactor> cofactors = Arrays.asList(cofactor, cofactor2);

        Note note = null;
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentBuilder().molecule(molecule).cofactors(cofactors).note(note)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(2, comment.getCofactors().size());
        assertEquals(cofactor2, comment.getCofactors().get(1));
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    void testCofactorCommentImplNoCofactor() {

        List<Cofactor> cofactors = Collections.emptyList();

        Note note = createNote();
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentBuilder().molecule(molecule).cofactors(cofactors).note(note)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
}
