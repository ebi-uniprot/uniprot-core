package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcEntryLight;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UniParcEntryLightImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcEntryLight lightObject = new UniParcEntryLightImpl();
        assertNotNull(lightObject);
    }
}
