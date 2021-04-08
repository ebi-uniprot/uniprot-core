package org.uniprot.core.taxonomy.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.taxonomy.*;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyImpl;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class TaxonomyEntryImpl extends TaxonomyImpl implements TaxonomyEntry {

    private static final long serialVersionUID = -319775179301440773L;

    private final Taxonomy parent;

    private final TaxonomyRank rank;

    private final Boolean hidden;

    private final Boolean active;

    private final List<String> otherNames;

    private final List<TaxonomyLineage> lineage;

    private final List<TaxonomyStrain> strains;

    private final List<Taxonomy> hosts;

    private final List<String> links;

    private final TaxonomyStatistics statistics;

    private final TaxonomyInactiveReason inactiveReason;

    // no arg constructor for JSON deserialization
    TaxonomyEntryImpl() {
        this(
                0, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null);
    }

    TaxonomyEntryImpl(
            long taxonId,
            String scientificName,
            String commonName,
            List<String> synonyms,
            String mnemonic,
            Taxonomy parent,
            TaxonomyRank rank,
            Boolean hidden,
            Boolean active,
            List<String> otherNames,
            List<TaxonomyLineage> lineage,
            List<TaxonomyStrain> strains,
            List<Taxonomy> hosts,
            List<String> links,
            TaxonomyStatistics statistics,
            TaxonomyInactiveReason inactiveReason) {
        super(taxonId, scientificName, commonName, synonyms, mnemonic);
        this.parent = parent;
        this.rank = rank;
        this.hidden = hidden;
        this.active = active;
        this.otherNames = Utils.unmodifiableList(otherNames);
        this.lineage = Utils.unmodifiableList(lineage);
        this.strains = Utils.unmodifiableList(strains);
        this.hosts = Utils.unmodifiableList(hosts);
        this.links = Utils.unmodifiableList(links);
        this.statistics = statistics;
        this.inactiveReason = inactiveReason;
    }

    @Override
    public Taxonomy getParent() {
        return parent;
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
    public List<TaxonomyLineage> getLineages() {
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
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public boolean hasRank() {
        return rank != null;
    }

    @Override
    public boolean hasOtherNames() {
        return Utils.notNullNotEmpty(this.otherNames);
    }

    @Override
    public boolean hasLineage() {
        return Utils.notNullNotEmpty(this.lineage);
    }

    @Override
    public boolean hasStrains() {
        return Utils.notNullNotEmpty(this.strains);
    }

    @Override
    public boolean hasHosts() {
        return Utils.notNullNotEmpty(this.hosts);
    }

    @Override
    public boolean hasLinks() {
        return Utils.notNullNotEmpty(this.links);
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
        return "TaxonomyEntryImpl{"
                + "taxonId="
                + getTaxonId()
                + ", scientificName="
                + getScientificName()
                + ", commonName="
                + getCommonName()
                + ", mnemonic="
                + getMnemonic()
                + ", parent="
                + parent
                + ", rank="
                + rank
                + ", hidden="
                + hidden
                + ", active="
                + active
                + ", otherNames="
                + otherNames
                + ", lineage="
                + lineage
                + ", strains="
                + strains
                + ", hosts="
                + hosts
                + ", links="
                + links
                + ", statistics="
                + statistics
                + ", inactiveReason="
                + inactiveReason
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TaxonomyEntryImpl that = (TaxonomyEntryImpl) o;
        return Objects.equals(getParent(), that.getParent())
                && isHidden() == that.isHidden()
                && isActive() == that.isActive()
                && getRank() == that.getRank()
                && Objects.equals(getOtherNames(), that.getOtherNames())
                && Objects.equals(getLineages(), that.getLineages())
                && Objects.equals(getStrains(), that.getStrains())
                && Objects.equals(getHosts(), that.getHosts())
                && Objects.equals(getLinks(), that.getLinks())
                && Objects.equals(getStatistics(), that.getStatistics())
                && Objects.equals(getInactiveReason(), that.getInactiveReason());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                super.hashCode(),
                getParent(),
                getRank(),
                isHidden(),
                isActive(),
                getOtherNames(),
                getLineages(),
                getStrains(),
                getHosts(),
                getLinks(),
                getStatistics(),
                getInactiveReason());
    }
}
