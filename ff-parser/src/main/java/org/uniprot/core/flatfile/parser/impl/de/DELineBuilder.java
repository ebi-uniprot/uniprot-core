package org.uniprot.core.flatfile.parser.impl.de;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.EQUAL_SIGN;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

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

    private List<String> buildLines(
            ProteinDescription description, boolean includeFlatFileMarkings, boolean addEvidence) {

        List<String> deLines = new ArrayList<>();
        if (description.getRecommendedName() != null) {
            deLines.addAll(
                    buildNameLine(
                            description.getRecommendedName(),
                            REC_NAME,
                            linePrefix,
                            includeFlatFileMarkings,
                            addEvidence));
        }
        if (!description.getAlternativeNames().isEmpty()) {
            deLines.addAll(
                    buildAlternativeNameLine(
                            description.getAlternativeNames(),
                            linePrefix,
                            includeFlatFileMarkings,
                            addEvidence));
        }
        deLines.addAll(
                buildOtherNames(description, linePrefix, includeFlatFileMarkings, addEvidence));

        if (description.getSubmissionNames() != null) {
            for (ProteinSubName name : description.getSubmissionNames()) {
                deLines.addAll(
                        buildNameLine(
                                name, SUB_NAME, linePrefix, includeFlatFileMarkings, addEvidence));
            }
        }

        for (ProteinSection section : description.getIncludes()) {
            deLines.addAll(
                    buildSectionLines(section, INCLUDES, includeFlatFileMarkings, addEvidence));
        }
        for (ProteinSection section : description.getContains()) {
            deLines.addAll(
                    buildSectionLines(section, CONTAINS, includeFlatFileMarkings, addEvidence));
        }
        if (description.getFlag() != null) {
            StringBuilder sb = new StringBuilder();
            if (includeFlatFileMarkings) sb.append(linePrefix);
            sb.append(FLAGS);
            Flag flag = description.getFlag();
            FlagType flagType = flag.getType();
            if (flagType == FlagType.FRAGMENT_PRECURSOR) {
                sb.append("Precursor; Fragment;");
            } else if (flagType == FlagType.FRAGMENTS_PRECURSOR) {
                sb.append("Precursor; Fragments;");
            } else {
                sb.append(flagType.getName()).append(SEMICOLON);
            }
            deLines.add(sb.toString());
        }

        return deLines;
    }
    //
    // ALLERGEN("Allergen"),
    // BIOTECH("Biotech"),
    // CD_ANTIGEN("CD_antigen"),
    // INN("INN"),
    private List<String> buildOtherNames(
            ProteinDescription pd,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        Name allergenName = pd.getAllergenName();
        if (allergenName != null) {
            lines.add(
                    buildNameLine1(
                            allergenName,
                            "Allergen",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            true));
        }
        Name biotechName = pd.getBiotechName();
        if (biotechName != null) {
            lines.add(
                    buildNameLine1(
                            biotechName,
                            "Biotech",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            true));
        }
        List<Name> cdAntigenNames = pd.getCdAntigenNames();
        boolean isFirst = true;
        for (Name name : cdAntigenNames) {
            lines.add(
                    buildNameLine1(
                            name,
                            "CD_antigen",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            isFirst));
            isFirst = true;
        }

        List<Name> innNames = pd.getInnNames();
        isFirst = true;
        for (Name name : innNames) {
            lines.add(
                    buildNameLine1(
                            name,
                            "INN",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            isFirst));
            isFirst = true;
        }
        return lines;
    }

    private List<String> buildAlternativeNameLine(
            List<ProteinName> protAltNames,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        for (ProteinName altName : protAltNames) {
            lines.addAll(
                    buildNameLine(
                            altName,
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence));
        }

        return lines;
    }

    private String buildNameLine1(
            EvidencedValue value,
            String valueType,
            String NameType,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence,
            boolean isFirst) {
        StringBuilder sb = new StringBuilder();
        if (includeFlatFileMarkings) sb.append(deLinePrefix);
        if (isFirst) sb.append(NameType);
        else sb.append(DE_LINE_SPACE);
        sb.append(valueType).append(EQUAL_SIGN).append(value.getValue());
        addEvidences(sb, value, showEvidence);
        sb.append(SEMICOLON);
        return sb.toString();
    }

    private List<String> buildNameLine(
            ProteinName name,
            String type,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        boolean first = true;

        if (name.getFullName() != null) {
            lines.add(
                    buildNameLine1(
                            name.getFullName(),
                            FULL,
                            type,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            first));
            first = false;
        }
        for (Name shortName : name.getShortNames()) {
            lines.add(
                    buildNameLine1(
                            shortName,
                            SHORT,
                            type,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            first));
            first = false;
        }
        for (EC ecNumber : name.getEcNumbers()) {
            lines.add(
                    buildNameLine1(
                            ecNumber,
                            EC,
                            type,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            first));
            first = false;
        }

        return lines;
    }

    private List<String> buildNameLine(
            ProteinSubName name,
            String type,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        boolean first = true;

        if (name.getFullName() != null) {
            lines.add(
                    buildNameLine1(
                            name.getFullName(),
                            FULL,
                            type,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            first));
            first = false;
        }
        for (EC ecNumber : name.getEcNumbers()) {
            lines.add(
                    buildNameLine1(
                            ecNumber,
                            EC,
                            type,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            first));
            first = false;
        }

        return lines;
    }

    private List<String> buildSectionLines(
            ProteinSection section,
            String sectionType,
            boolean includeFlatFileMarkings,
            boolean addEvidence) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb1 = new StringBuilder();
        if (includeFlatFileMarkings) sb1.append(linePrefix);
        sb1.append(sectionType);
        lines.add(sb1.toString());
        if (section.getRecommendedName() != null) {
            lines.addAll(
                    buildNameLine(
                            section.getRecommendedName(),
                            REC_NAME,
                            DE_PREFIX_2,
                            includeFlatFileMarkings,
                            addEvidence));
        }
        if (!section.getAlternativeNames().isEmpty()) {
            lines.addAll(
                    buildAlternativeNameLine(
                            section.getAlternativeNames(),
                            DE_PREFIX_2,
                            includeFlatFileMarkings,
                            addEvidence));
        }
        lines.addAll(buildOtherNames(section, DE_PREFIX_2, includeFlatFileMarkings, addEvidence));

        return lines;
    }

    private List<String> buildOtherNames(
            ProteinSection pd,
            String deLinePrefix,
            boolean includeFlatFileMarkings,
            boolean showEvidence) {
        List<String> lines = new ArrayList<>();
        Name allergenName = pd.getAllergenName();
        if (allergenName != null) {
            lines.add(
                    buildNameLine1(
                            allergenName,
                            "Allergen",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            true));
        }
        Name biotechName = pd.getBiotechName();
        if (biotechName != null) {
            lines.add(
                    buildNameLine1(
                            biotechName,
                            "Biotech",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            true));
        }
        List<Name> cdAntigenNames = pd.getCdAntigenNames();
        boolean isFirst = true;
        for (Name name : cdAntigenNames) {
            lines.add(
                    buildNameLine1(
                            name,
                            "CD_antigen",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            isFirst));
            isFirst = true;
        }

        List<Name> innNames = pd.getInnNames();
        isFirst = true;
        for (Name name : innNames) {
            lines.add(
                    buildNameLine1(
                            name,
                            "INN",
                            ALT_NAME,
                            deLinePrefix,
                            includeFlatFileMarkings,
                            showEvidence,
                            isFirst));
            isFirst = true;
        }
        return lines;
    }
}
