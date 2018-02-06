package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;

public class AltNameImpl extends ProteinNameImpl implements AltName {
	public AltNameImpl(Name fullName,
			List<Name> shortNames,
			List<ECNumber> ecNumbers) {
		super(fullName, shortNames, ecNumbers);
	
	}	
}
