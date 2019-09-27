package org.uniprot.core.taxonomy.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.impl.TaxonomyStrainImpl;
import org.uniprot.core.util.Utils;

public class TaxonomyStrainBuilder implements Builder<TaxonomyStrainBuilder, TaxonomyStrain> {

    private String name;

    private List<String> synonyms = new ArrayList<>();

    public TaxonomyStrainBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TaxonomyStrainBuilder synonyms(List<String> synonyms) {
        this.synonyms = Utils.nonNullList(synonyms);
        return this;
    }

    public TaxonomyStrainBuilder addSynonym(String synonym) {
        Utils.nonNullAdd(synonym, this.synonyms);
        return this;
    }

    @Override
    public TaxonomyStrain build() {
        return new TaxonomyStrainImpl(name, synonyms);
    }

    @Override
    public TaxonomyStrainBuilder from(TaxonomyStrain instance) {
        if (instance != null) {
            this.name(instance.getName());
            this.synonyms(instance.getSynonyms());
        }
        return this;
    }
}
