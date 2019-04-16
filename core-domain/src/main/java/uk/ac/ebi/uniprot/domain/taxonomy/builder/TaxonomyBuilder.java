package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyImpl;

public class TaxonomyBuilder extends AbstractOrganismNameBuilder<TaxonomyBuilder, Taxonomy> {
	  private long taxonId;
	  private String mnemonic;
	  public static TaxonomyBuilder newInstance() {
		  return new TaxonomyBuilder();
	  }
	@Override
	public Taxonomy build() {
		 return new TaxonomyImpl(taxonId, scientificName, commonName, synonyms, mnemonic);
	}

	public TaxonomyBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }
	public TaxonomyBuilder mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }
	
	@Override
	public TaxonomyBuilder from(Taxonomy instance) {
		  this.taxonId(instance.getTaxonId());
		  this.mnemonic(instance.getMnemonic());
	        this.scientificName(instance.getScientificName());
	        this.commonName(instance.getCommonName());
	        this.synonyms(instance.getSynonyms());
	        return this;
	}

	@Override
	protected TaxonomyBuilder getThis() {
		return this;
	}

}
