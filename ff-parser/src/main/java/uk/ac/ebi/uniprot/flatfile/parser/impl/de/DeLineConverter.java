package uk.ac.ebi.uniprot.flatfile.parser.impl.de;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinAltNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinRecNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSubNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceConverterHelper;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineObject.FlagType;


public class DeLineConverter extends EvidenceCollector implements Converter<DeLineObject, ProteinDescription> {

	@Override
	public ProteinDescription convert(DeLineObject f) {
		Map<Object, List<Evidence>> evidenceMap = EvidenceConverterHelper.convert(f.getEvidenceInfo());
		this.addAll(evidenceMap.values());
		// ProteinDescription pd = factory.buildProteinDescription();
		ProteinRecName recName = null;
		if (f.recName != null) {
			recName = convertRecName(f.recName, evidenceMap);
		}
		List<ProteinAltName> altNames = f.altName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());
		
		List<Name> cdAntigenNames = f.alt_CD_antigen.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> innNames = f.alt_INN.stream().map(val -> convertName(val, evidenceMap)).collect(Collectors.toList());
		Name biotechName = null;
		if (!Strings.isNullOrEmpty(f.alt_Biotech)) {
			biotechName = convertName(f.alt_Biotech, evidenceMap);
		}
		Name allergenName = null;
		if (!Strings.isNullOrEmpty(f.alt_Allergen)) {
			allergenName = convertName(f.alt_Allergen, evidenceMap);
		}
		List<ProteinSubName> subNames = f.subName.stream().map(val -> convertSubmissionName(val, evidenceMap))
				.collect(Collectors.toList());

		List<ProteinSection> contained = f.containedNames.stream()
				.map(val -> convertProteinNameSection(val, evidenceMap)).collect(Collectors.toList());

		List<ProteinSection> included = f.includedNames.stream().map(val -> convertProteinNameSection(val, evidenceMap))
				.collect(Collectors.toList());

		uk.ac.ebi.uniprot.domain.uniprot.description.FlagType flag = createFlag(f.flags);
		ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
		builder.recommendedName(recName).submissionNames(subNames).alternativeNames(altNames).contains(contained)
				.includes(included).flag(flag)
				.cdAntigenNames(cdAntigenNames)
				.innNames(innNames)
				.allergenName(allergenName)
				.biotechName(biotechName);

		return builder.build();

	}

	private uk.ac.ebi.uniprot.domain.uniprot.description.FlagType createFlag(List<FlagType> flags) {
		if (flags.contains(FlagType.Fragment)) {
			if (flags.contains(FlagType.Precursor)) {
				return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT_PRECURSOR;
			} else {
				return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT;
			}
		} else if (flags.contains(FlagType.Fragments)) {
			if (flags.contains(FlagType.Precursor)) {
				return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENTS_PRECURSOR;
			} else {
				return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENTS;
			}
		} else if (flags.contains(FlagType.Precursor)) {
			return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.PRECURSOR;
		} else if (flags.contains(FlagType.Precursor_Fragment)) {
			return uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT_PRECURSOR;
		} else {
			return null;
		}
	}

	private ProteinSection convertProteinNameSection(DeLineObject.NameBlock nameBlock,
			Map<Object, List<Evidence>> evidenceMap) {
		ProteinRecName recName = null;
		if (nameBlock.recName != null) {
			recName = convertRecName(nameBlock.recName, evidenceMap);
		}
		
		List<ProteinAltName> altNames = nameBlock.altName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());
		
		List<Name> cdAntigenNames =nameBlock.alt_CD_antigen.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> innNames = nameBlock.alt_INN.stream().map(val -> convertName(val, evidenceMap)).collect(Collectors.toList());
		Name biotechName = null;
		if (!Strings.isNullOrEmpty(nameBlock.alt_Biotech)) {
			biotechName = convertName(nameBlock.alt_Biotech, evidenceMap);
		}
		Name allergenName = null;
		if (!Strings.isNullOrEmpty(nameBlock.alt_Allergen)) {
			allergenName = convertName(nameBlock.alt_Allergen, evidenceMap);
		}
		


		return new ProteinSectionBuilder().recommendedName(recName).alternativeNames(altNames)
				.cdAntigenNames(cdAntigenNames)
				.innNames(innNames)
				.allergenName(allergenName)
				.biotechName(biotechName).build();
	}

	private Name convertName(String val, Map<Object, List<Evidence>> evidenceMap) {
		NameBuilder builder = new NameBuilder();
		return builder.value(val).evidences(evidenceMap.get(val)).build();
	}

	private ProteinAltName convertAltName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = new NameBuilder().value(val.fullName).evidences(evidenceMap.get(val.fullName)).build();
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> new NameBuilder().value(shortName).evidences(evidenceMap.get(shortName)).build())
				.collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return new ECBuilder().value(ec).evidences(evidenceMap.get(ecEvidence)).build();
		}).collect(Collectors.toList());
		return new ProteinAltNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(ecNumbers).build();

	}

	private ProteinRecName convertRecName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = new NameBuilder().value(val.fullName).evidences(evidenceMap.get(val.fullName)).build();
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> new NameBuilder().value(shortName).evidences(evidenceMap.get(shortName)).build())
				.collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec -> createEC(ec, val, evidenceMap)).collect(Collectors.toList());
		return new ProteinRecNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(ecNumbers).build();

	}

	private EC createEC(String ec, DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
		ecEvidence.ecValue = ec;
		ecEvidence.nameECBelong = val;
		List<Evidence> evidences = evidenceMap.get(ecEvidence);
		return new ECBuilder().value(ec).evidences(evidences).build();
	}

	private ProteinSubName convertSubmissionName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = new NameBuilder().value(val.fullName).evidences(evidenceMap.get(val.fullName)).build();
		}
		// List<Name> shortNames =val.shortNames.stream()
		// .map(shortName ->factory.createProteinName(shortName,
		// evidenceMap.get(shortName)) )
		// .collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return new ECBuilder().value(ec).evidences(evidenceMap.get(ecEvidence)).build();
		}).collect(Collectors.toList());
		return new ProteinSubNameBuilder().fullName(fullName).ecNumbers(ecNumbers).build();

	}
}
