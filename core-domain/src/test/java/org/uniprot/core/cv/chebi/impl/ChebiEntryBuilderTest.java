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
    void canSetMajorMicrospeciesIds() {
        List<ChebiEntry> majorMicrospecie =
                List.of(
                        new ChebiEntryBuilder()
                                .inchiKey("majorK")
                                .id("majorId")
                                .name("majorName")
                                .build());
        ChebiEntry chebi = new ChebiEntryBuilder().majorMicrospeciesSet(majorMicrospecie).build();

        assertEquals(majorMicrospecie, chebi.getMajorMicrospecies());
        assertNull(chebi.getName());
        assertNull(chebi.getId());
    }

    @Test
    void canAddMajorMicrospeciesIds() {
        ChebiEntry majorMicrospecie =
                new ChebiEntryBuilder().inchiKey("majorK").id("majorId").name("majorName").build();
        ChebiEntry chebi = new ChebiEntryBuilder().majorMicrospeciesAdd(majorMicrospecie).build();

        assertNotNull(chebi.getRelatedIds());
        assertTrue(chebi.getMajorMicrospecies().contains(majorMicrospecie));
        assertNull(chebi.getName());
        assertNull(chebi.getId());
    }

    @Test
    void canSetSynonyms() {
        List<String> synonyms = List.of("synonym");
        ChebiEntry chebi = new ChebiEntryBuilder().synonymsSet(synonyms).build();

        assertEquals(synonyms, chebi.getSynonyms());
    }

    @Test
    void canAddSynonyms() {
        String synonym = "synonym";
        ChebiEntry chebi = new ChebiEntryBuilder().synonymsAdd(synonym).build();

        assertNotNull(chebi.getSynonyms());
        assertTrue(chebi.getSynonyms().contains(synonym));
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
        List<String> synonyms = List.of("synonym");
        ChebiEntry chebi =
                new ChebiEntryBuilder()
                        .inchiKey(inchiKey)
                        .id(id)
                        .name(name)
                        .relatedIdsSet(relatedIds)
                        .synonymsSet(synonyms)
                        .build();

        assertEquals(inchiKey, chebi.getInchiKey());
        assertEquals(id, chebi.getId());
        assertEquals(name, chebi.getName());
        assertEquals(relatedIds, chebi.getRelatedIds());
        assertEquals(synonyms, chebi.getSynonyms());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ChebiEntry obj =
                new ChebiEntryBuilder()
                        .inchiKey("inchiKey")
                        .id("id")
                        .name("name")
                        .relatedIdsSet(List.of(new ChebiEntryBuilder().build()))
                        .synonymsSet(List.of("synonym"))
                        .build();
        ChebiEntryBuilder builder = ChebiEntryBuilder.from(obj);
        assertNotNull(builder);
        assertEquals(obj, builder.build());
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ChebiEntry obj = new ChebiEntryBuilder().build();
        ChebiEntry obj2 = new ChebiEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
