package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.BPCPCommentImpl.class, name = "BPCPCommentImpl")
})
public interface BPCPComment extends Comment {

	 Absorption getAbsorption();
	
	 KineticParameters getKineticParameters();

	 PhDependence getPhDependence();

	 RedoxPotential getRedoxPotential();

	 TemperatureDependence getTemperatureDependence();

}
