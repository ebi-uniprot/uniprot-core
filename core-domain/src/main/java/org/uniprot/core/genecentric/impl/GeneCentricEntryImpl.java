package org.uniprot.core.genecentric.impl;

import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;

import java.util.List;
import java.util.Objects;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class GeneCentricEntryImpl  implements GeneCentricEntry {

    private static final long serialVersionUID = 1643302978804066975L;
    private final Protein canonicalProtein;
    private final List<Protein> relatedProteins;

    GeneCentricEntryImpl(){
        this(null,null);
    }

    public GeneCentricEntryImpl(Protein canonicalProtein, List<Protein> relatedProteins) {
        this.canonicalProtein = canonicalProtein;
        this.relatedProteins = relatedProteins;
    }

    @Override
    public Protein getCanonicalProtein() {
        return canonicalProtein;
    }

    @Override
    public List<Protein> getRelatedProteins() {
        return relatedProteins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneCentricEntryImpl that = (GeneCentricEntryImpl) o;
        return Objects.equals(getCanonicalProtein(), that.getCanonicalProtein()) &&
                Objects.equals(getRelatedProteins(), that.getRelatedProteins());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCanonicalProtein(), getRelatedProteins());
    }
}
