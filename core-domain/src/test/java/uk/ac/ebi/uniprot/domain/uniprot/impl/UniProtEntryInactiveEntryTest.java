package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UniProtEntryInactiveEntryTest {

    @Test
    void test() {
        EntryInactiveReason inactiveReason = new EntryInactiveReasonImpl(InactiveReasonType.MERGED, Arrays
                .asList("P12345"));
        UniProtAccession accession = new UniProtAccessionImpl("P2351");
        UniProtId uniProtId = new UniProtIdImpl("P2351_HUMAN");
        UniProtEntry entry = UniProtFactory.INSTANCE.createInactiveEntry(accession
                , uniProtId, inactiveReason);
        assertFalse(entry.isActive());
        assertEquals(accession, entry.getPrimaryAccession());
        assertEquals(uniProtId, entry.getUniProtId());
        assertEquals(inactiveReason, entry.getInactiveReason());
        TestHelper.verifyJson(entry);
    }

}
