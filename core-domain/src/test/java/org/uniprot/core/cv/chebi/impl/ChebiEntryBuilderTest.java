package org.uniprot.core.cv.chebi.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
    void canSetRelatedIds() {
        List<ChebiEntry> relatedIds =
                List.of(
                        new ChebiEntryBuilder()
                                .inchiKey("relK")
                                .id("relId")
                                .name("relName")
                                .build());
        ChebiEntry chebi = new ChebiEntryBuilder().relatedIdsSet(relatedIds).build();

        assertEquals(relatedIds, chebi.getRelatedIds());
        assertNull(chebi.getName());
        assertNull(chebi.getId());
    }

    @Test
    void canAddRelatedIds() {
        ChebiEntry related =
                new ChebiEntryBuilder().inchiKey("relK").id("relId").name("relName").build();
        ChebiEntry chebi = new ChebiEntryBuilder().relatedIdsAdd(related).build();

        assertNotNull(chebi.getRelatedIds());
        assertTrue(chebi.getRelatedIds().contains(related));
        assertNull(chebi.getName());
        assertNull(chebi.getId());
    }

    @Test
    void canBuildCompleteObject() {
        List<ChebiEntry> relatedIds =
                List.of(
                        new ChebiEntryBuilder()
                                .inchiKey("relK")
                                .id("relId")
                                .name("relName")
                                .build());
        String inchiKey = "inchiKey";
        String id = "id";
        String name = "name";
        ChebiEntry chebi =
                new ChebiEntryBuilder()
                        .inchiKey(inchiKey)
                        .id(id)
                        .name(name)
                        .relatedIdsSet(relatedIds)
                        .build();

        assertEquals(inchiKey, chebi.getInchiKey());
        assertEquals(id, chebi.getId());
        assertEquals(name, chebi.getName());
        assertEquals(relatedIds, chebi.getRelatedIds());
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
