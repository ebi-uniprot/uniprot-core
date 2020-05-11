package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.ProteinAltName;

import java.util.Collections;

class ProteinAltNameImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinAltName obj = new ProteinAltNameImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinAltName impl =
                new ProteinAltNameImpl(
                        new NameBuilder().build(),
                        Collections.emptyList(),
                        Collections.emptyList());
        ProteinAltName obj = ProteinAltNameBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    @Test
    void canTestNameValidity() {
        ProteinAltName altName =
                new ProteinAltNameBuilder()
                        .fullName(new NameBuilder().value("name").build())
                        .build();
        assertTrue(altName.hasFullName());
        assertTrue(altName.isValid());
    }
}
