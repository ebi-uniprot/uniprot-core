package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyLineage;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;

import java.util.Objects;
/**
 *
 * @author lgonzales
 */
public class TaxonomyLineageImpl implements TaxonomyLineage {

    private static final long serialVersionUID = -319775179301440774L;

    private long taxonId;

    private String scientificName;

    private TaxonomyRank rank;

    private boolean hidden;

    private TaxonomyLineageImpl() {
    	
    }
    public TaxonomyLineageImpl(long taxonId, String scientificName, TaxonomyRank rank, boolean hidden) {
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
        return Utils.notEmpty(this.scientificName);
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
        return getTaxonId() == that.getTaxonId() &&
                isHidden() == that.isHidden() &&
                getScientificName().equals(that.getScientificName()) &&
                getRank() == that.getRank();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTaxonId(), getScientificName(), getRank(), isHidden());
    }
}
