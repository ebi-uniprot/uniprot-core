package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;

public class ProteinRecommendedNameImpl extends ProteinNameImpl implements ProteinRecommendedName {
	public ProteinRecommendedNameImpl(Name fullName,
			List<Name> shortNames,
			List<ECNumber> ecNumbers) {
		super(fullName, shortNames, ecNumbers);
	
	}	

}
