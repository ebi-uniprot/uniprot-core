package uk.ac.ebi.uniprot.parser.impl.de;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

public class DELineBuilder extends FFLineBuilderAbstr<ProteinDescription> 
implements FFLineBuilder<ProteinDescription> {

	private static final String EC = "EC";
	private static final String SHORT = "Short";
	private static final String FULL = "Full";
	private static final String FLAGS = "Flags: ";
	private static final String CONTAINS = "Contains:";
	private static final String INCLUDES = "Includes:";
	private static final String REC_NAME = "RecName: ";
	private static final String DE_LINE_SPACE = "         ";
	private static final String DE_PREFIX_2 = "DE     ";
	private static final String SUB_NAME = "SubName: ";
	private static final String ALT_NAME = "AltName: ";

	public DELineBuilder() {
		super(LineType.DE);
	}

	@Override
	public String buildString(ProteinDescription f) {
		List<String> lines = buildLines(f, false, false);
		return FFLines.create(lines).toString();
	}

	@Override
	public String buildStringWithEvidence(ProteinDescription f) {
		List<String> lines = buildLines(f, false, true);
		return FFLines.create(lines).toString();
	}

	@Override
	protected FFLine buildLine(ProteinDescription f, boolean showEvidence) {
		List<String> lines = buildLines(f, true, showEvidence);
		return FFLines.create(lines);
	}

	private List<String> buildLines(ProteinDescription description, boolean includeFlatFileMarkings,
			boolean addEvidence) {

		List<String> deLines = new ArrayList<>();
		if (description.getRecommendedName() != null) {
			deLines.addAll(buildNameLine(description.getRecommendedName(), REC_NAME, linePrefix,
					includeFlatFileMarkings, addEvidence));
		}
		if (description.getAlternativeName() != null) {
			deLines.addAll(buildAlternativeNameLine(description.getAlternativeName(), linePrefix,
					includeFlatFileMarkings, addEvidence));
		}

		if (description.getSubmissionNames() != null) {
			for (ProteinName name : description.getSubmissionNames()) {
				deLines.addAll(buildNameLine(name, SUB_NAME, linePrefix, includeFlatFileMarkings, addEvidence));
			}
		}

		for (ProteinNameSection section : description.getIncludes()) {
			deLines.addAll(buildSectionLines(section, INCLUDES, includeFlatFileMarkings, addEvidence));
		}
		for (ProteinNameSection section : description.getContains()) {
			deLines.addAll(buildSectionLines(section, CONTAINS, includeFlatFileMarkings, addEvidence));
		}
		if (description.getFlag() != null) {
			StringBuilder sb = new StringBuilder();
			if (includeFlatFileMarkings)
				sb.append(linePrefix);
			sb.append(FLAGS);
			Flag flag = description.getFlag();
			FlagType flagType = flag.getFlagType();
			if (flagType == FlagType.FRAGMENT_PRECURSOR) {
				sb.append("Fragment; Precursor;");
			} else if (flagType == FlagType.FRAGMENTS_PRECURSOR) {
				sb.append("Fragments; Precursor;");
			} else {
				sb.append(flagType.getValue()).append(SEMI_COMA);
			}
			deLines.add(sb.toString());
		}

		return deLines;
	}

	private List<String> buildAlternativeNameLine(ProteinAlternativeName protAltNames, String deLinePrefix,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		List<AltName> altNames = protAltNames.getAltNames();
		for (AltName altName : altNames) {
			lines.addAll(buildNameLine(altName, ALT_NAME, deLinePrefix, includeFlatFileMarkings, showEvidence));
		}
		//
		// ALLERGEN("Allergen"),
		// BIOTECH("Biotech"),
		// CD_ANTIGEN("CD_antigen"),
		// INN("INN"),
		Name allergenName = protAltNames.getAllergenName();
		if (allergenName != null) {
			lines.add(buildNameLine1(allergenName, "Allergen", ALT_NAME, deLinePrefix, includeFlatFileMarkings,
					showEvidence, true));
		}
		Name biotechName = protAltNames.getBiotechName();
		if (biotechName != null) {
			lines.add(buildNameLine1(biotechName, "Biotech", ALT_NAME, deLinePrefix, includeFlatFileMarkings,
					showEvidence, true));
		}
		List<Name> cdAntigenNames = protAltNames.getCDAntigenNames();
		boolean isFirst = true;
		for (Name name : cdAntigenNames) {
			lines.add(buildNameLine1(name, "CD_antigen", ALT_NAME, deLinePrefix, includeFlatFileMarkings, showEvidence,
					isFirst));
			isFirst = false;
		}

		List<Name> innNames = protAltNames.getINNNames();
		isFirst = true;
		for (Name name : innNames) {
			lines.add(buildNameLine1(name, "INN", ALT_NAME, deLinePrefix, includeFlatFileMarkings, showEvidence,
					isFirst));
			isFirst = false;
		}
		return lines;
	}

	private String buildNameLine1(EvidencedValue value, String valueType, String NameType, String deLinePrefix,
			boolean includeFlatFileMarkings, boolean showEvidence, boolean isFirst) {
		StringBuilder sb = new StringBuilder();
		if (includeFlatFileMarkings)
			sb.append(deLinePrefix);
		if (isFirst)
			sb.append(NameType);
		else
			sb.append(DE_LINE_SPACE);
		sb.append(valueType).append(EQUAL_SIGN).append(value.getValue());
		addEvidences(sb, value, showEvidence);
		sb.append(SEMI_COMA);
		return sb.toString();

	}

	private List<String> buildNameLine(ProteinName name, String type, String deLinePrefix,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		boolean first = true;

		if (name.getFullName() != null) {
			lines.add(buildNameLine1(name.getFullName(), FULL, type, deLinePrefix, includeFlatFileMarkings,
					showEvidence, first));
			first = false;
		}
		for (Name shortName : name.getShortNames()) {
			lines.add(
					buildNameLine1(shortName, SHORT, type, deLinePrefix, includeFlatFileMarkings, showEvidence, first));
			first = false;
		}
		for (EC ecNumber : name.getEcNumbers()) {
			lines.add(buildNameLine1(ecNumber, EC, type, deLinePrefix, includeFlatFileMarkings, showEvidence, first));
			first = false;
		}

		return lines;
	}

	private List<String> buildSectionLines(ProteinNameSection section, String sectionType,
			boolean includeFlatFileMarkings, boolean addEvidence) {
		List<String> lines = new ArrayList<>();
		StringBuilder sb1 = new StringBuilder();
		if (includeFlatFileMarkings)
			sb1.append(linePrefix);
		sb1.append(sectionType);
		lines.add(sb1.toString());
		if (section.getRecommendedName() != null) {
			lines.addAll(buildNameLine(section.getRecommendedName(), REC_NAME, DE_PREFIX_2, includeFlatFileMarkings,
					addEvidence));
		}
		if (section.getAlternativeName() != null) {
			lines.addAll(buildAlternativeNameLine(section.getAlternativeName(), DE_PREFIX_2, includeFlatFileMarkings,
					addEvidence));
		}
		return lines;
	}

}
