package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.Optional;

public interface WebResourceComment extends Comment {
    public String ONLINE_INFORMATION_XMLTAG = "online information";

     String getResourceName();
     Optional<String> getNote();  
     Optional<String>getResourceUrl();
     boolean isFtp();
                                
}
