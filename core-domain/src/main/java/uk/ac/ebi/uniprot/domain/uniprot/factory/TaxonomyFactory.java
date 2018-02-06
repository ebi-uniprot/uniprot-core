package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonId;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.TaxonNodeImpl;

import java.util.Collections;
import java.util.List;

public enum TaxonomyFactory {
    INSTANCE;
    public TaxonId createTaxonId(long taxId){
        return new TaxonIdImpl(taxId);
    }
    public TaxonName createTaxonName(String name) {
    	return new TaxonNameImpl(name);
    }
    public Taxon createTaxon(long taxId, String scientificName){
        return createTaxon(taxId, scientificName, "");
    }
    public Taxon createTaxon(long taxId, String scientificName, String commonName){
        return createTaxon(taxId, scientificName, commonName, Collections.emptyList());
    }
    
    public Taxon createTaxon(long taxId, String scientificName, String commonName, List<String> synonyms){
        return new TaxonImpl(taxId, scientificName, commonName, synonyms);
    }
    public TaxonNode createTaxonNode(TaxonNode parent, Taxon taxon, TaxonomyRank rank){
        return new TaxonNodeImpl(parent, taxon, rank);
    }
}
