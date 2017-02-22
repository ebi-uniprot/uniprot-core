package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface WebResourceComment extends Comment {
    public String ONLINE_INFORMATION_XMLTAG = "online information";

    public String getDatabaseName();

    public boolean hasDatabaseName();

    public String getDatabaseNote();

    public boolean hasDatabaseNote();

    public String getDatabaseURL();

    public boolean hasDatabaseURL();

    public String getDatabaseFTP();

    public boolean hasDatabaseFTP();
                                
}
