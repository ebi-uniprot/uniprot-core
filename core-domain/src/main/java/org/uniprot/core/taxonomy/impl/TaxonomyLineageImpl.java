package org.uniprot.core.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.uniprot.taxonomy.impl.AbstractOrganismNameImpl;

/** @author lgonzales */
public class TaxonomyLineageImpl extends AbstractOrganismNameImpl implements TaxonomyLineage {

    private static final long serialVersionUID = -319775179301440774L;

    private long taxonId;

    private TaxonomyRank rank;

    private boolean hidden;

    TaxonomyLineageImpl() {
        this(0, null, null, null, null, false);
    }

    public TaxonomyLineageImpl(
            long taxonId,
            String scientificName,
            String commonName,
            List<String> synonyms,
            TaxonomyRank rank,
            boolean hidden) {
        super(scientificName, commonName, synonyms);
        this.taxonId = taxonId;
        this.rank = rank;
        this.hidden = hidden;
    }

    @Override
    public long getTaxonId() {
        return taxonId;
    }

    @Override
    public TaxonomyRank getRank() {
        return rank;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public boolean hasTaxonId() {
        return taxonId > 0;
    }

    @Override
    public boolean hasRank() {
        return this.rank != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaxonomyLineageImpl that = (TaxonomyLineageImpl) o;
        return getTaxonId() == that.getTaxonId()
                && isHidden() == that.isHidden()
                && getRank() == that.getRank();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTaxonId(), getRank(), isHidden());
    }
}
