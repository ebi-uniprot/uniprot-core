package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.DeletedReason;
import org.uniprot.core.uniprotkb.EntryInactiveReason;

class EntryInactiveReasonBuilderTest {

    @Test
    void canBuildWithMergeDemergeTo() {
        EntryInactiveReason reason =
                new EntryInactiveReasonBuilder().mergeDemergeTosAdd("merge").build();
        assertNotNull(reason.getMergeDemergeTos());
        assertFalse(reason.getMergeDemergeTos().isEmpty());
        assertEquals("merge", reason.getMergeDemergeTos().get(0));
    }

    @Test
    void canBuildWithDeletedReason() {
        DeletedReason deletedReason = DeletedReason.SWISSPROT_DELETION;
        EntryInactiveReason reason = new EntryInactiveReasonBuilder()
                .deletedReason(deletedReason)
                .build();
        assertNotNull(reason.getDeletedReason());
        assertEquals(deletedReason, reason.getDeletedReason());
    }

    @Test
    void canCreateBuilderFromInstance() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().build();
        EntryInactiveReasonBuilder builder = EntryInactiveReasonBuilder.from(reason);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        EntryInactiveReason reason = new EntryInactiveReasonBuilder().build();
        EntryInactiveReason reason2 = new EntryInactiveReasonBuilder().build();
        assertTrue(reason.equals(reason2) && reason2.equals(reason));
        assertEquals(reason.hashCode(), reason2.hashCode());
    }
}
