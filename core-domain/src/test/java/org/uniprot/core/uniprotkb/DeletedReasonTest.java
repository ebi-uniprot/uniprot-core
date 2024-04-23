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
        assertEquals("Overrepresented sequence", DeletedReason.OVERREPRESENTED.getName());
    }

    @Test
    void canGetFromIdWithValidSingleId() {
        assertEquals(DeletedReason.PROTEOME_REDUNDANCY, DeletedReason.fromId("13"));
    }

    @Test
    void canGetFromIdWithValidMultipleId() {
        assertEquals(DeletedReason.SOURCE_DELETION, DeletedReason.fromId("8"));
    }

    @Test
    void canGetFromIdWithInValidIdThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DeletedReason.fromId("99"));
        assertEquals("The DeletedReason id '99' doesn't exist.", exception.getMessage());
    }
}