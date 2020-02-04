package org.uniprot.core.uniprot.description.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.ProteinAltName;

class ProteinAltNameBuilderTest {
    @Test
    void canAddSingleSortName() {
        ProteinAltName altName =
                new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder().build()).build();
        assertTrue(altName.hasShortNames());
    }

    @Test
    void nullWillIgnoreWhileAddingSingleSortName() {
        ProteinAltName altName = new ProteinAltNameBuilder().shortNamesAdd(null).build();
        assertFalse(altName.hasShortNames());
    }

    @Test
    void canAddEcNumber() {
        ProteinAltName altName =
                new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder().build()).build();
        assertTrue(altName.hasEcNumbers());
    }

    @Test
    void nullWillIgnoreWhileAddingEcNumber() {
        ProteinAltName altName = new ProteinAltNameBuilder().ecNumbersAdd(null).build();
        assertFalse(altName.hasEcNumbers());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinAltName obj = new ProteinAltNameBuilder().build();
        ProteinAltNameBuilder builder = ProteinAltNameBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinAltName obj = new ProteinAltNameBuilder().build();
        ProteinAltName obj2 = new ProteinAltNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
