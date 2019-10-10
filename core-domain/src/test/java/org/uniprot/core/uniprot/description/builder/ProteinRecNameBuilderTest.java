package org.uniprot.core.uniprot.description.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.ProteinRecName;

class ProteinRecNameBuilderTest {

    @Test
    void canAddSingleShortName() {
        ProteinRecName obj =
                new ProteinRecNameBuilder().addShortName(new NameBuilder().build()).build();
        assertNotNull(obj.getShortNames());
        assertFalse(obj.getShortNames().isEmpty());
        assertTrue(obj.hasShortNames());
    }

    @Test
    void nullShortName_willBeIgnore() {
        ProteinRecName obj = new ProteinRecNameBuilder().addShortName(null).build();
        assertNotNull(obj.getShortNames());
        assertTrue(obj.getShortNames().isEmpty());
        assertFalse(obj.hasShortNames());
    }

    @Test
    void canAddSingleEcNumber() {
        ProteinRecName obj =
                new ProteinRecNameBuilder().addEcNumber(new ECBuilder().build()).build();
        assertNotNull(obj.getEcNumbers());
        assertFalse(obj.getEcNumbers().isEmpty());
        assertTrue(obj.hasEcNumbers());
    }

    @Test
    void nullEcNumber_willBeIgnore() {
        ProteinRecName obj = new ProteinRecNameBuilder().addEcNumber(null).build();
        assertNotNull(obj.getEcNumbers());
        assertTrue(obj.getEcNumbers().isEmpty());
        assertFalse(obj.hasEcNumbers());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinRecName obj = new ProteinRecNameBuilder().build();
        ProteinRecNameBuilder builder = new ProteinRecNameBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinRecName obj = new ProteinRecNameBuilder().build();
        ProteinRecName obj2 = new ProteinRecNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
