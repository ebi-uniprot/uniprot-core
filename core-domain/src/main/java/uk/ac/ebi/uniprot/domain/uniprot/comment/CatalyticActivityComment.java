package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CatalyticActivityCommentImpl.class, name = "CatalyticActivityCommentImpl")
})
public interface CatalyticActivityComment extends Comment {
	Reaction getReaction();
	List<PhysiologicalReaction> getPhysiologicalReactions();
}
