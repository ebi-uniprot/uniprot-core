package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferenceImpl.class, name = "UniProtReferenceImpl")
})
public interface UniProtReference<T extends Citation> extends HasEvidences {
    T getCitation();
    public List<ReferenceComment> getTypedReferenceComments(ReferenceCommentType type);
    public List<ReferenceComment> getReferenceComments();
    public List<String> getReferencePositions();
   
}
