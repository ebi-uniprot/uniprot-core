package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonId;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonNodeImpl;

import java.util.Collections;
import java.util.List;

public class TaxonomyFactory {
    public static TaxonId createTaxonId(long taxId){
        return new TaxonIdImpl(taxId);
    }
    public static Taxon createTaxon(long taxId, String scientificName){
        return createTaxon(taxId, scientificName, "");
    }
    public static Taxon createTaxon(long taxId, String scientificName, String commonName){
        return createTaxon(taxId, scientificName, commonName, Collections.emptyList());
    }
    
    public static Taxon createTaxon(long taxId, String scientificName, String commonName, List<String> synonyms){
        return new TaxonImpl(taxId, scientificName, commonName, synonyms);
    }
    public static TaxonNode createTaxonNode(TaxonNode parent, Taxon taxon, TaxonomyRank rank){
        return new TaxonNodeImpl(parent, taxon, rank);
    }
}
