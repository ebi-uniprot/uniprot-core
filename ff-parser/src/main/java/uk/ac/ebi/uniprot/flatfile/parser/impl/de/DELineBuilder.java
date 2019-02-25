package uk.ac.ebi.uniprot.flatfile.parser.impl.de;

import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLines;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.EQUAL_SIGN;
import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.SEMICOLON;

public class DELineBuilder extends FFLineBuilderAbstr<ProteinDescription> implements FFLineBuilder<ProteinDescription> {

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
			deLines.addAll(buildRecNameLine(description.getRecommendedName(), REC_NAME, linePrefix,
					includeFlatFileMarkings, addEvidence));
		}

		if (description.getAlternativeNames() != null) {
			for (ProteinAltName name : description.getAlternativeNames()) {
				deLines.addAll(buildAltNameLine(name, ALT_NAME, linePrefix, includeFlatFileMarkings, addEvidence));
			}
		}

		if (description.getSubmissionNames() != null) {
			for (ProteinSubName name : description.getSubmissionNames()) {
				deLines.addAll(buildSubNameLine(name, SUB_NAME, linePrefix, includeFlatFileMarkings, addEvidence));
			}
		}

		for (ProteinSection section : description.getIncludes()) {
			deLines.addAll(buildSectionLines(section, INCLUDES, includeFlatFileMarkings, addEvidence));
		}
		for (ProteinSection section : description.getContains()) {
			deLines.addAll(buildSectionLines(section, CONTAINS, includeFlatFileMarkings, addEvidence));
		}
		if (description.getFlag() != null) {
			StringBuilder sb = new StringBuilder();
			if (includeFlatFileMarkings)
				sb.append(linePrefix);
			sb.append(FLAGS);
			Flag flag = description.getFlag();
			FlagType flagType = flag.getType();
			if (flagType == FlagType.FRAGMENT_PRECURSOR) {
				sb.append("Precursor; Fragment;");
			} else if (flagType == FlagType.FRAGMENTS_PRECURSOR) {
				sb.append("Precursor; Fragments;");
			} else {
				sb.append(flagType.getValue()).append(SEMICOLON);
			}
			deLines.add(sb.toString());
		}

		return deLines;
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
		sb.append(SEMICOLON);
		return sb.toString();

	}

	private List<String> buildAltNameLine(ProteinAltName name, String type, String deLinePrefix,
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

		Name allergenName = name.getAllergenName();
		if (allergenName != null) {
			lines.add(buildNameLine1(allergenName, "Allergen", ALT_NAME, deLinePrefix, includeFlatFileMarkings,
					showEvidence, true));
		}
		Name biotechName = name.getBiotechName();
		if (biotechName != null) {
			lines.add(buildNameLine1(biotechName, "Biotech", ALT_NAME, deLinePrefix, includeFlatFileMarkings,
					showEvidence, true));
		}
		List<Name> cdAntigenNames = name.getCdAntigenNames();
		boolean isFirst = true;
		for (Name cname : cdAntigenNames) {
			lines.add(buildNameLine1(cname, "CD_antigen", ALT_NAME, deLinePrefix, includeFlatFileMarkings, showEvidence,
					isFirst));
			isFirst = true;
		}

		List<Name> innNames = name.getInnNames();
		isFirst = true;
		for (Name iname : innNames) {
			lines.add(buildNameLine1(iname, "INN", ALT_NAME, deLinePrefix, includeFlatFileMarkings, showEvidence,
					isFirst));
			isFirst = true;
		}

		return lines;
	}

	private List<String> buildRecNameLine(ProteinRecName name, String type, String deLinePrefix,
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

	private List<String> buildSubNameLine(ProteinSubName name, String type, String deLinePrefix,
			boolean includeFlatFileMarkings, boolean showEvidence) {
		List<String> lines = new ArrayList<>();
		boolean first = true;

		if (name.getFullName() != null) {
			lines.add(buildNameLine1(name.getFullName(), FULL, type, deLinePrefix, includeFlatFileMarkings,
					showEvidence, first));
			first = false;
		}
		for (EC ecNumber : name.getEcNumbers()) {
			lines.add(buildNameLine1(ecNumber, EC, type, deLinePrefix, includeFlatFileMarkings, showEvidence, first));
			first = false;
		}

		return lines;
	}

	private List<String> buildSectionLines(ProteinSection section, String sectionType, boolean includeFlatFileMarkings,
			boolean addEvidence) {
		List<String> lines = new ArrayList<>();
		StringBuilder sb1 = new StringBuilder();
		if (includeFlatFileMarkings)
			sb1.append(linePrefix);
		sb1.append(sectionType);
		lines.add(sb1.toString());
		if (section.getRecommendedName() != null) {
			lines.addAll(buildRecNameLine(section.getRecommendedName(), REC_NAME, DE_PREFIX_2, includeFlatFileMarkings,
					addEvidence));
		}
		if (!section.getAlternativeNames().isEmpty()) {
			for (ProteinAltName name : section.getAlternativeNames()) {
				lines.addAll(buildAltNameLine(name, ALT_NAME, DE_PREFIX_2, includeFlatFileMarkings, addEvidence));
			}
		}
		return lines;
	}

}
