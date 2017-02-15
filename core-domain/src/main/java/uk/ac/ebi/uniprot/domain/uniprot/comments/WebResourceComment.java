package uk.ac.ebi.uniprot.domain.uniprot.comments;

public interface WebResourceComment extends Comment {
    public String ONLINE_INFORMATION_XMLTAG = "online information";

    public DatabaseName getDatabaseName();

    public boolean hasDatabaseName();

    public DatabaseNote getDatabaseNote();

    public boolean hasDatabaseNote();

    public DatabaseURL getDatabaseURL();

    public boolean hasDatabaseURL();

    public DatabaseFTP getDatabaseFTP();

    public boolean hasDatabaseFTP();
                                
}
