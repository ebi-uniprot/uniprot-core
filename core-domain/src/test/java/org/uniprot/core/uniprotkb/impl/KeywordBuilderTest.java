package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

public class KeywordBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Keyword obj = new KeywordBuilder().build();
        KeywordBuilder builder = KeywordBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        Keyword obj = new KeywordBuilder().build();
        Keyword obj2 = new KeywordBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    @Test
    void canSetIdFromBuilder() {
        Keyword keyword = new KeywordBuilder().id("id").build();
        assertEquals("id", keyword.getId());
    }

    @Test
    void canSetCategoryFromBuilder() {
        Keyword keyword = new KeywordBuilder().category(KeywordCategory.UNKNOWN).build();
        assertEquals(KeywordCategory.UNKNOWN, keyword.getCategory());
    }

    public static Keyword createObject(int listSize, boolean includeEvidences) {
        KeywordBuilder builder = new KeywordBuilder();
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        String name = "name-" + random;
        KeywordCategory kc = KeywordCategory.UNKNOWN;
        List<Evidence> evidences =
                includeEvidences ? EvidenceBuilderTest.createObjects(listSize) : new ArrayList<>();
        builder.id(id).name(name).category(kc).evidencesSet(evidences);
        return builder.build();
    }

    public static Keyword createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static Keyword createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Keyword> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
