package org.uniprot.core.crossref;

import java.util.Objects;

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

    private CrossRefEntryImpl() {
        // do nothing.. just to satisfy the objectmapper
    }

    public CrossRefEntryImpl(String name, String accession, String abbrev, String pubMedId,
                             String doiId, String linkType, String server, String dbUrl, String category,
                             Long reviewedProteinCount, Long unreviewedProteinCount) {
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAccession() {
        return this.accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    @Override
    public String getAbbrev() {
        return this.abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

    @Override
    public String getPubMedId() {
        return this.pubMedId;
    }

    public void setPubMedId(String pubMedId) {
        this.pubMedId = pubMedId;
    }

    @Override
    public String getDoiId() {
        return this.doiId;
    }

    public void setDoiId(String doiId) {
        this.doiId = doiId;
    }

    @Override
    public String getLinkType() {
        return this.linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @Override
    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String getDbUrl() {
        return this.dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public Long getReviewedProteinCount() {
        return this.reviewedProteinCount;
    }

    public void setReviewedProteinCount(Long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
    }

    @Override
    public Long getUnreviewedProteinCount() {
        return this.unreviewedProteinCount;
    }

    public void setUnreviewedProteinCount(Long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrossRefEntryImpl that = (CrossRefEntryImpl) o;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.accession, that.accession) &&
                Objects.equals(this.abbrev, that.abbrev) &&
                Objects.equals(this.pubMedId, that.pubMedId) &&
                Objects.equals(this.doiId, that.doiId) &&
                Objects.equals(this.linkType, that.linkType) &&
                Objects.equals(this.server, that.server) &&
                Objects.equals(this.dbUrl, that.dbUrl) &&
                Objects.equals(this.category, that.category) &&
                Objects.equals(this.reviewedProteinCount, that.reviewedProteinCount) &&
                Objects.equals(this.unreviewedProteinCount, that.unreviewedProteinCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.accession, this.abbrev, this.pubMedId, this.doiId, this.linkType,
                this.server, this.dbUrl, this.category, this.reviewedProteinCount, this.unreviewedProteinCount);
    }
}
