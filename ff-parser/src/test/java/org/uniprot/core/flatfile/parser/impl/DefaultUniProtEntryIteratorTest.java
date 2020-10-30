package org.uniprot.core.flatfile.parser.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created 30/10/2020
 *
 * @author Edd
 */
class DefaultUniProtEntryIteratorTest {
    private DefaultUniProtEntryIterator entryIterator;

    @BeforeEach
    void setUp() {
        entryIterator = new DefaultUniProtEntryIterator(2, 1, 2);
        entryIterator.setIgnoreWrong(true);
    }

    @Test
    void canParseGoodFile() {
        entryIterator.setIgnoreWrong(true);
        entryIterator.setInput("src/test/resources/entryIT/A0A0A0MSM0.dat", null, null, null, null);
        List<String> accessions = new ArrayList<>();
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry = entryIterator.next();
            accessions.add(entry.getPrimaryAccession().getValue());
        }

        assertThat(accessions, contains("A0A0A0MSM0"));
    }

    @Test
    void canParseGoodGZFile() {
        entryIterator.setInput(
                "src/test/resources/entryIT/A8EZU1_D6RDV7.dat.gz", null, null, null, null);
        List<String> accessions = new ArrayList<>();
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry = entryIterator.next();
            accessions.add(entry.getPrimaryAccession().getValue());
        }

        assertThat(accessions, containsInAnyOrder("A8EZU1", "D6RDV7"));
    }

    @Test
    void parseErrorForAllEntriesDoesNotCauseHanging() {
        entryIterator.setInput(
                "src/test/resources/entryIT/ERROR_ERROR.dat", null, null, null, null);
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry =
                    entryIterator
                            .next(); // the entry should be null because there was a parse error
            assertThat(entry, is(nullValue()));
        }
    }

    @Test
    void parseErrorInOnlyOneEntryDoesNotCauseHanging() {
        entryIterator.setInput(
                "src/test/resources/entryIT/A8EZU1_ERROR_D6RDV7.dat", null, null, null, null);
        List<String> accessions = new ArrayList<>();
        while (entryIterator.hasNext()) {
            UniProtKBEntry entry = entryIterator.next();
            accessions.add(entry.getPrimaryAccession().getValue());
        }

        assertThat(accessions, containsInAnyOrder("A8EZU1", "D6RDV7"));
    }
}
