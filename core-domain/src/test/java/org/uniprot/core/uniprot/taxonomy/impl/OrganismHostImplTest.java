package org.uniprot.core.uniprot.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;

class OrganismHostImplTest {

    @Test
    void testOnlyScientific() {
        String scientificName = "Homo sapiens";
        OrganismHost organism = new OrganismHostImpl(0, scientificName, null, null);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
    }

    @Test
    void testScientificCommon() {
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        OrganismHost organism = new OrganismHostImpl(0, scientificName, commonName, null);
        ;
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
    }

    @Test
    void testNoScientificName() {
        String commonName = "Human";
        OrganismHost organism = new OrganismHostImpl(0, null, commonName, null);
        ;
        assertEquals(commonName, organism.getCommonName());
    }

    @Test
    void testWithSynonym() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList("Name1", "Name2");
        OrganismHost organism = new OrganismHostImpl(0, scientificName, commonName, synonyms);
        ;

        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(synonyms, organism.getSynonyms());
    }
}
