package org.uniprot.core.cv.xdb.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.xdb.CrossRefEntry;

public class CrossRefEntryImpl implements CrossRefEntry {

    private static final long serialVersionUID = -6498172017920571803L;
    private String name;
    private String id;
    private String abbrev;
    private String pubMedId;
    private String doiId;
    private String linkType;
    private List<String> servers;
    private String dbUrl;
    private String category;
    private Statistics statistics;

    // no arg constructor for JSON deserialization
    CrossRefEntryImpl() {
        // do nothing.. just to satisfy the objectmapper
    }

    public CrossRefEntryImpl(
            String name,
            String id,
            String abbrev,
            String pubMedId,
            String doiId,
            String linkType,
            List<String> servers,
            String dbUrl,
            String category,
            Statistics statistics) {
        this.name = name;
        this.id = id;
        this.abbrev = abbrev;
        this.pubMedId = pubMedId;
        this.doiId = doiId;
        this.linkType = linkType;
        this.servers = servers;
        this.dbUrl = dbUrl;
        this.category = category;
        this.statistics = statistics;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
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
    public List<String> getServers() {
        return this.servers;
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
    public Statistics getStatistics() {
        return this.statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrossRefEntryImpl that = (CrossRefEntryImpl) o;
        return Objects.equals(this.name, that.name)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.abbrev, that.abbrev)
                && Objects.equals(this.pubMedId, that.pubMedId)
                && Objects.equals(this.doiId, that.doiId)
                && Objects.equals(this.linkType, that.linkType)
                && Objects.equals(this.servers, that.servers)
                && Objects.equals(this.dbUrl, that.dbUrl)
                && Objects.equals(this.category, that.category)
                && Objects.equals(this.statistics, that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.name,
                this.id,
                this.abbrev,
                this.pubMedId,
                this.doiId,
                this.linkType,
                this.servers,
                this.dbUrl,
                this.category,
                this.statistics);
    }
}
