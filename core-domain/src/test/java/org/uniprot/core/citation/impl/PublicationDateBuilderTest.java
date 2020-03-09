package org.uniprot.core.citation.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.PublicationDate;

class PublicationDateBuilderTest {
    @Test
    void canCreateWithAnyStringVal() {
        String str = "any string";
        PublicationDate publicationDate = new PublicationDateBuilder(str).build();
        assertNotNull(publicationDate);
        assertEquals(str, publicationDate.getValue());
    }
}
