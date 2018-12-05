package uk.ac.ebi.uniprot.domain.uniprot.comment;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.RnaEditingCommentImpl.RnaEdPositionImpl.class, name = "RnaEdPositionImpl")
})
public interface RnaEdPosition extends HasEvidences{

    public String getPosition();
}
