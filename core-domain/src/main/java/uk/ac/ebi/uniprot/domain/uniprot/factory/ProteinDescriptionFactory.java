package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ECImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.NameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

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
			List<ProteinName> submissionNames) {
		return new ProteinDescriptionImpl(recommendedName, alternativeNames, submissionNames);
	}

	public ProteinDescription createProteinDescription(ProteinDescriptionBuilder builder) {
		return builder.build();
	}
}
