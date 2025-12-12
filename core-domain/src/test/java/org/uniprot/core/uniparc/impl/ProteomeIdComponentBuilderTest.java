package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.ProteomeIdComponent;

import static org.junit.jupiter.api.Assertions.*;

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
}