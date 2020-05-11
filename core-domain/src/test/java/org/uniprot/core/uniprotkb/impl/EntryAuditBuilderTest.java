package org.uniprot.core.uniprotkb.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.EntryAudit;

import java.time.LocalDate;

class EntryAuditBuilderTest {

    @Test
    void canBuildWithFirstPublicDate() {
        LocalDate fp = LocalDate.now();
        EntryAudit entryAudit = new EntryAuditBuilder().firstPublic(fp).build();
        assertSame(fp, entryAudit.getFirstPublicDate());
    }

    @Test
    void canBuildWithLastAnnotationUpdate() {
        LocalDate lau = LocalDate.now();
        EntryAudit entryAudit = new EntryAuditBuilder().lastAnnotationUpdate(lau).build();
        assertSame(lau, entryAudit.getLastAnnotationUpdateDate());
    }

    @Test
    void canBuildWithLastSequenceUpdate() {
        LocalDate lsu = LocalDate.now();
        EntryAudit entryAudit = new EntryAuditBuilder().lastSequenceUpdate(lsu).build();
        assertSame(lsu, entryAudit.getLastSequenceUpdateDate());
    }

    @Test
    void canBuildWithEntryVersion() {
        EntryAudit entryAudit = new EntryAuditBuilder().entryVersion(101).build();
        assertEquals(101, entryAudit.getEntryVersion());
    }

    @Test
    void canBuildWithSequenceVersion() {
        EntryAudit entryAudit = new EntryAuditBuilder().sequenceVersion(001).build();
        assertEquals(1, entryAudit.getSequenceVersion());
    }

    @Test
    void canCreateBuilderFromInstance() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        EntryAuditBuilder builder = EntryAuditBuilder.from(entryAudit);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        EntryAudit entryAudit2 = new EntryAuditBuilder().build();
        assertTrue(entryAudit.equals(entryAudit2) && entryAudit2.equals(entryAudit));
        assertEquals(entryAudit.hashCode(), entryAudit2.hashCode());
    }
}
