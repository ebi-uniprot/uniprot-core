package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

class CofactorCommentImplTest {

    @Test
    void testCofactorCommentImpl() {
        String name = "someName";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
                                                                                       "CHEBI:324");
        Cofactor cofactor = CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentImpl(molecule, cofactors, note);
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
        Cofactor cofactor = CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        String molecule = "";
        CofactorComment comment = new CofactorCommentImpl(molecule, cofactors, note);
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
        Cofactor cofactor = CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
        DBCrossReference<CofactorReferenceType> reference2 = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
                                                                                        "CHEBI:31324");
        Cofactor cofactor2 = CofactorCommentBuilder.createCofactor("Some Name 2 ", reference2, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor, cofactor2);

        Note note = null;
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentImpl(molecule, cofactors, note);
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

        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        String molecule = "Some molecule";
        CofactorComment comment = new CofactorCommentImpl(molecule, cofactors, note);
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
