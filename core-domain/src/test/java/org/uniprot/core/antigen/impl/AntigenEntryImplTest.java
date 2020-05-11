package org.uniprot.core.antigen.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.antigen.AntigenEntry;

/**
 * @author lgonzales
 * @since 11/05/2020
 */
public class AntigenEntryImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        AntigenEntry obj = new AntigenEntryImpl();
        assertNotNull(obj);
    }
}
