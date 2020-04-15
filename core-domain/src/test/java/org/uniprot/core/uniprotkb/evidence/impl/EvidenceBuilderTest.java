package org.uniprot.core.uniprotkb.evidence.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;

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

    public static Evidence createObject() {
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

    public static List<Evidence> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
