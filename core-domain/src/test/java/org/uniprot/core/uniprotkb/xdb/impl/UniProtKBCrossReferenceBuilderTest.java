package org.uniprot.core.uniprotkb.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.uniprot.core.UniProtKBDatabaseMock;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

public class UniProtKBCrossReferenceBuilderTest {

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

    public static UniProtKBCrossReference createObject() {
        UniProtCrossReferenceBuilder builder = new UniProtCrossReferenceBuilder();
        UniProtKBDatabase database = new UniProtKBDatabaseMock("EMBL");
        String random = UUID.randomUUID().toString();
        String id = "id" + random;
        String isoformId = "isoform" + random;
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(4);
        builder.database(database).id(id).isoformId(isoformId);
        builder.propertiesAdd("prop1" + random, "value" + random);
        builder.evidencesSet(evidences);
        return builder.build();
    }
}
