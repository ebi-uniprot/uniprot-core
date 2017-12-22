package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
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
                builder.setMolecule(molecule)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(0, comment.getCofactors().size());
        assertNull(comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    public void testSetCofactors() {
        String name ="someName";
        CofactorReference reference =CofactorCommentBuilder.createCofactorReference(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, createEvidences(), reference);
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
        String molecule ="some mol";
        CofactorComment comment =
                builder.setMolecule(molecule)
                .setCofactors(cofactors)
                .build();
        assertEquals(molecule, comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        assertEquals(cofactor, comment.getCofactors().get(0));
        assertNull(comment.getNote());
        assertEquals(CommentType.COFACTOR, comment.getCommentType());
    }

    @Test
    public void testSetNote() {
        String name ="someName";
        CofactorReference reference =CofactorCommentBuilder.createCofactorReference(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, createEvidences(), reference);
        List<Cofactor> cofactors = Arrays.asList(cofactor);
        CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
        CommentNote note = CommentBuilderUtil.createCommentNote(createEvidenceValues());
        String molecule ="some mol";
        CofactorComment comment =
                builder.setMolecule(molecule)
                .setCofactors(cofactors)
                .setNote(note)
                .build();
        assertEquals(molecule, comment.getMolecule());
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
        CofactorReference reference =CofactorCommentBuilder.createCofactorReference(type, referenceId);
        assertEquals(type, reference.getCofactorReferenceType());
        assertEquals(referenceId, reference.getReferenceId());
    }

    @Test
    public void testCreateCofactor() {
        String name ="someName";
        CofactorReference reference =CofactorCommentBuilder.createCofactorReference(CofactorReferenceType.CHEBI, "CHEBI:324");
        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, createEvidences(), reference);
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(2, cofactor.getEvidences().size());
    }
    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(EvidenceFactory.from("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(EvidenceFactory.from("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.createEvidencedValue("value2", Collections.emptyList()));
        return evidencedValues;
    }
}
