package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcId;

class UniParcIdImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniParcId obj = new UniParcIdImpl();
        assertNotNull(obj);
    }
}
