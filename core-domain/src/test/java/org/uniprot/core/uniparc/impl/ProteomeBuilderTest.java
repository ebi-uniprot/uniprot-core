package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.Proteome;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeBuilderTest {
    @Test
    void testId() {
        Proteome domain = new ProteomeBuilder().id("someId").build();
        assertEquals("someId", domain.getId());
    }

    @Test
    void testComponent() {
        Proteome domain = new ProteomeBuilder().component("some name").build();
        assertEquals("some name", domain.getComponent());
    }

    @Test
    void testFrom() {
        Proteome proteome1 = new ProteomeBuilder().id("some id").component("some name").build();
        Proteome proteome2 = ProteomeBuilder.from(proteome1).build();
        assertEquals(proteome1, proteome2);
        Proteome proteome3 = ProteomeBuilder.from(proteome1).component("component2").build();
        assertEquals("some id", proteome3.getId());
        assertEquals("component2", proteome3.getComponent());
    }
}