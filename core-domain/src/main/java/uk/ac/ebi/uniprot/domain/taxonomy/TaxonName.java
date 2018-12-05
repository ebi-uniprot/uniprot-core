package uk.ac.ebi.uniprot.domain.taxonomy;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNameImpl.class, name = "taxonNameImpl")
})
public interface TaxonName {
	String getScientificName();

	String getCommonName();

	List<String> getSynonyms();
}
