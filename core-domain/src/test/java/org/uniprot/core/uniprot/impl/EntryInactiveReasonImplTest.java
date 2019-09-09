package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;

import org.uniprot.core.uniprot.EntryInactiveReason;
import org.uniprot.core.uniprot.InactiveReasonType;
import org.uniprot.core.uniprot.builder.EntryInactiveReasonBuilder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntryInactiveReasonImplTest {

    @Test
    void testEntryInactiveReasonImplDefMerge() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().type(InactiveReasonType.MERGED).mergeDemergeTo(Arrays.asList("P12345")).build();
        assertEquals(InactiveReasonType.MERGED, reason.getInactiveReasonType());
        assertEquals(Arrays.asList("P12345"), reason.getMergeDemergeTo());
    }

    @Test
    void testEntryInactiveReasonImplMerge() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().type(InactiveReasonType.DEMERGED).mergeDemergeTo(Arrays
                .asList("P12345", "P12347")).build();
        assertEquals(InactiveReasonType.DEMERGED, reason.getInactiveReasonType());
        assertEquals(Arrays.asList("P12345", "P12347"), reason.getMergeDemergeTo());
    }

    @Test
    void testEntryInactiveReasonImplDelete() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().type(InactiveReasonType.DELETED).mergeDemergeTo(null).build();
        assertEquals(InactiveReasonType.DELETED, reason.getInactiveReasonType());
        assertTrue(reason.getMergeDemergeTo().isEmpty());
    }
}
