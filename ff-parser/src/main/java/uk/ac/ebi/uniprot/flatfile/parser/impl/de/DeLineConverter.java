package uk.ac.ebi.uniprot.flatfile.parser.impl.de;

import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.flatfile.parser.impl.EvidenceConverterHelper;
import uk.ac.ebi.uniprot.flatfile.parser.impl.de.DeLineObject.FlagType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;


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
		List<ProteinAltName> altNames = convertAltNames( f.altName, f.alt_CD_antigen,
				f.alt_INN, f.alt_Allergen, f.alt_Biotech,			
				evidenceMap);
	
		List<ProteinSubName> subNames = f.subName.stream().map(val -> convertSubmissionName(val, evidenceMap))
				.collect(Collectors.toList());

		List<ProteinSection> contained = f.containedNames.stream()
				.map(val -> convertProteinNameSection(val, evidenceMap)).collect(Collectors.toList());

		List<ProteinSection> included = f.includedNames.stream().map(val -> convertProteinNameSection(val, evidenceMap))
				.collect(Collectors.toList());

		uk.ac.ebi.uniprot.domain.uniprot.description.FlagType flag = createFlag(f.flags);
		ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
		builder.recommendedName(recName).submissionNames(subNames).alternativeNames(altNames).contains(contained)
				.includes(included).flag(flag);

		return builder.build();

	}
	
	private List<ProteinAltName> convertAltNames(List<DeLineObject.Name> faltName, List<String> altCD_antigen,
			List<String> altINN, String altAllergen, String altBiotech, Map<Object, List<Evidence>> evidenceMap) {
		List<ProteinAltName> altNames = faltName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> cdAntigenNames = altCD_antigen.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> innNames = altINN.stream().map(val -> convertName(val, evidenceMap)).collect(Collectors.toList());
		Name biotechName = null;
		if (!Strings.isNullOrEmpty(altBiotech)) {
			biotechName = convertName(altBiotech, evidenceMap);
		}
		Name allergenName = null;
		if (!Strings.isNullOrEmpty(altAllergen)) {
			allergenName = convertName(altAllergen, evidenceMap);
		}
		if (cdAntigenNames.isEmpty() && innNames.isEmpty() && (biotechName == null) && (allergenName == null)) {
			return altNames;
		} else {
			List<ProteinAltName> altNames2 = new ArrayList<>();

			if (altNames.isEmpty()) {
				altNames2.add(new ProteinAltNameBuilder().cdAntigenNames(cdAntigenNames).innNames(innNames)
						.allergenName(allergenName).biotechName(biotechName).build());
				return altNames2;
			}
			ProteinAltName altName = altNames.get(0);
			altNames2.add(new ProteinAltNameBuilder().from(altName).cdAntigenNames(cdAntigenNames).innNames(innNames)
					.allergenName(allergenName).biotechName(biotechName).build());
			altNames2.addAll(altNames.subList(1, altNames.size()));
			return altNames2;
		}
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
		List<ProteinAltName> altNames = 
				convertAltNames(nameBlock.altName,nameBlock.alt_CD_antigen, nameBlock.alt_INN, nameBlock.alt_Allergen, nameBlock.alt_Biotech,
						evidenceMap);

		return new ProteinSectionBuilder().recommendedName(recName).alternativeNames(altNames).build();
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
