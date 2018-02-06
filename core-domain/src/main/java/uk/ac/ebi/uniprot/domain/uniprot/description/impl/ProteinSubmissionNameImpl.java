package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;

public class ProteinSubmissionNameImpl extends ProteinNameImpl implements ProteinSubmissionName {
	public ProteinSubmissionNameImpl(Name fullName,
			List<ECNumber> ecNumbers) {
		super(fullName, Collections.emptyList(), ecNumbers);
	}
	
}
