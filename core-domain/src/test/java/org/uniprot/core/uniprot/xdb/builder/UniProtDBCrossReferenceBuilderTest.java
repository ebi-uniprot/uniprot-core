package org.uniprot.core.uniprot.xdb.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

class UniProtDBCrossReferenceBuilderTest {

    @Test
    void defaultEvidences_willNotNull() {
        UniProtDBCrossReference reference = new UniProtDBCrossReferenceBuilder().build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void canAddSingleEvidence() {
        UniProtDBCrossReference reference =
                new UniProtDBCrossReferenceBuilder()
                        .addEvidence(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void nullEvidence_willBeIgnore() {
        UniProtDBCrossReference reference =
                new UniProtDBCrossReferenceBuilder().addEvidence(null).build();
        assertNotNull(reference.getEvidences());
        assertTrue(reference.getEvidences().isEmpty());
    }

    @Test
    void evidences_willConvertModifiable_toUnModifiable() {
        UniProtDBCrossReference reference =
                new UniProtDBCrossReferenceBuilder()
                        .evidences(Collections.emptyList())
                        .addEvidence(new EvidenceBuilder().build())
                        .build();
        assertNotNull(reference.getEvidences());
        assertFalse(reference.getEvidences().isEmpty());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtDBCrossReference reference = new UniProtDBCrossReferenceBuilder().build();
        UniProtDBCrossReferenceBuilder builder = UniProtDBCrossReferenceBuilder.from(reference);
        assertNotNull(builder);
    }
}
