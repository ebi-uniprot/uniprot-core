package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.EntryInactiveReason;
import org.uniprot.core.uniprot.InactiveReasonType;
import org.uniprot.core.uniprot.builder.EntryInactiveReasonBuilder;

class EntryInactiveReasonImplTest {

    @Test
    void testEntryInactiveReasonImplDefMerge() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.MERGED)
                        .mergeDemergeToSet(Collections.singletonList("P12345"))
                        .build();
        assertEquals(InactiveReasonType.MERGED, reason.getInactiveReasonType());
        assertEquals(Collections.singletonList("P12345"), reason.getMergeDemergeTo());
    }

    @Test
    void testEntryInactiveReasonImplMerge() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.DEMERGED)
                        .mergeDemergeToSet(Arrays.asList("P12345", "P12347"))
                        .build();
        assertEquals(InactiveReasonType.DEMERGED, reason.getInactiveReasonType());
        assertEquals(Arrays.asList("P12345", "P12347"), reason.getMergeDemergeTo());
    }

    @Test
    void testEntryInactiveReasonImplDelete() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.DELETED)
                        .mergeDemergeToSet(null)
                        .build();
        assertEquals(InactiveReasonType.DELETED, reason.getInactiveReasonType());
        assertTrue(reason.getMergeDemergeTo().isEmpty());
    }

    @Test
    void defaultConstructor_MergeList_shouldEmpty() {
        EntryInactiveReason reason = new EntryInactiveReasonImpl();
        assertNotNull(reason.getMergeDemergeTo());
        assertTrue(reason.getMergeDemergeTo().isEmpty());
    }

    @Test
    void inactiveReasonTypeEqualTest() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().build();
        EntryInactiveReason reason2 =
                new EntryInactiveReasonBuilder().type(InactiveReasonType.MERGED).build();

        assertFalse(reason.equals(reason2) || reason2.equals(reason));
        assertNotEquals(reason.hashCode(), reason2.hashCode());
    }
}
