package uk.ac.ebi.uniprot.parser.impl.de;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.EvidenceCollector;
import uk.ac.ebi.uniprot.parser.impl.EvidenceHelper;
import uk.ac.ebi.uniprot.parser.impl.de.DeLineObject.FlagType;

public class DeLineConverter extends EvidenceCollector implements Converter<DeLineObject, ProteinDescription> {
	private final ProteinDescriptionFactory factory = ProteinDescriptionFactory.INSTANCE;

	@Override
	public ProteinDescription convert(DeLineObject f) {
		Map<Object, List<Evidence>> evidenceMap = EvidenceHelper.convert(f.getEvidenceInfo());
		this.addAll(evidenceMap.values());
		// ProteinDescription pd = factory.buildProteinDescription();
		ProteinName recName = null;
		if (f.recName != null) {
			recName = convertRecName(f.recName, evidenceMap);
		}
		List<ProteinName> altNames = f.altName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> cdAntigenNames = f.alt_CD_antigen.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> innNames = f.alt_INN.stream().map(val -> convertName(val, evidenceMap)).collect(Collectors.toList());
		Name biotechName = null;
		if ((f.alt_Biotech != null) && (!f.alt_Biotech.isEmpty())) {
			biotechName = convertName(f.alt_Biotech, evidenceMap);
		}
		Name allergenName = null;
		if ((f.alt_Allergen != null) && (!f.alt_Allergen.isEmpty())) {
			allergenName = convertName(f.alt_Allergen, evidenceMap);
		}
		List<ProteinName> subNames = f.subName.stream().map(val -> convertSubmissionName(val, evidenceMap))
				.collect(Collectors.toList());

		List<ProteinSection> contained = f.containedNames.stream()
				.map(val -> convertProteinNameSection(val, evidenceMap)).collect(Collectors.toList());

		List<ProteinSection> included = f.includedNames.stream().map(val -> convertProteinNameSection(val, evidenceMap))
				.collect(Collectors.toList());

		Flag flag = createFlag(f.flags);
		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		builder.recommendedName(recName).submissionNames(subNames).alternativeNames(altNames).allergenName(allergenName)
				.biotechName(biotechName).cdAntigenNames(cdAntigenNames).innNames(innNames).contains(contained)
				.includes(included).flag(flag);

		return builder.build();

	}

	private Flag createFlag(List<FlagType> flags) {
		if (flags.contains(FlagType.Fragment)) {
			if (flags.contains(FlagType.Precursor)) {
				return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT_PRECURSOR);
			} else {
				return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT);
			}
		} else if (flags.contains(FlagType.Fragments)) {
			if (flags.contains(FlagType.Precursor)) {
				return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENTS_PRECURSOR);
			} else {
				return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENTS);
			}
		} else if (flags.contains(FlagType.Precursor)) {
			return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.PRECURSOR);
		} else if (flags.contains(FlagType.Precursor_Fragment)) {
			return factory.createFlag(uk.ac.ebi.uniprot.domain.uniprot.description.FlagType.FRAGMENT_PRECURSOR);
		} else {
			return null;
		}
	}

	private ProteinSection convertProteinNameSection(DeLineObject.NameBlock nameBlock,
			Map<Object, List<Evidence>> evidenceMap) {
		ProteinName recName = null;
		if (nameBlock.recName != null) {
			recName = convertRecName(nameBlock.recName, evidenceMap);
		}
		List<ProteinName> altNames = nameBlock.altName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());

		return factory.createProteinNameSection(recName, altNames);
	}

	private Name convertName(String val, Map<Object, List<Evidence>> evidenceMap) {
		return factory.createName(val, evidenceMap.get(val));
	}

	private ProteinName convertAltName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createName(val.fullName, evidenceMap.get(val.fullName));
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> factory.createName(shortName, evidenceMap.get(shortName)))
				.collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return factory.createECNumber(ec, evidenceMap.get(ecEvidence));
		}).collect(Collectors.toList());
		return factory.createProteinName(fullName, shortNames, ecNumbers);

	}

	private ProteinName convertRecName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createName(val.fullName, evidenceMap.get(val.fullName));
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> factory.createName(shortName, evidenceMap.get(shortName)))
				.collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec ->
			createEC(ec, val, evidenceMap))
			.collect(Collectors.toList());
		return factory.createProteinName(fullName, shortNames, ecNumbers);

	}

	private EC createEC(String ec, DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
		ecEvidence.ecValue = ec;
		ecEvidence.nameECBelong = val;
		List<Evidence> evidences = evidenceMap.get(ecEvidence);
		return factory.createECNumber(ec, evidences);
	}
	private ProteinName convertSubmissionName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createName(val.fullName, evidenceMap.get(val.fullName));
		}
		// List<Name> shortNames =val.shortNames.stream()
		// .map(shortName ->factory.createProteinName(shortName,
		// evidenceMap.get(shortName)) )
		// .collect(Collectors.toList());
		List<EC> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return factory.createECNumber(ec, evidenceMap.get(ecEvidence));
		}).collect(Collectors.toList());
		return factory.createProteinName(fullName, null, ecNumbers);

	}
}
