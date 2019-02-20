package uk.ac.ebi.uniprot.domain.uniprot.comment;

public interface WebResourceComment extends Comment {
    public String ONLINE_INFORMATION_XMLTAG = "online information";

    String getResourceName();

    String getNote();

    String getResourceUrl();

    boolean isFtp();

    boolean hasResourceName();

    boolean hasNote();

    boolean hasResourceUrl();

}
