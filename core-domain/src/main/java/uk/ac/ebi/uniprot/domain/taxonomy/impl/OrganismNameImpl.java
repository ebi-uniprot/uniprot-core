package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

import java.util.Collections;
import java.util.List;

public class OrganismNameImpl extends TaxonNameImpl implements OrganismName{

	private OrganismNameImpl(){
		super(null);
	}

	public OrganismNameImpl(String scientificName) {
		this(scientificName, null);
		
	}

	public OrganismNameImpl(String scientificName, String commonName) {
		this(scientificName, commonName, Collections.emptyList());
	}


	public OrganismNameImpl(String scientificName, String commonName, List<String> synonyms) {
		super(scientificName, commonName, synonyms);
	}

	@Override
	public boolean isValid() {
		return getScientificName() != null && !getScientificName().isEmpty();
  
	}

	
}
