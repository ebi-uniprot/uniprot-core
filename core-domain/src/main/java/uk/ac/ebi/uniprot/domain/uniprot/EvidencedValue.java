package uk.ac.ebi.uniprot.domain.uniprot;


import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.Value;
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl.class, name = "EvidencedValueImpl")
})

public interface EvidencedValue extends Value, HasEvidences{
	public String getDisplayed(String separator);
}
