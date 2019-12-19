package org.uniprot.core.uniprot.taxonomy.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismHostImpl;

public class OrganismHostBuilder
        extends AbstractOrganismNameBuilder<OrganismHostBuilder, OrganismHost> {
    private long taxonId;

    public @Nonnull OrganismHostBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull OrganismHost build() {
        return new OrganismHostImpl(taxonId, scientificName, commonName, synonyms);
    }

    public @Nonnull OrganismHostBuilder from(@Nonnull OrganismHost instance) {
        super.from(instance);
        this.taxonId(instance.getTaxonId());
        return this;
    }

    @Override
    protected @Nonnull OrganismHostBuilder getThis() {
        return this;
    }
}
