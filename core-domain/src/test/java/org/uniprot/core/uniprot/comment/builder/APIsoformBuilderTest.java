package org.uniprot.core.uniprot.comment.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.APIsoform;

class APIsoformBuilderTest {

    @Test
    void canAddSingleId() {
        APIsoform obj = new APIsoformBuilder().isoformIdsAdd("id").build();
        assertNotNull(obj.getIsoformIds());
        assertFalse(obj.getIsoformIds().isEmpty());
        assertTrue(obj.hasIsoformIds());
    }

    @Test
    void nullId_willBeIgnore() {
        APIsoform obj = new APIsoformBuilder().isoformIdsAdd(null).build();
        assertNotNull(obj.getIsoformIds());
        assertTrue(obj.getIsoformIds().isEmpty());
        assertFalse(obj.hasIsoformIds());
    }

    @Test
    void canAddSingleSequenceId() {
        APIsoform obj = new APIsoformBuilder().sequenceIdsAdd("id").build();
        assertNotNull(obj.getSequenceIds());
        assertFalse(obj.getSequenceIds().isEmpty());
        assertTrue(obj.hasSequenceIds());
    }

    @Test
    void nullSequenceId_willBeIgnore() {
        APIsoform obj = new APIsoformBuilder().sequenceIdsAdd(null).build();
        assertNotNull(obj.getSequenceIds());
        assertTrue(obj.getSequenceIds().isEmpty());
        assertFalse(obj.hasSequenceIds());
    }

    @Test
    void canAddSingleSynonym() {
        APIsoform obj =
                new APIsoformBuilder().synonymsAdd(new IsoformNameBuilder().build()).build();
        assertNotNull(obj.getSynonyms());
        assertFalse(obj.getSynonyms().isEmpty());
        assertTrue(obj.hasSynonyms());
    }

    @Test
    void nullSynonym_willBeIgnore() {
        APIsoform obj = new APIsoformBuilder().synonymsAdd(null).build();
        assertNotNull(obj.getSynonyms());
        assertTrue(obj.getSynonyms().isEmpty());
        assertFalse(obj.hasSynonyms());
    }

    @Test
    void canCreateBuilderFromInstance() {
        APIsoform obj = new APIsoformBuilder().build();
        APIsoformBuilder builder = APIsoformBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        APIsoform obj = new APIsoformBuilder().build();
        APIsoform obj2 = new APIsoformBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
