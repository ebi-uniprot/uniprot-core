package org.uniprot.core.genecentric.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;

/**
 * @author lgonzales
 * @since 16/10/2020
 */
class GeneCentricEntryBuilderTest {

    @Test
    void canSetProteomeId() {
        String proteomeId = "Proteome Id VALUE";
        GeneCentricEntry entry = new GeneCentricEntryBuilder().proteomeId(proteomeId).build();
        assertNotNull(entry.getProteomeId());
        assertEquals(proteomeId, entry.getProteomeId());
    }

    @Test
    void canonicalProtein() {
        Protein canonicalProtein = new ProteinBuilder().build();
        GeneCentricEntry entry =
                new GeneCentricEntryBuilder().canonicalProtein(canonicalProtein).build();
        assertEquals(canonicalProtein, entry.getCanonicalProtein());
    }

    @Test
    void canAddRelatedProteins() {
        Protein relatedProtein = new ProteinBuilder().build();
        GeneCentricEntry entry =
                new GeneCentricEntryBuilder().relatedProteinsAdd(relatedProtein).build();
        assertNotNull(entry.getRelatedProteins());
        assertTrue(entry.getRelatedProteins().contains(relatedProtein));
    }

    @Test
    void canSetRelatedProteins() {
        Protein relatedProtein = new ProteinBuilder().build();
        List<Protein> relatedProteins = Collections.singletonList(relatedProtein);
        GeneCentricEntry entry =
                new GeneCentricEntryBuilder().relatedProteinsSet(relatedProteins).build();
        assertEquals(relatedProteins, entry.getRelatedProteins());
    }

    @Test
    void from() {
        Protein canonicalProtein = new ProteinBuilder().id("1").build();
        Protein relatedProtein = new ProteinBuilder().id("2").build();

        GeneCentricEntry entry =
                new GeneCentricEntryBuilder()
                        .proteomeId("proteome Id value")
                        .canonicalProtein(canonicalProtein)
                        .relatedProteinsAdd(relatedProtein)
                        .build();

        GeneCentricEntry entryFrom = GeneCentricEntryBuilder.from(entry).build();
        assertEquals(entry, entryFrom);
        assertTrue(entry.equals(entryFrom) && entryFrom.equals(entry));
        assertEquals(entry.hashCode(), entryFrom.hashCode());
    }
}
