package org.uniprot.core.cv.pathway.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.pathway.PathwayEntry;

class PathwayEntryBuilderTest {

    @Test
    void canSetId() {
        String id = "id";
        PathwayEntry obj = new PathwayEntryBuilder().id(id).build();

        assertEquals(id, obj.getId());
    }

    @Test
    void canSetAccession() {
        String accession = "accession";
        PathwayEntry obj = new PathwayEntryBuilder().accession(accession).build();

        assertEquals(accession, obj.getAccession());
    }

    @Test
    void canSetDefinition() {
        String definition = "definition";
        PathwayEntry obj = new PathwayEntryBuilder().definition(definition).build();

        assertEquals(definition, obj.getDefinition());
    }

    @Test
    void canSetPathwayClass() {
        String pathwayClass = "pathwayClass";
        PathwayEntry obj = new PathwayEntryBuilder().pathwayClass(pathwayClass).build();

        assertEquals(pathwayClass, obj.getPathwayClass());
    }

    @Test
    void canAddSingleSynonyms() {
        PathwayEntry obj = new PathwayEntryBuilder().synonymsAdd("syn").build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void nullSynonyms_willBeIgnore() {
        PathwayEntry obj = new PathwayEntryBuilder().synonymsAdd(null).build();
        assertNotNull(obj.getSynonyms());
        assertTrue(obj.getSynonyms().isEmpty());
    }

    @Test
    void synonyms_willConvertUnModifiable_toModifiable() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .synonymsSet(Collections.emptyList())
                        .synonymsAdd("syn")
                        .build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(1, obj.getSynonyms().size());
    }

    @Test
    void canAddListSynonyms() {
        PathwayEntry obj =
                new PathwayEntryBuilder().synonymsSet(Arrays.asList("1", "2", "3")).build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertEquals(3, obj.getSynonyms().size());
    }

    @Test
    void canAddSingle_isAParents() {
        PathwayEntry obj = new PathwayEntryBuilder().isAParentsAdd(new PathwayEntryImpl()).build();
        assertNotNull(obj.getIsAParents());
        assertFalse(obj.getIsAParents().isEmpty());
        assertEquals(1, obj.getIsAParents().size());
    }

    @Test
    void null_isAParents_willBeIgnore() {
        PathwayEntry obj = new PathwayEntryBuilder().isAParentsAdd(null).build();
        assertNotNull(obj.getIsAParents());
        assertTrue(obj.getIsAParents().isEmpty());
    }

    @Test
    void isAParents_willConvertUnModifiable_toModifiable() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .isAParentsSet(Collections.emptyList())
                        .isAParentsAdd(new PathwayEntryImpl())
                        .build();
        assertNotNull(obj.getIsAParents());
        assertFalse(obj.getIsAParents().isEmpty());
        assertEquals(1, obj.getIsAParents().size());
    }

    @Test
    void canAddList_isAParents() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .isAParentsSet(Collections.singletonList(new PathwayEntryImpl()))
                        .build();
        assertNotNull(obj.getIsAParents());
        assertFalse(obj.getIsAParents().isEmpty());
        assertEquals(1, obj.getIsAParents().size());
    }

    @Test
    void canAddSingle_partOfParents() {
        PathwayEntry obj =
                new PathwayEntryBuilder().partOfParentsAdd(new PathwayEntryImpl()).build();
        assertNotNull(obj.getPartOfParents());
        assertFalse(obj.getPartOfParents().isEmpty());
        assertEquals(1, obj.getPartOfParents().size());
    }

    @Test
    void null_partOfParents_willBeIgnore() {
        PathwayEntry obj = new PathwayEntryBuilder().partOfParentsAdd(null).build();
        assertNotNull(obj.getPartOfParents());
        assertTrue(obj.getPartOfParents().isEmpty());
    }

    @Test
    void partOfParents_willConvertUnModifiable_toModifiable() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .partOfParentsSet(Collections.emptyList())
                        .partOfParentsAdd(new PathwayEntryImpl())
                        .build();
        assertNotNull(obj.getPartOfParents());
        assertFalse(obj.getPartOfParents().isEmpty());
        assertEquals(1, obj.getPartOfParents().size());
    }

    @Test
    void canAddList_partOfParents() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .partOfParentsSet(Collections.singletonList(new PathwayEntryImpl()))
                        .build();
        assertNotNull(obj.getPartOfParents());
        assertFalse(obj.getPartOfParents().isEmpty());
        assertEquals(1, obj.getPartOfParents().size());
    }

    @Test
    void canAddSingle_diseaseCrossReferences() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .crossReferencesAdd(new DiseaseCrossReferenceBuilder().build())
                        .build();
        assertNotNull(obj.getCrossReferences());
        assertFalse(obj.getCrossReferences().isEmpty());
        assertEquals(1, obj.getCrossReferences().size());
    }

    @Test
    void null_diseaseCrossReferences_willBeIgnore() {
        PathwayEntry obj = new PathwayEntryBuilder().crossReferencesAdd(null).build();
        assertNotNull(obj.getCrossReferences());
        assertTrue(obj.getCrossReferences().isEmpty());
    }

    @Test
    void diseaseCrossReferences_willConvertUnModifiable_toModifiable() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .crossReferencesSet(Collections.emptyList())
                        .crossReferencesAdd(new DiseaseCrossReferenceBuilder().build())
                        .build();
        assertNotNull(obj.getCrossReferences());
        assertFalse(obj.getCrossReferences().isEmpty());
        assertEquals(1, obj.getCrossReferences().size());
    }

    @Test
    void canAddList_diseaseCrossReferences() {
        PathwayEntry obj =
                new PathwayEntryBuilder()
                        .crossReferencesSet(
                                Collections.singletonList(
                                        new DiseaseCrossReferenceBuilder().build()))
                        .build();
        assertNotNull(obj.getCrossReferences());
        assertFalse(obj.getCrossReferences().isEmpty());
        assertEquals(1, obj.getCrossReferences().size());
    }

    @Test
    void canCreateBuilderFromInstance() {
        PathwayEntry obj = new PathwayEntryBuilder().build();
        PathwayEntryBuilder builder = PathwayEntryBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        PathwayEntry obj = new PathwayEntryBuilder().build();
        PathwayEntry obj2 = new PathwayEntryBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
