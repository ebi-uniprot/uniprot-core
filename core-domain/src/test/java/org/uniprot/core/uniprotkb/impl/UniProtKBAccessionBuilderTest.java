package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

public class UniProtKBAccessionBuilderTest {

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBAccession obj = new UniProtKBAccessionBuilder("val").build();
        UniProtKBAccessionBuilder builder = UniProtKBAccessionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtKBAccession obj = new UniProtKBAccessionBuilder("val").build();
        UniProtKBAccession obj2 = new UniProtKBAccessionBuilder("val").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static UniProtKBAccession createObject() {
        String random = UUID.randomUUID().toString();
        String kbId = "accession-" + random;
        UniProtKBAccession obj = new UniProtKBAccessionBuilder(kbId).build();
        assertNotNull(obj);
        assertEquals(kbId, obj.getValue());
        return obj;
    }

    public static List<UniProtKBAccession> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
