package org.uniprot.core.uniprot.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.EntryAudit;
import org.uniprot.core.uniprot.builder.EntryAuditBuilder;

class EntryAuditImplTest {

    private LocalDate firstPublicDate = LocalDate.of(2015, Month.AUGUST, 2);
    private LocalDate lastAnnotationUpdateDate = LocalDate.of(2016, Month.APRIL, 24);
    private LocalDate lastSequenceUpdateDate = LocalDate.of(2017, Month.JANUARY, 21);
    private int entryVersion = 20;
    private int sequenceVersion = 5;

    private EntryAudit entryAudit =
            new EntryAuditImpl(
                    firstPublicDate,
                    lastAnnotationUpdateDate,
                    lastSequenceUpdateDate,
                    entryVersion,
                    sequenceVersion);

    @Test
    void testEntryAuditImpl() {
        assertEquals(firstPublicDate, entryAudit.getFirstPublicDate());
        assertEquals(lastAnnotationUpdateDate, entryAudit.getLastAnnotationUpdateDate());
        assertEquals(lastSequenceUpdateDate, entryAudit.getLastSequenceUpdateDate());
        assertEquals(entryVersion, entryAudit.getEntryVersion());
        assertEquals(sequenceVersion, entryAudit.getSequenceVersion());
    }

    @Test
    void equalConsiderAnnotationDate() {
        EntryAudit entryAudit2 =
                new EntryAuditBuilder()
                        .from(entryAudit)
                        .lastAnnotationUpdate(LocalDate.now())
                        .build();
        assertFalse(entryAudit.equals(entryAudit2) && entryAudit2.equals(entryAudit));
        assertNotEquals(entryAudit.hashCode(), entryAudit2.hashCode());
    }

    @Test
    void equalConsiderSequenceDate() {
        EntryAudit entryAudit2 =
                new EntryAuditBuilder()
                        .from(entryAudit)
                        .lastSequenceUpdate(LocalDate.now())
                        .build();
        assertFalse(entryAudit.equals(entryAudit2) && entryAudit2.equals(entryAudit));
        assertNotEquals(entryAudit.hashCode(), entryAudit2.hashCode());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        EntryAudit entryAudit = new EntryAuditImpl();
        assertNotNull(entryAudit);
    }
}
