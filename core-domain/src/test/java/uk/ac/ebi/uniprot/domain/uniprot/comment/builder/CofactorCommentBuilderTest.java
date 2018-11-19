package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class CofactorCommentBuilderTest {
    @Test
    public void testNewInstance() {
        CofactorCommentBuilder builder1 = CofactorCommentBuilder.newInstance();
        CofactorCommentBuilder builder2 = CofactorCommentBuilder.newInstance();
        assertNotNull(builder1);
        assertNotNull(builder2);
        assertFalse(builder1== builder2);
    }
    
    @Test
    public void testSetMolecule() {
        CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
        String molecule ="some mol";
        CofactorComment comment =
                builder.molecule(molecule)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertFalse(comment.getNote() !=null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    public void testSetCofactors() {
        String name ="someName";
        DBCrossReference<CofactorReferenceType> reference =new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name,reference,  createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
        String molecule ="some mol";
        CofactorComment comment =
                builder.molecule(molecule)
                .cofactors(cofactors)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertFalse(comment.getNote() !=null);
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    public void testSetNote() {
        String name ="someName";
        DBCrossReference<CofactorReferenceType> reference =new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
        Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
        String molecule ="";
        CofactorComment comment =
                builder.molecule(molecule)
                .cofactors(cofactors)
                .note(note)
                .build();
        assertFalse( comment.getMolecule() !=null);
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertNotNull(comment.getNote());
        assertEquals(note, comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    public void testCreateCofactorReference() {
        CofactorReferenceType type = CofactorReferenceType.CHEBI;
        String referenceId = "CHEBI:314";
        DBCrossReference<CofactorReferenceType> reference =new DBCrossReferenceImpl<>(type, referenceId);
        assertEquals(type, reference.getDatabaseType());
        assertEquals(referenceId, reference.getId());
    }

    @Test
    public void testCreateCofactor() {
        String name ="someName";
        DBCrossReference<CofactorReferenceType> reference =new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(2, cofactor.getEvidences().size());
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
