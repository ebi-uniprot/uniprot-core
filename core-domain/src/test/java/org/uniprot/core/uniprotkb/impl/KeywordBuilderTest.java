package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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

    public static Keyword createObject() {
        KeywordBuilder builder = new KeywordBuilder();
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        String name = "name-" + random;
        int rIndex = ThreadLocalRandom.current().nextInt(0, KeywordCategory.values().length);
        KeywordCategory kc = KeywordCategory.values()[rIndex];
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(3);
        builder.id(id).name(name).category(kc).evidencesSet(evidences);
        return builder.build();
    }
}
