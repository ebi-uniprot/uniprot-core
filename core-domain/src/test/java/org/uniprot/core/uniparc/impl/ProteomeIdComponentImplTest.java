package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.ProteomeIdComponent;

import static org.junit.jupiter.api.Assertions.*;

class ProteomeIdComponentImplTest {

    public static final String PROTEOME_ID = "upi";
    public static final String COMPONENT = "component";

    @Test
    void needDefaultConstructor() {
        ProteomeIdComponentImpl proteomeIdComponent = new ProteomeIdComponentImpl();
        assertNotNull(proteomeIdComponent);
    }

    @Test
    void getProteomeId() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        assertSame(PROTEOME_ID, proteomeIdComponent.getProteomeId());
    }

    @Test
    void getComponent() {
        ProteomeIdComponent proteomeIdComponent = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        assertSame(COMPONENT, proteomeIdComponent.getComponent());
    }

    @Test
    void testEquals() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        assertEquals(proteomeIdComponent0, proteomeIdComponent1);
    }

    @Test
    void testEqualsFalse() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentImpl(PROTEOME_ID, "diff");
        assertNotEquals(proteomeIdComponent0, proteomeIdComponent1);
    }

    @Test
    void testHashCode() {
        ProteomeIdComponent proteomeIdComponent0 = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        ProteomeIdComponent proteomeIdComponent1 = new ProteomeIdComponentImpl(PROTEOME_ID, COMPONENT);
        assertEquals(proteomeIdComponent0, proteomeIdComponent1);
        assertEquals(proteomeIdComponent0.hashCode(), proteomeIdComponent1.hashCode());
    }
}