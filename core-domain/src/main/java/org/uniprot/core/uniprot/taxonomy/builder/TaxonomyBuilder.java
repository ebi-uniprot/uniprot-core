package org.uniprot.core.uniprot.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;

public class TaxonomyBuilder extends AbstractOrganismNameBuilder<TaxonomyBuilder, Taxonomy> {
    private long taxonId;
    private String mnemonic;

    public static @Nonnull TaxonomyBuilder newInstance() {
        return new TaxonomyBuilder();
    }

    @Override
    public @Nonnull Taxonomy build() {
        return new TaxonomyImpl(taxonId, scientificName, commonName, synonyms, mnemonic);
    }

    public @Nonnull TaxonomyBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull TaxonomyBuilder mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    @Override
    public @Nonnull TaxonomyBuilder from(@Nonnull Taxonomy instance) {
        super.from(instance);
        this.taxonId(instance.getTaxonId());
        this.mnemonic(instance.getMnemonic());
        return this;
    }

    @Override
    protected @Nonnull TaxonomyBuilder getThis() {
        return this;
    }
}
