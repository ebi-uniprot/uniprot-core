package org.uniprot.core.uniprotkb.evidence.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprotkb.evidence.impl.EvidenceImplTest.getEvidenceCrossRef;

public class EvidenceBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Evidence obj = new EvidenceBuilder().build();
        EvidenceBuilder builder = EvidenceBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Evidence obj = new EvidenceBuilder().build();
        Evidence obj2 = new EvidenceBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void buildWithCrossReference(){
        CrossReference<EvidenceDatabase> xref = getEvidenceCrossRef();
        Evidence obj = new EvidenceBuilder().crossReference(xref).build();
        assertEquals(xref, obj.getEvidenceCrossReference());
        assertNull(obj.getEvidenceCode());
    }

    public static Evidence createObject(int listSize) {
        int length = EvidenceCode.values().length;
        int randomIndex = ThreadLocalRandom.current().nextInt(0, length);
        EvidenceCode code = EvidenceCode.values()[randomIndex];
        String random = UUID.randomUUID().toString();
        String dbName = "name-" + random;
        String dbId = "id-" + random;
        EvidenceBuilder builder = new EvidenceBuilder();
        builder.databaseName(dbName);
        builder.databaseId(dbId);
        builder.evidenceCode(code);
        return builder.build();
    }

    public static Evidence createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Evidence> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
