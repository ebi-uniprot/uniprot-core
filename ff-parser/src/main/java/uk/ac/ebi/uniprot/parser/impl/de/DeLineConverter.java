package uk.ac.ebi.uniprot.parser.impl.de;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
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
		ProteinRecommendedName recName = null;
		if (f.recName != null) {
			recName = convertRecName(f.recName, evidenceMap);
		}
		List<AltName> altNames = f.altName.stream().map(val -> convertAltName(val, evidenceMap))
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
			biotechName = convertName(f.alt_Allergen, evidenceMap);
		}
		ProteinAlternativeName proteinAltName = null;
		if (!altNames.isEmpty() || !cdAntigenNames.isEmpty() || !innNames.isEmpty() || (biotechName != null)
				|| (allergenName != null)) {
			proteinAltName = factory.createProteinAlternativeName(altNames, allergenName, biotechName, cdAntigenNames,
					innNames);
		}

		List<ProteinSubmissionName> subNames = f.subName.stream().map(val -> convertSubmissionName(val, evidenceMap))
				.collect(Collectors.toList());

		List<ProteinNameSection> contained = f.containedNames.stream()
				.map(val -> convertProteinNameSection(val, evidenceMap)).collect(Collectors.toList());

		List<ProteinNameSection> included = f.includedNames.stream()
				.map(val -> convertProteinNameSection(val, evidenceMap)).collect(Collectors.toList());

		Flag flag = createFlag(f.flags);
		return factory.createProteinDescription(recName, subNames, proteinAltName, flag, included, contained);

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

	private ProteinNameSection convertProteinNameSection(DeLineObject.NameBlock nameBlock,
			Map<Object, List<Evidence>> evidenceMap) {
		ProteinRecommendedName recName = null;
		if (nameBlock.recName != null) {
			recName = convertRecName(nameBlock.recName, evidenceMap);
		}
		List<AltName> altNames = nameBlock.altName.stream().map(val -> convertAltName(val, evidenceMap))
				.collect(Collectors.toList());
		List<Name> cdAntigenNames = nameBlock.alt_CD_antigen.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());

		List<Name> innNames = nameBlock.alt_INN.stream().map(val -> convertName(val, evidenceMap))
				.collect(Collectors.toList());
		Name biotechName = null;
		if ((nameBlock.alt_Biotech != null) && (!nameBlock.alt_Biotech.isEmpty())) {
			biotechName = convertName(nameBlock.alt_Biotech, evidenceMap);
		}
		Name allergenName = null;
		if ((nameBlock.alt_Allergen != null) && (!nameBlock.alt_Allergen.isEmpty())) {
			biotechName = convertName(nameBlock.alt_Allergen, evidenceMap);
		}

		ProteinAlternativeName proteinAltName = null;
		if (!altNames.isEmpty() || !cdAntigenNames.isEmpty() || !innNames.isEmpty() || (biotechName != null)
				|| (allergenName != null)) {
			proteinAltName = factory.createProteinAlternativeName(altNames, allergenName, biotechName, cdAntigenNames,
					innNames);
		}
		return factory.createProteinNameSection(recName, proteinAltName);
	}

	private Name convertName(String val, Map<Object, List<Evidence>> evidenceMap) {
		return factory.createProteinName(val, evidenceMap.get(val));
	}

	private AltName convertAltName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createProteinName(val.fullName, evidenceMap.get(val.fullName));
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> factory.createProteinName(shortName, evidenceMap.get(shortName)))
				.collect(Collectors.toList());
		List<ECNumber> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return factory.createECNumber(ec, evidenceMap.get(ecEvidence));
		}).collect(Collectors.toList());
		return factory.createAltName(fullName, shortNames, ecNumbers);

	}

	private ProteinRecommendedName convertRecName(DeLineObject.Name val, Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createProteinName(val.fullName, evidenceMap.get(val.fullName));
		}
		List<Name> shortNames = val.shortNames.stream()
				.map(shortName -> factory.createProteinName(shortName, evidenceMap.get(shortName)))
				.collect(Collectors.toList());
		List<ECNumber> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return factory.createECNumber(ec, evidenceMap.get(ecEvidence));
		}).collect(Collectors.toList());
		return factory.createProteinRecommendedName(fullName, shortNames, ecNumbers);

	}

	private ProteinSubmissionName convertSubmissionName(DeLineObject.Name val,
			Map<Object, List<Evidence>> evidenceMap) {
		Name fullName = null;
		if ((val.fullName != null) || (!val.fullName.isEmpty())) {
			fullName = factory.createProteinName(val.fullName, evidenceMap.get(val.fullName));
		}
		// List<Name> shortNames =val.shortNames.stream()
		// .map(shortName ->factory.createProteinName(shortName,
		// evidenceMap.get(shortName)) )
		// .collect(Collectors.toList());
		List<ECNumber> ecNumbers = val.ecs.stream().map(ec -> {
			DeLineObject.ECEvidence ecEvidence = new DeLineObject.ECEvidence();
			ecEvidence.ecValue = ec;
			ecEvidence.nameECBelong = val;
			return factory.createECNumber(ec, evidenceMap.get(ecEvidence));
		}).collect(Collectors.toList());
		return factory.createProteinSubmissionName(fullName, ecNumbers);

	}
}
