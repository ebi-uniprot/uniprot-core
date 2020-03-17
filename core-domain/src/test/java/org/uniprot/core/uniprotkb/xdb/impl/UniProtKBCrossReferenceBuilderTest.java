package org.uniprot.core.uniprotkb.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

class UniProtKBCrossReferenceBuilderTest {

    @Test
    void defaultEvidences_willNotNull() {
        UniProtKBCrossReference reference = new UniProtCrossReferenceBuilder().build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtKBCrossReference reference =
                new UniProtCrossReferenceBuilder()
                        .evidencesAdd(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        UniProtKBCrossReference reference =
                new UniProtCrossReferenceBuilder().evidencesAdd(null).build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void evidences_willConvertModifiable_toUnModifiable() {
        UniProtKBCrossReference reference =
                new UniProtCrossReferenceBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBCrossReference reference = new UniProtCrossReferenceBuilder().build();
        UniProtCrossReferenceBuilder builder = UniProtCrossReferenceBuilder.from(reference);
        assertNotNull(builder);
    }
}
