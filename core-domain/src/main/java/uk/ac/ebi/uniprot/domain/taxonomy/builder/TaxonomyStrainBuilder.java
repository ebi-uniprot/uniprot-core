package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyStrain;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyStrainImpl;

import java.util.ArrayList;
import java.util.List;

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
        Utils.nonNullAdd(synonym,this.synonyms);
        return this;
    }

    @Override
    public TaxonomyStrain build() {
        return new TaxonomyStrainImpl(name,synonyms);
    }

    @Override
    public TaxonomyStrainBuilder from(TaxonomyStrain instance) {
        if(instance != null) {
            this.name(instance.getName());
            this.synonyms(instance.getSynonyms());
        }
        return this;
    }
}
