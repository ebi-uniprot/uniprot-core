package org.uniprot.core.uniprotkb.taxonomy.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;

public class TaxonomyBuilder extends AbstractOrganismNameBuilder<TaxonomyBuilder, Taxonomy> {
    private long taxonId;
    private String mnemonic;

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

    public static @Nonnull TaxonomyBuilder from(@Nonnull Taxonomy instance) {
        TaxonomyBuilder builder = new TaxonomyBuilder();
        AbstractOrganismNameBuilder.init(builder, instance);
        builder.taxonId(instance.getTaxonId()).mnemonic(instance.getMnemonic());
        return builder;
    }

    @Override
    protected @Nonnull TaxonomyBuilder getThis() {
        return this;
    }
}
