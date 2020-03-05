package org.uniprot.core.uniprot.xdb.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

class UniProtCrossReferenceBuilderTest {

    @Test
    void defaultEvidences_willNotNull() {
        UniProtCrossReference reference = new UniProtCrossReferenceBuilder().build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtCrossReference reference =
                new UniProtCrossReferenceBuilder()
                        .evidencesAdd(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        UniProtCrossReference reference =
                new UniProtCrossReferenceBuilder().evidencesAdd(null).build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void evidences_willConvertModifiable_toUnModifiable() {
        UniProtCrossReference reference =
                new UniProtCrossReferenceBuilder()
                        .evidencesSet(Collections.emptyList())
                        .evidencesAdd(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtCrossReference reference = new UniProtCrossReferenceBuilder().build();
        UniProtCrossReferenceBuilder builder = UniProtCrossReferenceBuilder.from(reference);
        assertNotNull(builder);
    }
}
