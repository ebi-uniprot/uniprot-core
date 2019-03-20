package uk.ac.ebi.uniprot.cv.xdb.validator;

public class DBXRef {
    private String accession;
    private String abbr;
    private String name;
    private String ref;
    private String linkType;
    private String server;
    private String dbUrl;
    private String category;

    public DBXRef(String abbr, String dbUrl, String category){
        this.abbr = abbr;
        this.dbUrl = dbUrl;
        this.category = category;
    }

    public String getAccession() {
        return accession;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getName() {
        return name;
    }

    public String getRef() {
        return ref;
    }

    public String getLinkType() {
        return linkType;
    }

    public String getServer() {
        return server;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "DBXRef{" +
                "accession='" + accession + '\'' +
                ", abbr='" + abbr + '\'' +
                ", name='" + name + '\'' +
                ", ref='" + ref + '\'' +
                ", linkType='" + linkType + '\'' +
                ", server='" + server + '\'' +
                ", dbUrl='" + dbUrl + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
