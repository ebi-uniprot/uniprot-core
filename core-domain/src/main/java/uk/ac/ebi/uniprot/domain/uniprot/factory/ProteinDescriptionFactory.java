package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.AltNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinNameSectionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.NameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinAlternativeNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinRecommendedNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSubmissionNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

public enum ProteinDescriptionFactory {
	INSTANCE;

	public AltName createAltName(Name fullName, List<Name> shortNames, List<ECNumber> ecNumbers) {
		return new AltNameImpl(fullName, shortNames, ecNumbers);

	}

	public ECNumber createECNumber(String value, List<Evidence> evidences) {
		return new ECNumberImpl(value, evidences);
	}

	public Flag createFlag(FlagType type) {
		return new FlagImpl(type);
	}

	public Name createProteinName(String value, List<Evidence> evidences) {
		return new NameImpl(value, evidences);
	}

	public ProteinSubmissionName createProteinSubmissionName(Name fullName, List<ECNumber> eCNumbers) {
		return new ProteinSubmissionNameImpl(fullName, eCNumbers);
	}

	public ProteinRecommendedName createProteinRecommendedName(Name fullName, List<Name> shortNames,
			List<ECNumber> ecNumbers) {
		return new ProteinRecommendedNameImpl(fullName, shortNames, ecNumbers);

	}
	public ProteinAlternativeName createProteinAlternativeName(List<AltName> altNames) {
		return createProteinAlternativeName(altNames, null, null, null, null);
	}
	public ProteinAlternativeName createProteinAlternativeName(List<AltName> altNames,
			Name allergenName, Name biotechName, List<Name> cdAntigenNames, List<Name> innNames
			) {
		return new ProteinAlternativeNameImpl( altNames,
				 allergenName,
				 biotechName,
				 cdAntigenNames,
				 innNames)  ;	
	}
	public ProteinNameSection createProteinNameSection(ProteinRecommendedName recName, ProteinAlternativeName altName) {
		return new ProteinNameSectionImpl(recName, altName);
	}
	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames, ProteinAlternativeName alternativeName, Flag flag,
			List<ProteinNameSection> includes, List<ProteinNameSection> contains) {
		return new ProteinDescriptionImpl(recommendedName, submissionNames, alternativeName, flag, includes, contains);
	}

	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames, ProteinAlternativeName alternativeName, Flag flag) {
		return new ProteinDescriptionImpl(recommendedName, submissionNames, alternativeName, flag);
	}

	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			List<ProteinSubmissionName> submissionNames, ProteinAlternativeName alternativeName) {
		return new ProteinDescriptionImpl(recommendedName, submissionNames, alternativeName);
	}
}
