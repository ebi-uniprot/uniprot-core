package org.uniprot.core.cv.xdb.validator;

public class DBXRef {
    private String accession;
    private String abbr;
    private String name;
    private String ref;
    private String linkType;
    private String server;
    private String dbUrl;
    private String category;

    public static class DBXRefBuilder {
        private String accession;
        private String abbr;
        private String name;
        private String ref;
        private String linkType;
        private String server;
        private String dbUrl;
        private String category;

        public DBXRefBuilder accession(String accession) {
            this.accession = accession;
            return this;
        }

        public DBXRefBuilder abbr(String abbr) {
            this.abbr = abbr;
            return this;
        }

        public DBXRefBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DBXRefBuilder ref(String ref) {
            this.ref = ref;
            return this;
        }

        public DBXRefBuilder linkType(String linkType) {
            this.linkType = linkType;
            return this;
        }

        public DBXRefBuilder server(String server) {
            this.server = server;
            return this;
        }

        public DBXRefBuilder dbUrl(String dbUrl) {
            this.dbUrl = dbUrl;
            return this;
        }

        public DBXRefBuilder category(String category) {
            this.category = category;
            return this;
        }

        public DBXRef build() {
            DBXRef dbxRef = new DBXRef();
            dbxRef.accession = this.accession;
            dbxRef.abbr = this.abbr;
            dbxRef.name = this.name;
            dbxRef.ref = this.ref;
            dbxRef.linkType = this.linkType;
            dbxRef.server = this.server;
            dbxRef.dbUrl = this.dbUrl;
            dbxRef.category = this.category;
            return dbxRef;
        }
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
        return "DBXRef{"
                + "accession='"
                + accession
                + '\''
                + ", abbr='"
                + abbr
                + '\''
                + ", name='"
                + name
                + '\''
                + ", ref='"
                + ref
                + '\''
                + ", linkType='"
                + linkType
                + '\''
                + ", server='"
                + server
                + '\''
                + ", dbUrl='"
                + dbUrl
                + '\''
                + ", category='"
                + category
                + '\''
                + '}';
    }
}
