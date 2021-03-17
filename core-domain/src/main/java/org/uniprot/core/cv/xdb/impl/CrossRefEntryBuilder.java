package org.uniprot.core.cv.xdb.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.xdb.CrossRefEntry;

public class CrossRefEntryBuilder implements Builder<CrossRefEntry> {
    private String name;
    private String id;
    private String abbrev;
    private String pubMedId;
    private String doiId;
    private String linkType;
    private String server;
    private String dbUrl;
    private String category;
    private Statistics statistics;

    @Override
    public @Nonnull CrossRefEntry build() {
        return new CrossRefEntryImpl(
                this.name,
                this.id,
                this.abbrev,
                this.pubMedId,
                this.doiId,
                this.linkType,
                this.server,
                this.dbUrl,
                this.category,
                statistics);
    }

    public static @Nonnull CrossRefEntryBuilder from(@Nonnull CrossRefEntry instance) {
        return new CrossRefEntryBuilder()
                .name(instance.getName())
                .id(instance.getId())
                .abbrev(instance.getAbbrev())
                .pubMedId(instance.getPubMedId())
                .doiId(instance.getDoiId())
                .linkType(instance.getLinkType())
                .server(instance.getServer())
                .dbUrl(instance.getDbUrl())
                .category(instance.getCategory())
                .statistics(instance.getStatistics());
    }

    public @Nonnull CrossRefEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder abbrev(String abbrev) {
        this.abbrev = abbrev;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder pubMedId(String pubMedId) {
        this.pubMedId = pubMedId;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder doiId(String doiId) {
        this.doiId = doiId;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder linkType(String linkType) {
        this.linkType = linkType;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder server(String server) {
        this.server = server;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder dbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder category(String category) {
        this.category = category;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder statistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }
}
