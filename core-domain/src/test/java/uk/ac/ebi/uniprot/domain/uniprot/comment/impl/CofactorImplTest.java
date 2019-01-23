package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;

class CofactorImplTest {
    @Test
    void testCofactorImpl() {
        String name = "Some cofactor";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
        List<Evidence> evidences = createEvidences();
        Cofactor cofactor = new CofactorBuilder().name(name).reference(reference).evidences(createEvidences()).build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
        TestHelper.verifyJson(cofactor);
    }

    @Test
    void testCofactorImplNoEvidence() {
        String name = "Some cofactor";
        DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
        List<Evidence> evidences = Collections.emptyList();
        Cofactor cofactor = new CofactorBuilder().name(name).reference(reference).evidences(evidences).build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
        TestHelper.verifyJson(cofactor);
    }
}
