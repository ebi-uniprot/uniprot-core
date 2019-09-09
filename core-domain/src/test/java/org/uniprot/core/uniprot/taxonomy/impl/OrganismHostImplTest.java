package org.uniprot.core.uniprot.taxonomy.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganismHostImplTest {

    @Test
    public void testOnlyScientific() {
        String scientificName = "Homo sapiens";
        OrganismHost organism = new OrganismHostImpl(0, scientificName, null, null);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testScientificCommon() {
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        OrganismHost organism = new OrganismHostImpl(0, scientificName, commonName, null);;
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testNoScientificName() {
        String commonName = "Human";
        OrganismHost organism = new OrganismHostImpl(0, null, commonName, null);;
        assertEquals(commonName, organism.getCommonName());
    }

    @Test
    public void testWithSynonym() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList("Name1", "Name2");
        OrganismHost organism = new OrganismHostImpl(0, scientificName, commonName, synonyms);;

        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(synonyms, organism.getSynonyms());

        TestHelper.verifyJson(organism);
    }
}
