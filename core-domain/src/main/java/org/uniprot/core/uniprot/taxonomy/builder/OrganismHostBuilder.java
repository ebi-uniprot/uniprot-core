package org.uniprot.core.uniprot.taxonomy.builder;

import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismHostImpl;

import javax.annotation.Nonnull;

public class OrganismHostBuilder
        extends AbstractOrganismNameBuilder<OrganismHostBuilder, OrganismHost> {
    private long taxonId;

    public OrganismHostBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull OrganismHost build() {
        return new OrganismHostImpl(taxonId, scientificName, commonName, synonyms);
    }

    public OrganismHostBuilder from(@Nonnull OrganismHost instance) {
        this.taxonId(instance.getTaxonId());
        this.scientificName(instance.getScientificName());
        this.commonName(instance.getCommonName());
        this.synonyms(instance.getSynonyms());
        return this;
    }

    @Override
    protected OrganismHostBuilder getThis() {
        return this;
    }
}
