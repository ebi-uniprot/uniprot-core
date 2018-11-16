package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.PhysiologicalReactionImpl.class, name = "PhysiologicalReactionImpl")
})
public interface PhysiologicalReaction  extends HasEvidences{
	PhysiologicalDirectionType getDirectionType();
	DBCrossReference<ReactionReferenceType> getReactionReference();
}
