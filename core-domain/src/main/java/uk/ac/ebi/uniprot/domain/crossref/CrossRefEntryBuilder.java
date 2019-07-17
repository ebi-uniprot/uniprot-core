package uk.ac.ebi.uniprot.domain.crossref;

import uk.ac.ebi.uniprot.domain.Builder;

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


    public static CrossRefEntryBuilder newInstance() {
        return new CrossRefEntryBuilder();
    }

    @Override
    public CrossRefEntry build() {
        return new CrossRefEntryImpl(this.name, this.accession, this.abbrev,
                this.pubMedId, this.doiId, this.linkType,
                this.server, this.dbUrl, this.category, this.reviewedProteinCount, this.unreviewedProteinCount);
    }

    @Override
    public CrossRefEntryBuilder from(CrossRefEntry instance) {
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

    public CrossRefEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CrossRefEntryBuilder accession(String accession) {
        this.accession = accession;
        return this;
    }

    public CrossRefEntryBuilder abbrev(String abbrev) {
        this.abbrev = abbrev;
        return this;
    }

    public CrossRefEntryBuilder pubMedId(String pubMedId) {
        this.pubMedId = pubMedId;
        return this;
    }

    public CrossRefEntryBuilder doiId(String doiId) {
        this.doiId = doiId;
        return this;
    }

    public CrossRefEntryBuilder linkType(String linkType) {
        this.linkType = linkType;
        return this;
    }

    public CrossRefEntryBuilder server(String server) {
        this.server = server;
        return this;
    }

    public CrossRefEntryBuilder dbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
        return this;
    }

    public CrossRefEntryBuilder category(String category) {
        this.category = category;
        return this;
    }

    public CrossRefEntryBuilder reviewedProteinCount(Long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public CrossRefEntryBuilder unreviewedProteinCount(Long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }
}