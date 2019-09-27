package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Journal;

class JournalTest {
    @Test
    void testJournalImpl() {
        Journal journal = new JournalArticleBuilder().journalName("Nature").build().getJournal();
        assertEquals("Nature", journal.getName());
    }
}
