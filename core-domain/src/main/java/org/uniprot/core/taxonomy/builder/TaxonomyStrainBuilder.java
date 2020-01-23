package org.uniprot.core.taxonomy.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.impl.TaxonomyStrainImpl;
import org.uniprot.core.util.Utils;

public class TaxonomyStrainBuilder implements Builder<TaxonomyStrain> {

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

    public static @Nonnull TaxonomyStrainBuilder from(@Nonnull TaxonomyStrain instance) {
        return new TaxonomyStrainBuilder()
            .name(instance.getName())
            .synonyms(instance.getSynonyms());
    }
}
