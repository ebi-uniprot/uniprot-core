package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Author;

class AuthorImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Author obj = new AuthorImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Author impl = new AuthorImpl("auth");
        Author obj = AuthorBuilder.from(impl).build();

        assertTrue(impl.hasValue());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void null_author() {
        Author impl = new AuthorImpl(null);
        Author obj = AuthorBuilder.from(impl).build();

        assertFalse(impl.hasValue());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void toStringMethod() {
        Author impl = new AuthorImpl("auth");
        assertEquals("auth", impl.toString());
    }
}
