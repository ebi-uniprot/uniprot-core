package org.uniprot.core.cv.keyword.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordId;

import java.util.UUID;

class KeywordImplTest {

    private String name;
    private String id;

    @BeforeEach
    void setUp() {
        String random = UUID.randomUUID().toString();
        this.name = "name-" + random;
        this.id = "id-" + random;
    }

    @Test
    void testCreateKeyword() {
        KeywordId kw = createKeyword(this.name, this.id);
        Assertions.assertEquals(this.name, kw.getName());
        Assertions.assertEquals(this.id, kw.getId());
    }

    @Test
    void testValueEqual() {
        KeywordId kw1 = createKeyword(this.name, this.id);
        KeywordId kw2 = createKeyword(this.name, this.id);
        Assertions.assertTrue(kw1.equals(kw2));
        Assertions.assertTrue(kw1.hashCode() == kw2.hashCode());
    }

    @Test
    void testRefEqual() {
        KeywordId kw1 = createKeyword(this.name, this.id);
        Assertions.assertTrue(kw1.equals(kw1));
        Assertions.assertTrue(kw1.hashCode() == kw1.hashCode());
    }

    @Test
    void testEqualWithNull() {
        KeywordId kw = createKeyword(this.name, this.id);
        Assertions.assertFalse(kw.equals(null));
    }

    private KeywordId createKeyword(String name, String id) {
        return new KeywordIdBuilder().name(name).id(id).build();
    }
}
