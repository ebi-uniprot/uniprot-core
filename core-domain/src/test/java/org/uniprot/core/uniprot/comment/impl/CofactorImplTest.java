package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class CofactorImplTest {
    @Test
    void testCofactorImpl() {
        String name = "Some cofactor";
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
        List<Evidence> evidences = createEvidences();
        Cofactor cofactor =
                new CofactorBuilder()
                        .name(name)
                        .reference(reference)
                        .evidences(createEvidences())
                        .build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
    }

    @Test
    void testCofactorImplNoEvidence() {
        String name = "Some cofactor";
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI, "ChEBI:213");
        List<Evidence> evidences = Collections.emptyList();
        Cofactor cofactor =
                new CofactorBuilder().name(name).reference(reference).evidences(evidences).build();
        assertEquals(name, cofactor.getName());
        assertEquals(reference, cofactor.getCofactorReference());
        assertEquals(evidences, cofactor.getEvidences());
    }
}
