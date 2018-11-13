package uk.ac.ebi.uniprot.domain.taxonomy;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismNameImpl.class, name = "organismNameImpl")
})
public interface OrganismName extends TaxonName{
	boolean isValid();
}
