package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.PublicationDate;
import org.uniprot.core.citation.builder.PublicationDateBuilder;

class PublicationDateImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        PublicationDate obj = new PublicationDateImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        PublicationDate impl = new PublicationDateImpl("date");
        PublicationDate obj = new PublicationDateBuilder(null).from(impl).build();

        assertTrue(impl.hasValue());
        assertTrue(obj.hasValue());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
