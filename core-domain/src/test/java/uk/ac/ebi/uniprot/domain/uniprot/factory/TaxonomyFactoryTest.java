package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonId;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaxonomyFactoryTest {

    @Test
    public void testCreateTaxonId() {
        TaxonId taxonId = TaxonomyFactory.INSTANCE.createTaxonId(9606);
        assertEquals(9606, taxonId.getTaxonId());
    }

    @Test
    public void testCreateScientificNameOnly() {
        long taxId = 9606;
        String scientificName = "Homo sapiens";
        Taxon organism = TaxonomyFactory.INSTANCE.createTaxon(taxId, scientificName);
        assertEquals(scientificName, organism.getScientificName());
        assertEquals(scientificName, organism.toString());
        assertEquals(taxId, organism.getTaxonId().getTaxonId());
    }

    @Test
    public void testCreateScientificNameCommonName() {
        long taxId = 9606;
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        Taxon organism = TaxonomyFactory.INSTANCE.createTaxon(taxId, scientificName, commonName);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertTrue(organism.getSynonyms().isEmpty());
        assertEquals("Homo sapiens (Human)", organism.toString());
    }

    @Test
    public void testCreateScientificNameCommonNameSynonyms() {
        long taxId = 9606;
        String str = "Homo sapiens (Human) (Name1, Name2)";
        String scientificName = "Homo sapiens";
        String commonName = "Human";
        List<String> synonyms = Arrays.asList(new String[]{"Name1", "Name2"});
        Taxon organism = TaxonomyFactory.INSTANCE.createTaxon(taxId, scientificName, commonName, synonyms);

        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertEquals(2, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testCreateTaxonNode() {
        TaxonNode parent =
                TaxonomyFactory.INSTANCE.createTaxonNode(null, TaxonomyFactory.INSTANCE.createTaxon(9605, "Homo"), TaxonomyRank.GENUS);

        TaxonNode node =
                TaxonomyFactory.INSTANCE.createTaxonNode(parent, TaxonomyFactory.INSTANCE.createTaxon(9606, "Homo sapiens"),
                        TaxonomyRank.SPECIES);
        assertEquals(9606, node.getTaxon().getTaxonId().getTaxonId());
        assertEquals(TaxonomyRank.SPECIES, node.getRank());
        assertEquals(parent, node.getParent());
    }

}
