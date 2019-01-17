package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrganismHostImplTest {

    @Test
    public void testOnlyScientific() {
        String scientificName = "Homo sapiens";
        OrganismHostBuilder builder = new OrganismHostBuilder().scientificName(scientificName);
        OrganismHost organism = new OrganismHostImpl(builder);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testScientificCommon() {
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        OrganismHostBuilder builder = new OrganismHostBuilder()
                .scientificName(scientificName)
                .commonName(commonName);
        OrganismHost organism = new OrganismHostImpl(builder);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        TestHelper.verifyJson(organism);
    }

    @Test
    public void testNoScientificName() {
        String scientificName = "";
        String commonName = "Human";
        OrganismHostBuilder builder = new OrganismHostBuilder()
                .scientificName(scientificName)
                .commonName(commonName);
        OrganismHost organism = new OrganismHostImpl(builder);
        assertEquals(commonName, organism.getCommonName());
    }

    @Test
    public void testWithSynonym() {

        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList("Name1", "Name2");
        OrganismHostBuilder builder = new OrganismHostBuilder()
                .scientificName(scientificName)
                .commonName(commonName)
                .synonyms(synonyms);
        OrganismHost organism = new OrganismHostImpl(builder);

        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(synonyms, organism.getSynonyms());

        TestHelper.verifyJson(organism);
    }

}
