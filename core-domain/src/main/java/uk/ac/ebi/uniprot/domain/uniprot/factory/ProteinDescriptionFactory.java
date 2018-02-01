package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.description.RecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.AltNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.FlagImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinRecommendedNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.ProteinSubmissionNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.RecNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

public enum ProteinDescriptionFactory {
	INSTANCE;

	public AltName createAltName( ProteinName fullName,
		 List<ProteinName> shortNames,
		 List<ECNumber> ecNumbers) {
		AltNameImpl.Builder builder = AltNameImpl.newBuilder();
		builder.fullName(fullName);
		if(shortNames !=null) {
			shortNames.forEach(shortName->builder.shortName(shortName));
		}
		if(ecNumbers !=null) {
			ecNumbers.forEach(ecNumber->builder.ecNumber(ecNumber));
		}
		return builder.build();
	}
	public AltName createAltName(AltNameImpl.Builder builder) {
		return builder.build();
	}

	public ECNumber createECNumber(String value, List<Evidence> evidences) {
		return new ECNumberImpl(value, evidences);
	}

	public Flag createFlag(FlagType type) {
		return new FlagImpl(type);
	}

	public ProteinName createProteinName(String value, List<Evidence> evidences) {
		return new ProteinNameImpl(value, evidences);
	}

	public RecName createRecName(ProteinName fullName, List<ProteinName> shortNames, List<ECNumber> eCNumbers) {
		return new RecNameImpl(fullName, shortNames, eCNumbers);
	}

	public ProteinSubmissionName createProteinSubmissionName(ProteinName fullName, List<ECNumber> eCNumbers) {
		return new ProteinSubmissionNameImpl(fullName, eCNumbers);
	}

	public ProteinRecommendedName createProteinRecommendedName(RecName recName, List<AltName> altNames) {
		return new ProteinRecommendedNameImpl(recName, altNames);
	}

	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName, Flag flag, List<ProteinRecommendedName> includes,
			List<ProteinRecommendedName> contains) {
		return new ProteinDescriptionImpl(recommendedName, submmissionName, flag, includes, contains);
	}

	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName, Flag flag) {
		return new ProteinDescriptionImpl(recommendedName, submmissionName, flag);
	}

	public ProteinDescription createProteinDescription(ProteinRecommendedName recommendedName,
			ProteinSubmissionName submmissionName) {
		return new ProteinDescriptionImpl(recommendedName, submmissionName);
	}
}
