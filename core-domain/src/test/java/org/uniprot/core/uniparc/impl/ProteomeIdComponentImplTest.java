package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.ProteomeIdComponent;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeIdComponentImplTest {

    public static final String PROTEOME_ID = "upi";
    public static final String COMPONENT = "component";

    @Test
    void getProteomeId() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertSame(PROTEOME_ID, proteomeIdComponent.getProteomeId());
    }

    @Test
    void getComponent() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertSame(COMPONENT, proteomeIdComponent.getComponent());
    }

    @Test
    void testEquals() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertEquals(proteomeIdComponent0, proteomeIdComponent1);
    }

    @Test
    void testEqualsFalse() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component("diff").build();
        assertNotEquals(proteomeIdComponent0, proteomeIdComponent1);
    }

    @Test
    void testHashCode() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentBuilder().proteomeId(PROTEOME_ID).component(COMPONENT).build();
        assertEquals(proteomeIdComponent0, proteomeIdComponent1);
        assertEquals(proteomeIdComponent0.hashCode(), proteomeIdComponent1.hashCode());
    }
}