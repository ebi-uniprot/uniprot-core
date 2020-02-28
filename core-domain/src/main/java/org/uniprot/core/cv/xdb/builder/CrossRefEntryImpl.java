package org.uniprot.core.cv.xdb.builder;

import java.util.Objects;

import org.uniprot.core.cv.xdb.CrossRefEntry;

public class CrossRefEntryImpl implements CrossRefEntry {

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

    // no arg constructor for JSON deserialization
    CrossRefEntryImpl() {
        // do nothing.. just to satisfy the objectmapper
    }

    public CrossRefEntryImpl(
            String name,
            String accession,
            String abbrev,
            String pubMedId,
            String doiId,
            String linkType,
            String server,
            String dbUrl,
            String category,
            Long reviewedProteinCount,
            Long unreviewedProteinCount) {
        this.name = name;
        this.accession = accession;
        this.abbrev = abbrev;
        this.pubMedId = pubMedId;
        this.doiId = doiId;
        this.linkType = linkType;
        this.server = server;
        this.dbUrl = dbUrl;
        this.category = category;
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAccession() {
        return this.accession;
    }

    @Override
    public String getAbbrev() {
        return this.abbrev;
    }

    @Override
    public String getPubMedId() {
        return this.pubMedId;
    }

    @Override
    public String getDoiId() {
        return this.doiId;
    }

    @Override
    public String getLinkType() {
        return this.linkType;
    }

    @Override
    public String getServer() {
        return this.server;
    }

    @Override
    public String getDbUrl() {
        return this.dbUrl;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public Long getReviewedProteinCount() {
        return this.reviewedProteinCount;
    }

    @Override
    public Long getUnreviewedProteinCount() {
        return this.unreviewedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrossRefEntryImpl that = (CrossRefEntryImpl) o;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.accession, that.accession)
                && Objects.equals(this.abbrev, that.abbrev)
                && Objects.equals(this.pubMedId, that.pubMedId)
                && Objects.equals(this.doiId, that.doiId)
                && Objects.equals(this.linkType, that.linkType)
                && Objects.equals(this.server, that.server)
                && Objects.equals(this.dbUrl, that.dbUrl)
                && Objects.equals(this.category, that.category)
                && Objects.equals(this.reviewedProteinCount, that.reviewedProteinCount)
                && Objects.equals(this.unreviewedProteinCount, that.unreviewedProteinCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
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
}
