package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.taxonomy.*;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonomyEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.AbstractOrganismNameBuilder;

import java.util.ArrayList;
import java.util.List;

public class TaxonomyEntryBuilder extends AbstractOrganismNameBuilder<TaxonomyEntryBuilder,TaxonomyEntry>
        implements Builder<TaxonomyEntryBuilder, TaxonomyEntry> {

    private long taxonId;

    private String mnemonic;

    private long parentId;

    private TaxonomyRank rank;

    private boolean hidden;

    private boolean active;

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

    public TaxonomyEntryBuilder hidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public TaxonomyEntryBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public TaxonomyEntryBuilder otherNames(List<String> otherNames) {
        this.otherNames = Utils.nonNullList(otherNames);
        return this;
    }

    public TaxonomyEntryBuilder addOtherNames(String otherNames) {
        Utils.nonNullAdd(otherNames,this.otherNames);
        return this;
    }

    public TaxonomyEntryBuilder lineage(List<TaxonomyLineage> lineage) {
        this.lineage = Utils.nonNullList(lineage);
        return this;
    }

    public TaxonomyEntryBuilder addLineage(TaxonomyLineage lineage) {
        Utils.nonNullAdd(lineage,this.lineage);
        return this;
    }

    public TaxonomyEntryBuilder strains(List<TaxonomyStrain> strains) {
        this.strains = Utils.nonNullList(strains);
        return this;
    }

    public TaxonomyEntryBuilder addStrain(TaxonomyStrain strains) {
        Utils.nonNullAdd(strains,this.strains);
        return this;
    }

    public TaxonomyEntryBuilder hosts(List<Taxonomy> hosts) {
        this.hosts = Utils.nonNullList(hosts);
        return this;
    }

    public TaxonomyEntryBuilder addHost(Taxonomy host) {
        Utils.nonNullAdd(host,this.hosts);
        return this;
    }

    public TaxonomyEntryBuilder links(List<String> links) {
        this.links = Utils.nonNullList(links);
        return this;
    }

    public TaxonomyEntryBuilder addLink(String link) {
        Utils.nonNullAdd(link,this.links);
        return this;
    }

    public TaxonomyEntryBuilder statistics(TaxonomyStatistics statistics){
        this.statistics = statistics;
        return this;
    }

    public TaxonomyEntryBuilder inactiveReason(TaxonomyInactiveReason inactiveReason){
        this.inactiveReason = inactiveReason;
        return this;
    }

    @Override
    public TaxonomyEntry build() {
        return new TaxonomyEntryImpl(taxonId,scientificName,commonName,synonyms,mnemonic,parentId,
                rank,hidden,active,otherNames,lineage,strains,hosts,links,statistics,inactiveReason);
    }

    @Override
    public TaxonomyEntryBuilder from(TaxonomyEntry instance) {
        if(instance != null) {
            this.taxonId(instance.getTaxonId());
            this.scientificName(instance.getScientificName());
            this.commonName(instance.getCommonName());
            this.synonyms.clear();
            this.synonyms(instance.getSynonyms());
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
