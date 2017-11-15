package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrganismImplTest {

    @Test
    public void testOrganismImpl() {
        long taxId =9606;
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        Taxon organism = new TaxonImpl(taxId, scientificName, commonName, Collections.emptyList());
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertTrue(organism.getSynonyms().isEmpty());
        assertEquals("Homo sapiens (Human)", organism.toString());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testOrganismImplNoScientificName() {
        long taxId =9606;
        String scientificName ="";
        String commonName = "Human";
        new TaxonImpl(taxId,scientificName, commonName, Collections.emptyList());      
    }

    public void testOrganismImplWithSynonym() {
        long taxId =9606;
        String scientificName ="Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList(new String[] {"Name1", "Name2"});
        Taxon organism = new TaxonImpl(taxId, scientificName, commonName,synonyms);     
        
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(commonName, organism.getCommonName());
        assertEquals(2, organism.getSynonyms().size());
        assertEquals("Homo sapiens (Human) (Name1, Name2)", organism.toString());
        
    }
    
  

}
