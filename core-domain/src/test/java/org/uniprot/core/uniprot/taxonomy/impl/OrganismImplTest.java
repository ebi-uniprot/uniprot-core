package org.uniprot.core.uniprot.taxonomy.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganismImplTest {

    @Test
    void testOrganismImpl() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> lineages = Collections.singletonList("lineage");
        List<String> synonyms = Collections.singletonList("synonym");

        Organism organism = new OrganismImpl(lineages, 9606L, null, scientificName, commonName, synonyms);

        assertEquals(9606L, organism.getTaxonId());
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(lineages, organism.getLineage());
        assertEquals(synonyms, organism.getSynonyms());
        TestHelper.verifyJson(organism);
    }

}
