package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxonNodeImplTest {

    @Test
    public void testTaxonNodeImpl() {

        TaxonNode parent = new TaxonNodeImpl(null, TaxonomyFactory.createTaxon(9605, "Homo"), TaxonomyRank.GENUS);

        TaxonNode node =
                new TaxonNodeImpl(parent, TaxonomyFactory.createTaxon(9606, "Homo sapiens"), TaxonomyRank.SPECIES);
        assertEquals(9606, node.getTaxon().getTaxonId().getTaxonId());
        assertEquals(TaxonomyRank.SPECIES, node.getRank());
        assertEquals(parent, node.getParent());
    }

    @Test
    public void testGetTaxonLineage() {

        TaxonNode haplorrhini =
                new TaxonNodeImpl(null, TaxonomyFactory.createTaxon(376913, "Haplorrhini"), TaxonomyRank.SUBORDER);

        TaxonNode catarrhini =
                new TaxonNodeImpl(haplorrhini, TaxonomyFactory.createTaxon(9526, "Catarrhini"), TaxonomyRank.PARVORDER);

        TaxonNode homininae =
                new TaxonNodeImpl(catarrhini, TaxonomyFactory.createTaxon(9604, "Hominidae"), TaxonomyRank.FAMILY);

        TaxonNode homo = new TaxonNodeImpl(homininae, TaxonomyFactory.createTaxon(9605, "Homo"), TaxonomyRank.GENUS);

        TaxonNode node =
                new TaxonNodeImpl(homo, TaxonomyFactory.createTaxon(9606, "Homo sapiens"), TaxonomyRank.SPECIES);
        assertEquals(9606, node.getTaxon().getTaxonId().getTaxonId());
        assertEquals(TaxonomyRank.SPECIES, node.getRank());
        assertEquals(homo, node.getParent());
        List<Taxon> lineage = node.getTaxonLineage();
        List<String> lineageString = 
                lineage.stream().map(Taxon::getScientificName)
                .collect(Collectors.toList());
        List<String> expected = Arrays.asList(new String[]{"Haplorrhini", "Catarrhini", "Hominidae", "Homo"});
        assertEquals(expected, lineageString);
        lineage = homo.getTaxonLineage();
      lineageString = 
                lineage.stream().map(Taxon::getScientificName)
                .collect(Collectors.toList());
        expected = Arrays.asList(new String[]{"Haplorrhini", "Catarrhini", "Hominidae"});

        assertEquals(expected, lineageString);
    }

}
