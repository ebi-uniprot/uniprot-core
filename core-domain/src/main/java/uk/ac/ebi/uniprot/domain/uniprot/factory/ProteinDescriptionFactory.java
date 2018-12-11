package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public enum ProteinDescriptionFactory {
	INSTANCE;


	public EC createECNumber(String value, List<Evidence> evidences) {
		return new ECImpl(value, evidences);
	}

	public Name createName(String value, List<Evidence> evidences) {
		return new NameImpl(value, evidences);
	}

	public ProteinName createProteinName(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
		return new ProteinNameImpl(fullName, shortNames, ecNumbers);

	}

	public ProteinSection createProteinNameSection(ProteinName recommendedName,
			List<ProteinName> alternativeNames) {
		return new ProteinSectionImpl(recommendedName, alternativeNames);
	}

	public ProteinDescription createProteinDescription(ProteinName recommendedName,
			List<ProteinName> alternativeNames) {
		return new ProteinDescriptionImpl(recommendedName, alternativeNames);
	}

	public ProteinDescription createProteinDescription(ProteinName recommendedName, List<ProteinName> alternativeNames,
			List<ProteinName> submissionNames, Flag flag) {
		return new ProteinDescriptionImpl(recommendedName, alternativeNames, submissionNames, flag);
	}

	public ProteinDescription createProteinDescription(ProteinDescriptionBuilder builder) {
		return builder.build();
	}
	public Flag createFlag(FlagType type) {
		return new FlagImpl(type);
	}
}
