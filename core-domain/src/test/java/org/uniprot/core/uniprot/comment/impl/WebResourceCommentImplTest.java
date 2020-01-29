package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.builder.WebResourceCommentBuilder;

class WebResourceCommentImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        WebResourceComment obj = new WebResourceCommentImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        WebResourceComment impl = new WebResourceCommentImpl("mol", "resc", "url", false, "note");
        WebResourceComment obj = WebResourceCommentBuilder.from(impl).build();

        assertTrue(impl.hasNote());
        assertTrue(impl.hasResourceName());
        assertTrue(impl.hasResourceUrl());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
