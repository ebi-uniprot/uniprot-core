package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniProtKBIdBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBId obj = new UniProtKBIdBuilder("val").build();
        UniProtKBIdBuilder builder = UniProtKBIdBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBId obj = new UniProtKBIdBuilder("val").build();
        UniProtKBId obj2 = new UniProtKBIdBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static UniProtKBId createObject() {
        String random = UUID.randomUUID().toString();
        String kbId = "kbId-" + random;
        UniProtKBId obj = new UniProtKBIdBuilder(kbId).build();
        assertNotNull(obj);
        assertEquals(kbId, obj.getValue());
        return obj;
    }

    public static List<UniProtKBId> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
