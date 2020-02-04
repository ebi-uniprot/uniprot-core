package org.uniprot.core.uniprot.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.Organism;

class OrganismImplTest {

    @Test
    void testOrganismImpl() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> lineages = Collections.singletonList("lineage");
        List<String> synonyms = Collections.singletonList("synonym");

        Organism organism =
                new OrganismImpl(lineages, 9606L, null, scientificName, commonName, synonyms);

        assertEquals(9606L, organism.getTaxonId());
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(lineages, organism.getLineages());
        assertEquals(synonyms, organism.getSynonyms());
    }

    @Test
    void defaultConstructor_defaultValues() {
        Organism organism = new OrganismImpl();

        assertEquals(0, organism.getTaxonId());
        assertEquals("", organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertTrue(organism.getLineages().isEmpty());
        assertTrue(organism.getSynonyms().isEmpty());
    }

    @Test
    void toString_defaultObject() {
        Organism organism = new OrganismImpl();
        assertEquals("", organism.toString());
    }

    @Test
    void taxonIdAndLineagesWillPartOFToString() {
        Organism organism =
                new OrganismImpl(Collections.singletonList("abc"), 9606L, null, null, null, null);
        assertEquals("9606  (abc)", organism.toString());
    }
}
