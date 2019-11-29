package org.uniprot.core.taxonomy.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.impl.TaxonomyStrainImpl;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;

public class TaxonomyStrainBuilder implements Builder<TaxonomyStrainBuilder, TaxonomyStrain> {

    private String name;

    private List<String> synonyms = new ArrayList<>();

    public @Nonnull TaxonomyStrainBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull TaxonomyStrainBuilder synonyms(List<String> synonyms) {
        this.synonyms = Utils.modifiableList(synonyms);
        return this;
    }

    public @Nonnull TaxonomyStrainBuilder addSynonym(String synonym) {
        Utils.addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    @Override
    public @Nonnull TaxonomyStrain build() {
        return new TaxonomyStrainImpl(name, synonyms);
    }

    @Override
    public @Nonnull TaxonomyStrainBuilder from(@Nonnull TaxonomyStrain instance) {
        if (instance != null) {
            this.name(instance.getName());
            this.synonyms(instance.getSynonyms());
        }
        return this;
    }
}
