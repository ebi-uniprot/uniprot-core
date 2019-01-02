package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OrganismNameImplTest {

    @Test
    public void testOnlyScientific() {
        String scientificName = "Homo sapiens";
        OrganismName organism = new OrganismNameImpl(scientificName, null, null);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals("Homo sapiens", organism.toString());
        assertTrue(organism.isValid());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testScientificCommon() {
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        OrganismName organism = new OrganismNameImpl(scientificName, commonName, null);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals("Homo sapiens (Human)", organism.toString());
        assertTrue(organism.isValid());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testNoScientificName() {
        String scientificName = "";
        String commonName = "Human";
        OrganismName organism = new OrganismNameImpl(scientificName, commonName, null);
        assertFalse(organism.isValid());
    }

    @Test
    public void testWithSynonym() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList("Name1", "Name2");
        OrganismName organism = new OrganismNameImpl(scientificName, commonName, synonyms);

        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());

        assertEquals("Homo sapiens (Human) (Name1, Name2)", organism.toString());
        assertTrue(organism.isValid());
        TestHelper.verifyJson(organism);
    }

}
