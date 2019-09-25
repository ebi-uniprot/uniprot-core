package org.uniprot.core.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.taxonomy.*;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.impl.TaxonomyImpl;
import org.uniprot.core.util.Utils;
/**
 *
 * @author lgonzales
 */
public class TaxonomyEntryImpl extends TaxonomyImpl implements TaxonomyEntry {

    private static final long serialVersionUID = -319775179301440773L;

    private Long parentId;

    private TaxonomyRank rank;

    private Boolean hidden;

    private Boolean active;

    private List<String> otherNames;

    private List<TaxonomyLineage> lineage;

    private List<TaxonomyStrain> strains;

    private List<Taxonomy> hosts;

    private List<String> links;

    private TaxonomyStatistics statistics;

    private TaxonomyInactiveReason inactiveReason;

    private TaxonomyEntryImpl(){
        this(0,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    public TaxonomyEntryImpl(long taxonId, String scientificName, String commonName, List<String> synonyms,
                             String mnemonic, Long parentId, TaxonomyRank rank, Boolean hidden, Boolean active,
                             List<String> otherNames, List<TaxonomyLineage> lineage, List<TaxonomyStrain> strains,
                             List<Taxonomy> hosts, List<String> links, TaxonomyStatistics statistics,
                             TaxonomyInactiveReason inactiveReason) {
        super(taxonId, scientificName, commonName, synonyms, mnemonic);
        this.parentId = parentId;
        this.rank = rank;
        this.hidden = hidden;
        this.active = active;
        this.otherNames = Utils.nonNullList(otherNames);
        this.lineage = Utils.nonNullList(lineage);
        this.strains = Utils.nonNullList(strains);
        this.hosts = Utils.nonNullList(hosts);
        this.links = Utils.nonNullList(links);
        this.statistics = statistics;
        this.inactiveReason = inactiveReason;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public TaxonomyRank getRank() {
        return rank;
    }

    @Override
    public Boolean isHidden() {
        return hidden != null && hidden;
    }

    @Override
    public Boolean isActive() {
        return active != null && active;
    }

    @Override
    public List<String> getOtherNames() {
        return otherNames;
    }

    @Override
    public List<TaxonomyLineage> getLineage() {
        return lineage;
    }

    @Override
    public List<TaxonomyStrain> getStrains() {
        return strains;
    }

    @Override
    public List<Taxonomy> getHosts() {
        return hosts;
    }

    @Override
    public List<String> getLinks() {
        return links;
    }

    @Override
    public TaxonomyStatistics getStatistics() {
        return statistics;
    }

    @Override
    public TaxonomyInactiveReason getInactiveReason() {
        return inactiveReason;
    }

    @Override
    public boolean hasParentId() {
        return parentId != null && parentId > 0;
    }

    @Override
    public boolean hasRank() {
        return rank != null;
    }

    @Override
    public boolean hasOtherNames() {
        return Utils.notNullOrEmpty(this.otherNames);
    }

    @Override
    public boolean hasLineage() {
        return Utils.notNullOrEmpty(this.lineage);
    }

    @Override
    public boolean hasStrains() {
        return Utils.notNullOrEmpty(this.strains);
    }

    @Override
    public boolean hasHosts() {
        return Utils.notNullOrEmpty(this.hosts);
    }

    @Override
    public boolean hasLinks() {
        return Utils.notNullOrEmpty(this.links);
    }

    @Override
    public boolean hasStatistics() {
        return this.statistics != null;
    }

    @Override
    public boolean hasInactiveReason() {
        return this.inactiveReason != null;
    }

    @Override
    public String toString() {
        return "TaxonomyEntryImpl{" +
                "taxonId=" + getTaxonId() +
                ", scientificName=" + getScientificName() +
                ", commonName=" + getCommonName() +
                ", mnemonic=" + getMnemonic() +
                ", parentId=" + parentId +
                ", rank=" + rank +
                ", hidden=" + hidden +
                ", active=" + active +
                ", otherNames=" + otherNames +
                ", lineage=" + lineage +
                ", strains=" + strains +
                ", hosts=" + hosts +
                ", links=" + links +
                ", statistics=" + statistics +
                ", inactiveReason=" + inactiveReason +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaxonomyEntryImpl that = (TaxonomyEntryImpl) o;
        return Objects.equals(getParentId(),that.getParentId()) &&
                isHidden() == that.isHidden() &&
                isActive() == that.isActive() &&
                getRank() == that.getRank() &&
                Objects.equals(getOtherNames(), that.getOtherNames()) &&
                Objects.equals(getLineage(), that.getLineage()) &&
                Objects.equals(getStrains(), that.getStrains()) &&
                Objects.equals(getHosts(), that.getHosts()) &&
                Objects.equals(getLinks(), that.getLinks()) &&
                Objects.equals(getStatistics(), that.getStatistics()) &&
                Objects.equals(getInactiveReason(), that.getInactiveReason());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getParentId(), getRank(), isHidden(), isActive(), getOtherNames(),
                getLineage(), getStrains(), getHosts(), getLinks(), getStatistics(), getInactiveReason());
    }
}
