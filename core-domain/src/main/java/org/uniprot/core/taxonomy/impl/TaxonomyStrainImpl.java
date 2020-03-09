package org.uniprot.core.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class TaxonomyStrainImpl implements TaxonomyStrain {

    private static final long serialVersionUID = -319775179301440775L;

    private String name;

    private List<String> synonyms;

    // no arg constructor for JSON deserialization
    TaxonomyStrainImpl() {
        this(null, null);
    }

    TaxonomyStrainImpl(String name, List<String> synonyms) {
        this.name = name;
        this.synonyms = Utils.unmodifiableList(synonyms);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasName() {
        return Utils.notNullNotEmpty(this.name);
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public boolean hasSynonyms() {
        return Utils.notNullNotEmpty(this.synonyms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyStrainImpl that = (TaxonomyStrainImpl) o;
        return Objects.equals(getName(), that.getName())
                && Objects.equals(getSynonyms(), that.getSynonyms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSynonyms());
    }

    @Override
    public String toString() {
        return "TaxonomyStrainImpl{" + "name='" + name + '\'' + ", synonyms=" + synonyms + '}';
    }
}
