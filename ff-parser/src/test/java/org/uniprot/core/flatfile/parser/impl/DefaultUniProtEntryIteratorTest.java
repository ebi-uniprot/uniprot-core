package org.uniprot.core.flatfile.parser.impl;

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
    @Test
    void canParseGoodFile() {
        DefaultUniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(2, 10, 10);
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
        DefaultUniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(2, 10, 10);
        entryIterator.setIgnoreWrong(true);
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
        // TODO: 30/10/2020 problem with iterator. hasNext() returns true, but then next() returns
        // false. Maybe logic of hasNext() needs to change to make sure hasNext() returns true only
        // if next exists

        DefaultUniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(2, 10, 10);
        entryIterator.setIgnoreWrong(true);
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
        DefaultUniProtEntryIterator entryIterator = new DefaultUniProtEntryIterator(2, 10, 10);
        entryIterator.setIgnoreWrong(true);
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
