package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.WebResourceCommentImpl.class, name = "WebResourceCommentImpl")
})
public interface WebResourceComment extends Comment {
    public String ONLINE_INFORMATION_XMLTAG = "online information";

     String getResourceName();
     String getNote();  
     String getResourceUrl();
     boolean isFtp();
                                
}
