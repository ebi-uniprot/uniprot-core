package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrganismNameImpl extends TaxonNameImpl implements OrganismName{
	public OrganismNameImpl(String scientificName) {
		this(scientificName, null);
		
	}

	public OrganismNameImpl(String scientificName, String commonName) {
		this(scientificName, commonName, Collections.emptyList());
	}

	@JsonCreator
	public OrganismNameImpl(@JsonProperty("scientificName") String scientificName,
			@JsonProperty("commonName") String commonName, 
			@JsonProperty("synonyms") List<String> synonyms) {
		super(scientificName, commonName, synonyms);
	}
	@JsonIgnore
	@Override
	public boolean isValid() {
		return !Strings.isNullOrEmpty(getScientificName());
  
	}

	
}
