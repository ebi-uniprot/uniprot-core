package uk.ac.ebi.uniprot.domain.uniprot.description;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl.class, name = "ProteinDescription")
})

public interface ProteinDescription {

	ProteinName getRecommendedName();
	List<ProteinName> getAlternativeNames();
	List<ProteinName> getSubmissionNames();	
	Name getAllergenName();
	Name getBiotechName();
	List<Name> getCdAntigenNames();
	List<Name> getInnNames();
	List<ProteinSection> getIncludes();
	List<ProteinSection> getContains();
	boolean isValid();
	
}
