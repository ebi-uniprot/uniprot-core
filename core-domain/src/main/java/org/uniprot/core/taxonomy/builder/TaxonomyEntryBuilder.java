package org.uniprot.core.taxonomy.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.taxonomy.*;
import org.uniprot.core.taxonomy.impl.TaxonomyEntryImpl;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.AbstractOrganismNameBuilder;
import org.uniprot.core.util.Utils;

public class TaxonomyEntryBuilder
        extends AbstractOrganismNameBuilder<TaxonomyEntryBuilder, TaxonomyEntry>
        implements Builder<TaxonomyEntryBuilder, TaxonomyEntry> {

    private long taxonId;

    private String mnemonic;

    private Long parentId;

    private TaxonomyRank rank;

    private Boolean hidden;

    private Boolean active;

    private List<String> otherNames = new ArrayList<>();

    private List<TaxonomyLineage> lineage = new ArrayList<>();

    private List<TaxonomyStrain> strains = new ArrayList<>();

    private List<Taxonomy> hosts = new ArrayList<>();

    private List<String> links = new ArrayList<>();

    private TaxonomyStatistics statistics;

    private TaxonomyInactiveReason inactiveReason;

    public TaxonomyEntryBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public TaxonomyEntryBuilder mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public TaxonomyEntryBuilder rank(TaxonomyRank rank) {
        this.rank = rank;
        return this;
    }

    public TaxonomyEntryBuilder parentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

    public TaxonomyEntryBuilder parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public TaxonomyEntryBuilder hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public TaxonomyEntryBuilder hidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public TaxonomyEntryBuilder active(Boolean active) {
        this.active = active;
        return this;
    }

    public TaxonomyEntryBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public TaxonomyEntryBuilder otherNames(List<String> otherNames) {
        this.otherNames = Utils.modifiableList(otherNames);
        return this;
    }

    public TaxonomyEntryBuilder addOtherNames(String otherNames) {
        Utils.addOrIgnoreNull(otherNames, this.otherNames);
        return this;
    }

    public TaxonomyEntryBuilder lineage(List<TaxonomyLineage> lineage) {
        this.lineage = Utils.modifiableList(lineage);
        return this;
    }

    public TaxonomyEntryBuilder addLineage(TaxonomyLineage lineage) {
        Utils.addOrIgnoreNull(lineage, this.lineage);
        return this;
    }

    public TaxonomyEntryBuilder strains(List<TaxonomyStrain> strains) {
        this.strains = Utils.modifiableList(strains);
        return this;
    }

    public TaxonomyEntryBuilder addStrain(TaxonomyStrain strains) {
        Utils.addOrIgnoreNull(strains, this.strains);
        return this;
    }

    public TaxonomyEntryBuilder hosts(List<Taxonomy> hosts) {
        this.hosts = Utils.modifiableList(hosts);
        return this;
    }

    public TaxonomyEntryBuilder addHost(Taxonomy host) {
        Utils.addOrIgnoreNull(host, this.hosts);
        return this;
    }

    public TaxonomyEntryBuilder links(List<String> links) {
        this.links = Utils.modifiableList(links);
        return this;
    }

    public TaxonomyEntryBuilder addLink(String link) {
        Utils.addOrIgnoreNull(link, this.links);
        return this;
    }

    public TaxonomyEntryBuilder statistics(TaxonomyStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public TaxonomyEntryBuilder inactiveReason(TaxonomyInactiveReason inactiveReason) {
        this.inactiveReason = inactiveReason;
        return this;
    }

    @Override
    public TaxonomyEntry build() {
        return new TaxonomyEntryImpl(
                taxonId,
                scientificName,
                commonName,
                synonyms,
                mnemonic,
                parentId,
                rank,
                hidden,
                active,
                otherNames,
                lineage,
                strains,
                hosts,
                links,
                statistics,
                inactiveReason);
    }

    @Override
    public TaxonomyEntryBuilder from(TaxonomyEntry instance) {
        if (instance != null) {
            super.from(instance);
            this.taxonId(instance.getTaxonId());
            this.mnemonic(instance.getMnemonic());
            this.parentId(instance.getParentId());
            this.rank(instance.getRank());
            this.hidden(instance.isHidden());
            this.active(instance.isActive());
            this.otherNames.clear();
            this.otherNames(instance.getOtherNames());
            this.lineage.clear();
            this.lineage(instance.getLineage());
            this.strains.clear();
            this.strains(instance.getStrains());
            this.hosts.clear();
            this.hosts(instance.getHosts());
            this.links.clear();
            this.links(instance.getLinks());
            this.statistics(instance.getStatistics());
            this.inactiveReason = instance.getInactiveReason();
        }
        return this;
    }

    @Override
    protected TaxonomyEntryBuilder getThis() {
        return this;
    }
}
