package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class EntryAuditImplTest {

    @Test
    public void testEntryAuditImpl() {
        LocalDate firstPublicDate = LocalDate.of(2015, Month.AUGUST, 2);
        LocalDate lastAnnotationUpdateDate = LocalDate.of(2016, Month.APRIL, 24);
        LocalDate lastSequenceUpdateDate = LocalDate.of(2017, Month.JANUARY, 21);
        int entryVersion = 20;
        int sequenceVersion = 5;

        EntryAudit entryAudit = new EntryAuditImpl(firstPublicDate, lastAnnotationUpdateDate,
                                                   lastSequenceUpdateDate, entryVersion, sequenceVersion);
        assertEquals(firstPublicDate, entryAudit.getFirstPublicDate());
        assertEquals(lastAnnotationUpdateDate, entryAudit.getLastAnnotationUpdateDate());
        assertEquals(lastSequenceUpdateDate, entryAudit.getLastSequenceUpdateDate());
        assertEquals(entryVersion, entryAudit.getEntryVersion());
        assertEquals(sequenceVersion, entryAudit.getSequenceVersion());
        TestHelper.verifyJson(entryAudit);
    }

}
