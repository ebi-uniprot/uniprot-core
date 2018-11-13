package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNodeImpl;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxonNodeImplTest {

    @Test
    public void testTaxonNodeImpl() {

        TaxonNode parent = new TaxonNodeImpl(null, TaxonomyFactory.INSTANCE.createTaxon(
        		TaxonomyFactory.INSTANCE.createTaxonName("Homo"), 9605), TaxonomyRank.GENUS);

        TaxonNode node =
                new TaxonNodeImpl(parent, 
                		 TaxonomyFactory.INSTANCE.createTaxon( 
                	        		TaxonomyFactory.INSTANCE.createTaxonName("Homo sapiens"), 9606), TaxonomyRank.SPECIES);
        assertEquals(9606, node.getTaxon().getTaxonId());
        assertEquals(TaxonomyRank.SPECIES, node.getRank());
        assertEquals(parent, node.getParent());
        TestHelper.verifyJson(node);
    }

    @Test
    public void testGetTaxonLineage() {

        TaxonNode haplorrhini =
                new TaxonNodeImpl(null,
                		TaxonomyFactory.INSTANCE.createTaxon(
                        		TaxonomyFactory.INSTANCE.createTaxonName("Haplorrhini"), 376913), TaxonomyRank.SUBORDER);

        TaxonNode catarrhini =
                new TaxonNodeImpl(haplorrhini,
                		TaxonomyFactory.INSTANCE.createTaxon(
                        		TaxonomyFactory.INSTANCE.createTaxonName("Catarrhini"), 9526), TaxonomyRank.PARVORDER);

        TaxonNode homininae =
                new TaxonNodeImpl(catarrhini,TaxonomyFactory.INSTANCE.createTaxon( 
                		"Hominidae", 9604), TaxonomyRank.FAMILY);
                		
        TaxonNode homo =
                new TaxonNodeImpl(homininae,TaxonomyFactory.INSTANCE.createTaxon(
                		"Homo", 9605), TaxonomyRank.GENUS);

      

        TaxonNode node =
                new TaxonNodeImpl(homo,TaxonomyFactory.INSTANCE.createTaxon( 
                		"Homo sapiens", 9606), TaxonomyRank.SPECIES);
        assertEquals(9606, node.getTaxon().getTaxonId());
        assertEquals(TaxonomyRank.SPECIES, node.getRank());
        assertEquals(homo, node.getParent());
        List<Taxon> lineage = node.getTaxonLineage();
        List<String> lineageString = 
                lineage.stream().map(Taxon::getName)
                .map(TaxonName::getScientificName)
                .collect(Collectors.toList());
        List<String> expected = Arrays.asList(new String[]{"Haplorrhini", "Catarrhini", "Hominidae", "Homo"});
        assertEquals(expected, lineageString);
        lineage = homo.getTaxonLineage();
      lineageString = 
                lineage.stream().map(Taxon::getName)
                .map(TaxonName::getScientificName)
                .collect(Collectors.toList());
        expected = Arrays.asList(new String[]{"Haplorrhini", "Catarrhini", "Hominidae"});

        assertEquals(expected, lineageString);
        
        TestHelper.verifyJson(node);
    }

}
