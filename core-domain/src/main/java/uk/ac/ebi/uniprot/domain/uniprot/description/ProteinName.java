package uk.ac.ebi.uniprot.domain.uniprot.description;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinNameImpl.class, name = "proteinNameImpl")
})
public interface ProteinName {
	Name getFullName();
	List<Name> getShortNames();
	List<EC> getEcNumbers();
	
	 boolean isValid() ;
}
