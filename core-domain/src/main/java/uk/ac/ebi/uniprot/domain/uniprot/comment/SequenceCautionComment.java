package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;


@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.SequenceCautionCommentImpl.class, name = "SequenceCautionCommentImpl")
})
public interface SequenceCautionComment extends Comment, HasEvidences {

     String getSequence();
     
     String getNote();

     SequenceCautionType getSequenceCautionType();

     List<String> getPositions();
}
