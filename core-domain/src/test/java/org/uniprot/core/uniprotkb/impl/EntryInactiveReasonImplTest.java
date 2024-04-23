package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.DeletedReason;
import org.uniprot.core.uniprotkb.EntryInactiveReason;
import org.uniprot.core.uniprotkb.InactiveReasonType;

class EntryInactiveReasonImplTest {

    @Test
    void testEntryInactiveReasonImplDefMerge() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.MERGED)
                        .mergeDemergeTosSet(Collections.singletonList("P12345"))
                        .build();
        assertEquals(InactiveReasonType.MERGED, reason.getInactiveReasonType());
        assertEquals(Collections.singletonList("P12345"), reason.getMergeDemergeTos());
    }

    @Test
    void testEntryInactiveReasonImplMerge() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.DEMERGED)
                        .mergeDemergeTosSet(Arrays.asList("P12345", "P12347"))
                        .build();
        assertEquals(InactiveReasonType.DEMERGED, reason.getInactiveReasonType());
        assertEquals(Arrays.asList("P12345", "P12347"), reason.getMergeDemergeTos());
    }

    @Test
    void testEntryInactiveReasonImplDelete() {
        DeletedReason deletedReason = DeletedReason.PROTEOME_REDUNDANCY;
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.DELETED)
                        .deletedReason(deletedReason)
                        .mergeDemergeTosSet(null)
                        .build();
        assertEquals(InactiveReasonType.DELETED, reason.getInactiveReasonType());
        assertEquals(deletedReason, reason.getDeletedReason());
        assertTrue(reason.getMergeDemergeTos().isEmpty());
    }

    @Test
    void defaultConstructor_MergeList_shouldEmpty() {
        EntryInactiveReason reason = new EntryInactiveReasonImpl();
        assertNotNull(reason.getMergeDemergeTos());
        assertTrue(reason.getMergeDemergeTos().isEmpty());
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
