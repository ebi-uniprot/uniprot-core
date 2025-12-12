package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.ProteomeIdComponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ProteomeIdComponentBuilderTest {

    public static final String PROTEOME_ID = "upi";
    public static final String COMPONENT = "component";

    @Test
    void proteomeId() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertSame(PROTEOME_ID, proteomeIdComponent.getProteomeId());
    }

    @Test
    void component() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertSame(COMPONENT, proteomeIdComponent.getComponent());
    }

    @Test
    void build() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertSame(PROTEOME_ID, proteomeIdComponent.getProteomeId());
        assertSame(COMPONENT, proteomeIdComponent.getComponent());
    }

    @Test
    void testFrom() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId("proteomeId1").component("component").build();
        ProteomeIdComponent proteomeIdComponent1 = ProteomeIdComponentBuilder.from(proteomeIdComponent).build();
        assertEquals(proteomeIdComponent, proteomeIdComponent1);
        ProteomeIdComponent proteomeIdComponent2 = ProteomeIdComponentBuilder.from(proteomeIdComponent).component("component2").build();
        assertEquals("proteomeId1", proteomeIdComponent2.getProteomeId());
        assertEquals("component2", proteomeIdComponent2.getComponent());
    }
}