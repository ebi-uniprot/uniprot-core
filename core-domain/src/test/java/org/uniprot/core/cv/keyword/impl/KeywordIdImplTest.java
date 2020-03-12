package org.uniprot.core.cv.keyword.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordId;

class KeywordIdImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        KeywordId obj = new KeywordIdImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        KeywordId impl = new KeywordIdImpl("name", "id");
        KeywordId obj = KeywordIdBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
