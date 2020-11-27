package org.uniprot.cv.go.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created 26/11/2020
 *
 * @author Edd
 */
class GOTermFileReaderTest {
    @Test
    void parsesCorrectly() {
        GOTermFileReader reader = new GOTermFileReader();
        List<GeneOntologyEntry> entries = reader.parse("src/test/resources/go");

        assertThat(entries, hasSize(12));
        assertThat(
                entries,
                hasItem(
                        new GeneOntologyEntryBuilder()
                                .id("GO:0000001")
                                .name("mitochondrion inheritance")
                                .build()));

        assertThat(
                entries,
                hasItem(
                        new GeneOntologyEntryBuilder()
                                .id("GO:0007005")
                                .name("mitochondrion organization")
                                .build()));
    }
}