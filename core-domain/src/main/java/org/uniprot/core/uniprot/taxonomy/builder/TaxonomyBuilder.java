package org.uniprot.core.uniprot.taxonomy.builder;

import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;

import javax.annotation.Nonnull;

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
    public TaxonomyBuilder from(@Nonnull Taxonomy instance) {
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
