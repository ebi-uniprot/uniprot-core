package org.uniprot.core.uniprotkb;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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