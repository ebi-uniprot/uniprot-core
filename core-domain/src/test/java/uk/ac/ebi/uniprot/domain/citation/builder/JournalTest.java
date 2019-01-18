package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.Journal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JournalTest {
    @Test
    void testJournalImpl() {
        Journal journal = new JournalArticleBuilder().journalName("Nature").build().getJournal();
        assertEquals("Nature", journal.getName());
        TestHelper.verifyJson(journal);
    }
}
