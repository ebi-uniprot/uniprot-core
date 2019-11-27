package org.uniprot.core.crossref;

import org.uniprot.core.Builder;

import javax.annotation.Nonnull;

public class CrossRefEntryBuilder implements Builder<CrossRefEntryBuilder, CrossRefEntry> {
    private String name;
    private String accession;
    private String abbrev;
    private String pubMedId;
    private String doiId;
    private String linkType;
    private String server;
    private String dbUrl;
    private String category;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;

    @Override
    public @Nonnull
    CrossRefEntry build() {
        return new CrossRefEntryImpl(
                this.name,
                this.accession,
                this.abbrev,
                this.pubMedId,
                this.doiId,
                this.linkType,
                this.server,
                this.dbUrl,
                this.category,
                this.reviewedProteinCount,
                this.unreviewedProteinCount);
    }

    @Override
    public @Nonnull CrossRefEntryBuilder from(@Nonnull CrossRefEntry instance) {
        this.name = instance.getName();
        this.accession = instance.getAccession();
        this.abbrev = instance.getAbbrev();
        this.pubMedId = instance.getPubMedId();
        this.doiId = instance.getDoiId();
        this.linkType = instance.getLinkType();
        this.server = instance.getServer();
        this.dbUrl = instance.getDbUrl();
        this.category = instance.getCategory();
        this.reviewedProteinCount = instance.getReviewedProteinCount();
        this.unreviewedProteinCount = instance.getUnreviewedProteinCount();
        return this;
    }

    public @Nonnull CrossRefEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder accession(String accession) {
        this.accession = accession;
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

    public @Nonnull CrossRefEntryBuilder reviewedProteinCount(Long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull CrossRefEntryBuilder unreviewedProteinCount(Long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }
}
