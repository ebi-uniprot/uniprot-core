package org.uniprot.core.taxonomy.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

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

    public @Nonnull TaxonomyEntryBuilder taxonId(long taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder mnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder rank(TaxonomyRank rank) {
        this.rank = rank;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder parentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder hidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder active(Boolean active) {
        this.active = active;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder otherNames(List<String> otherNames) {
        this.otherNames = Utils.modifiableList(otherNames);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder addOtherNames(String otherNames) {
        Utils.addOrIgnoreNull(otherNames, this.otherNames);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder lineage(List<TaxonomyLineage> lineage) {
        this.lineage = Utils.modifiableList(lineage);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder addLineage(TaxonomyLineage lineage) {
        Utils.addOrIgnoreNull(lineage, this.lineage);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder strains(List<TaxonomyStrain> strains) {
        this.strains = Utils.modifiableList(strains);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder addStrain(TaxonomyStrain strains) {
        Utils.addOrIgnoreNull(strains, this.strains);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder hosts(List<Taxonomy> hosts) {
        this.hosts = Utils.modifiableList(hosts);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder addHost(Taxonomy host) {
        Utils.addOrIgnoreNull(host, this.hosts);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder links(List<String> links) {
        this.links = Utils.modifiableList(links);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder addLink(String link) {
        Utils.addOrIgnoreNull(link, this.links);
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder statistics(TaxonomyStatistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public @Nonnull TaxonomyEntryBuilder inactiveReason(TaxonomyInactiveReason inactiveReason) {
        this.inactiveReason = inactiveReason;
        return this;
    }

    @Override
    public @Nonnull TaxonomyEntry build() {
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

    public static @Nonnull TaxonomyEntryBuilder from(@Nonnull TaxonomyEntry instance) {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        AbstractOrganismNameBuilder.init(builder, instance);
        builder.taxonId(instance.getTaxonId())
            .mnemonic(instance.getMnemonic())
            .parentId(instance.getParentId())
            .rank(instance.getRank())
            .hidden(instance.isHidden())
            .active(instance.isActive())
            .otherNames(instance.getOtherNames())
            .lineage(instance.getLineage())
            .strains(instance.getStrains())
            .hosts(instance.getHosts())
            .links(instance.getLinks())
            .statistics(instance.getStatistics())
            .inactiveReason(instance.getInactiveReason());
        return builder;
    }

    @Override
    protected @Nonnull TaxonomyEntryBuilder getThis() {
        return this;
    }
}
