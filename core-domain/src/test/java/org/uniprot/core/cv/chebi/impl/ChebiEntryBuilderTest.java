package org.uniprot.core.cv.chebi.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.ChebiEntry;

class ChebiEntryBuilderTest {

    @Test
    void canSetId() {
        String id = "id";
        ChebiEntry chebi = new ChebiEntryBuilder().id(id).build();

        assertEquals(id, chebi.getId());
        assertNull(chebi.getInchiKey());
        assertNull(chebi.getName());
    }

    @Test
    void canSetName() {
        String name = "name";
        ChebiEntry chebi = new ChebiEntryBuilder().name(name).build();

        assertEquals(name, chebi.getName());
        assertNull(chebi.getInchiKey());
        assertNull(chebi.getId());
    }

    @Test
    void canSetInchiKey() {
        String inchiKey = "inchiKey";
        ChebiEntry chebi = new ChebiEntryBuilder().inchiKey(inchiKey).build();

        assertEquals(inchiKey, chebi.getInchiKey());
        assertNull(chebi.getName());
        assertNull(chebi.getId());
    }

    @Test
    void canBuildCompleteObject() {
        String inchiKey = "inchiKey";
        String id = "id";
        String name = "name";
        ChebiEntry chebi = new ChebiEntryBuilder().inchiKey(inchiKey).id(id).name(name).build();

        assertEquals(inchiKey, chebi.getInchiKey());
        assertEquals(id, chebi.getId());
        assertEquals(name, chebi.getName());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ChebiEntry obj = new ChebiEntryBuilder().build();
        ChebiEntryBuilder builder = ChebiEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ChebiEntry obj = new ChebiEntryBuilder().build();
        ChebiEntry obj2 = new ChebiEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
