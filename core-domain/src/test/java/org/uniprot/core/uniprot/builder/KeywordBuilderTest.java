package org.uniprot.core.uniprot.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;

class KeywordBuilderTest {
    @Test
    void canCreateBuilderFromInstance() {
        Keyword obj = new KeywordBuilder().build();
        KeywordBuilder builder = new KeywordBuilder().from(obj);
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
}
