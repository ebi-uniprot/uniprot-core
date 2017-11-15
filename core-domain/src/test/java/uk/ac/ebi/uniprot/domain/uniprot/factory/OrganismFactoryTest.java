package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrganismFactoryTest {

    @Test
    public void testParseScientificNameOnly() {
        long taxId =9606;
       String str ="Homo sapiens";
       Organism organism = OrganismFactory.parse(taxId, str);
       assertEquals(str, organism.getScientificName());
       assertEquals(str, organism.toString());    
    }

    @Test
    public void testParseScientificNameCommonName() {
        long taxId =9606;
        String str ="Homo sapiens (Human)";
        Organism organism = OrganismFactory.parse(taxId, str);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertTrue(organism.getSynonyms().isEmpty());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testParseScientificNameCommonNameSynonyms() {
        long taxId =9606;
        String str ="Homo sapiens (Human) (Name1, Name2)";
        Organism organism = OrganismFactory.parse(taxId, str);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertEquals(2, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
        assertEquals(taxId, organism.getTaxonId().getTaxonId());
    }

    @Test
    public void testCreateScientificNameOnly() {
        long taxId =9606;
       String scientificName ="Homo sapiens";
       Organism organism = OrganismFactory.createOrganism(taxId, scientificName);
       assertEquals(scientificName, organism.getScientificName());
       assertEquals(scientificName, organism.toString());    
       assertEquals(taxId, organism.getTaxonId().getTaxonId());
    }

    @Test
    public void testCreateScientificNameCommonName() {
        long taxId =9606;
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        Organism organism =  OrganismFactory.createOrganism(taxId, scientificName, commonName);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertTrue(organism.getSynonyms().isEmpty());
        assertEquals("Homo sapiens (Human)", organism.toString());
    }

    @Test
    public void testCreateScientificNameCommonNameSynonyms() {
        long taxId =9606;
        String str ="Homo sapiens (Human) (Name1, Name2)";
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        List<String> synonyms =Arrays.asList(new String[]{"Name1", "Name2"});
        Organism organism =  OrganismFactory.createOrganism(taxId, scientificName, commonName, synonyms);

        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertEquals(2, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
    }
    
}
