package org.uniprot.core.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class DeletedReasonTest {

    @Test
    void canGetIds() {
        assertEquals(List.of(2), DeletedReason.SWISSPROT_DELETION.getIds());
    }

    @Test
    void canGetName() {
        assertEquals("Over-represented sequence", DeletedReason.OVERREPRESENTED.getName());
    }

    @Test
    void canGetFromIdWithValidSingleId() {
        assertEquals(DeletedReason.PROTEOME_REDUNDANCY, DeletedReason.fromId("13"));
        assertEquals(DeletedReason.REFERENCE_PROTEOME_EXCLUSION.getName(), DeletedReason.fromId("13").getName());
    }

    @Test
    void canGetFromIdWithValidMultipleId() {
        assertEquals(DeletedReason.UNKNOWN, DeletedReason.fromId("10"));
    }

    @Test
    void canGetFromIdWithInValidIdReturnsUndefined() {
        assertEquals(DeletedReason.UNDEFINED, DeletedReason.fromId("99"));
    }

    @Test
    void canGetReferenceProteomeExclusion() {
        assertEquals(DeletedReason.REFERENCE_PROTEOME_EXCLUSION, DeletedReason.fromId("16"));
    }
}
