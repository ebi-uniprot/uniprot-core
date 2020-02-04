package org.uniprot.core.taxonomy.impl;

import java.util.Objects;

import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class TaxonomyLineageImpl implements TaxonomyLineage {

    private static final long serialVersionUID = -319775179301440774L;

    private long taxonId;

    private String scientificName;

    private TaxonomyRank rank;

    private boolean hidden;

    TaxonomyLineageImpl() {
        this(0, null, null, false);
    }

    public TaxonomyLineageImpl(
            long taxonId, String scientificName, TaxonomyRank rank, boolean hidden) {
        this.taxonId = taxonId;
        this.scientificName = scientificName;
        this.rank = rank;
        this.hidden = hidden;
    }

    @Override
    public long getTaxonId() {
        return taxonId;
    }

    @Override
    public String getScientificName() {
        return scientificName;
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
    public boolean hasScientificName() {
        return Utils.notNullNotEmpty(this.scientificName);
    }

    @Override
    public boolean hasRank() {
        return this.rank != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxonomyLineageImpl that = (TaxonomyLineageImpl) o;
        return getTaxonId() == that.getTaxonId()
                && isHidden() == that.isHidden()
                && Objects.equals(getScientificName(), that.getScientificName())
                && getRank() == that.getRank();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaxonId(), getScientificName(), getRank(), isHidden());
    }
}
