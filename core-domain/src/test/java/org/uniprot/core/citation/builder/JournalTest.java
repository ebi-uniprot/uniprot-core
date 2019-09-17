package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;

import org.uniprot.core.citation.Journal;
import org.uniprot.core.citation.builder.JournalArticleBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JournalTest {
    @Test
    void testJournalImpl() {
        Journal journal = new JournalArticleBuilder().journalName("Nature").build().getJournal();
        assertEquals("Nature", journal.getName());
    }
}
