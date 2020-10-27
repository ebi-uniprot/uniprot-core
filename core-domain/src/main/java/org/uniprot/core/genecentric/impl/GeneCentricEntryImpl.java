package org.uniprot.core.genecentric.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 15/10/2020
 */
public class GeneCentricEntryImpl implements GeneCentricEntry {

    private static final long serialVersionUID = 1643302978804066975L;

    private final String proteomeId;
    private final Protein canonicalProtein;
    private final List<Protein> relatedProteins;

    GeneCentricEntryImpl() {
        this(null, null, null);
    }

    public GeneCentricEntryImpl(
            String proteomeId, Protein canonicalProtein, List<Protein> relatedProteins) {
        this.proteomeId = proteomeId;
        this.canonicalProtein = canonicalProtein;
        this.relatedProteins = Utils.unmodifiableList(relatedProteins);
    }

    @Override
    public String getProteomeId() {
        return proteomeId;
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
        return Objects.equals(getCanonicalProtein(), that.getCanonicalProtein())
                && Objects.equals(getProteomeId(), that.getProteomeId())
                && Objects.equals(getRelatedProteins(), that.getRelatedProteins());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProteomeId(), getCanonicalProtein(), getRelatedProteins());
    }
}
