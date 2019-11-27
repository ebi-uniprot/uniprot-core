package org.uniprot.core.uniprot.comment;

public interface WebResourceComment extends Comment, HasMolecule {
    String ONLINE_INFORMATION_XMLTAG = "online information";

    String getResourceName();

    String getNote();

    String getResourceUrl();

    boolean isFtp();

    boolean hasResourceName();

    boolean hasNote();

    boolean hasResourceUrl();
}
