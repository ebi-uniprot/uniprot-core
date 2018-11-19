package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.MaximumVelocityImpl.class, name = "MaximumVelocityImpl")
})
public interface MaximumVelocity extends HasEvidences {
	public float getVelocity();
	public String getEnzyme();
	public String getUnit();

}
