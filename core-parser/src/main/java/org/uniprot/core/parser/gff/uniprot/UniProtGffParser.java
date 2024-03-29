package org.uniprot.core.parser.gff.uniprot;

import static org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType.*;
import static org.uniprot.core.util.Utils.nullOrEmpty;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.flatfile.parser.impl.ft.FTLineBuilderHelper;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

/** @author gqi */
public class UniProtGffParser {
    private static final String FEATURE_SOURCE = "UniProtKB";
    private static final String EMPTY_COLUMN = ".";
    private static final String COLUMN_SEPARATOR = "\t";
    private static final String ATTRIBUTE_SEPARATOR = ";";
    private static final String LINE_SEPARATOR = "\n";

    public static String convert(UniProtKBEntry entry) {
        // Note: the trailing '/t' character at the end of the line is strange, but is there to
        // conform to the
        //       way the current uniprot.org produces GFF files, as of 06/02/2019
        String lines =
                entry.getFeatures().stream()
                        .filter(UniProtGffParser::isPrintable)
                        .map(f -> convert(f, entry))
                        .collect(Collectors.joining("\t" + LINE_SEPARATOR));

        return lines + "\t";
    }

    private static String convert(UniProtKBFeature feature, UniProtKBEntry entry) {
        String accession = entry.getPrimaryAccession().getValue(); // 1: seqid
        String type = FeatureLabel.getLabelFromName(feature.getType().getName()); // 3: type
        int start = feature.getLocation().getStart().getValue(); // 4: start
        int end = feature.getLocation().getEnd().getValue(); // 5: end
        String score = EMPTY_COLUMN,
                strand = EMPTY_COLUMN,
                phase = EMPTY_COLUMN; // 6,7,8: empty score, strand and phase
        // 9: attributes
        String attributes = getFeatureAttributes(feature);

        return String.join(
                COLUMN_SEPARATOR,
                accession,
                FEATURE_SOURCE,
                type,
                String.valueOf(start),
                String.valueOf(end),
                score,
                strand,
                phase,
                attributes);
    }

    private static void gatherEvidenceAttributes(
            UniProtKBFeature feature, StringBuilder xrefs, StringBuilder attributes) {
        List<Evidence> evidences = feature.getEvidences();

        // Do we need remove duplicate evidence code, or just repeat e.g. Q8WWI5
        String evidenceCodes =
                evidences.stream()
                        .map(Evidence::getEvidenceCode)
                        .filter(Objects::nonNull)
                        .map(EvidenceCode::getCode)
                        .collect(Collectors.joining(","));
        if (!nullOrEmpty(evidenceCodes)) {
            appendAttributes("Ontology_term", evidenceCodes, attributes);
        }

        String evidenceString =
                evidences.stream().map(Evidence::toString).collect(Collectors.joining(","));
        if (!nullOrEmpty(evidenceString)) {
            appendAttributes("evidence", evidenceString, attributes);
        }

        String pubmed =
                evidences.stream()
                        .filter(evidence -> evidence.getEvidenceCrossReference() != null)
                        .filter(
                                evidence ->
                                        evidence.getEvidenceCrossReference()
                                                .getDatabase()
                                                .getName()
                                                .equals("PubMed"))
                        .map(Evidence::getEvidenceCrossReference)
                        .map(CrossReference::getId)
                        .collect(Collectors.toSet())
                        .stream()
                        // UUW pmid not in order, eg. P00550
                        // pmid duplicate Q8WWI5
                        .sorted()
                        .map(p -> "PMID:" + escape(p))
                        .collect(Collectors.joining(","));

        if (!nullOrEmpty(pubmed)) {
            ifBuilderNonEmptyThenAppend(xrefs, ",");
            xrefs.append(pubmed);
        }
    }

    private static void gatherVariantDbXref(UniProtKBFeature feature, StringBuilder dbxrefDbSNP) {
        UniprotKBFeatureType featureType = feature.getType();
        if (featureType.equals(VARIANT)) {
            String r = feature.getDescription().getValue();
            Pattern pattern = Pattern.compile("(dbSNP:\\w+)");
            Matcher match = pattern.matcher(r);
            while (match.find()) {
                ifBuilderNonEmptyThenAppend(dbxrefDbSNP, ",");
                dbxrefDbSNP.append(escape(match.group()));
            }
        }
    }

    private static void appendAttributes(String key, String value, StringBuilder builder) {
        ifBuilderNonEmptyThenAppend(builder, ATTRIBUTE_SEPARATOR);
        builder.append(key).append('=').append(value);
    }

    private static void replaceArrow(StringBuilder sb) {
        String replacement = "->";
        Pattern pattern = Pattern.compile(" -> ");
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }

    private static String getFeatureAttributes(UniProtKBFeature feature) {
        StringBuilder attributesBuilder = new StringBuilder();
        String descriptionValue = capitaliseFirstLetter(feature.getDescription().getValue());

        // id
        if (feature.hasFeatureId()) {
            String featureId = feature.getFeatureId().getValue();
            if (!nullOrEmpty(featureId)) {
                appendAttributes("ID", escape(featureId), attributesBuilder);
            }
        }

        // note
        String noteStr = createNote(feature, descriptionValue);

        // check any ending dot
        if (noteStr.length() > 0) {
            if (noteStr.charAt(noteStr.length() - 1) == '.') {
                noteStr = noteStr.substring(0, noteStr.length() - 1);
            }
            appendAttributes("Note", escape(noteStr), attributesBuilder);
        }

        // Dbxref
        StringBuilder dbxrefBuilder = new StringBuilder();

        // Dbxref - dbSNP
        gatherVariantDbXref(feature, dbxrefBuilder);

        // Dbxref - PMID
        // Ontology_term
        // evidence
        gatherEvidenceAttributes(feature, dbxrefBuilder, attributesBuilder);

        if (dbxrefBuilder.length() > 0) {
            appendAttributes("Dbxref", dbxrefBuilder.toString(), attributesBuilder);
        }

        // empty attributes
        if (attributesBuilder.length() == 0) attributesBuilder.append('.');

        return attributesBuilder.toString();
    }

    private static String createNote(UniProtKBFeature feature, String descriptionValue) {
        StringBuilder note = new StringBuilder();
        UniprotKBFeatureType featureType = feature.getType();
        if (!nullOrEmpty(descriptionValue)) {
            if (featureType.equals(VARIANT)) {
                updateNoteForVariant(descriptionValue, note);
            } else if (featureType.equals(VAR_SEQ)
                    || featureType.equals(MUTAGEN)
                    || featureType.equals(CARBOHYD)
                    || !descriptionValue.startsWith("In Ref")) {
                ifBuilderNonEmptyThenAppendSpace(note);
                note.append(descriptionValue);
                if (note.charAt(note.length() - 1) != '.') {
                    note.append(".");
                }
            }
        }

        // note - alternativeSequence
        if (feature.hasAlternativeSequence()) {
            ifBuilderNonEmptyThenAppendSpace(note);
            FTLineBuilderHelper.appendAlternativeSequenceNoFFMarking(note, feature);
            if (!(featureType.equals(MUTAGEN))) {
                // change all to arrows without space before and after
                replaceArrow(note);
            }
        }
        return note.toString();
    }

    private static void updateNoteForVariant(String descriptionValue, StringBuilder note) {
        descriptionValue = descriptionValue.replaceAll("[Ii]n dbSNP:.+$", "");
        descriptionValue = descriptionValue.replaceAll("; dbSNP:.+$", "");
        if (!nullOrEmpty(descriptionValue)) {
            ifBuilderNonEmptyThenAppendSpace(note);
            descriptionValue =
                    descriptionValue.substring(0, 1).toUpperCase() + descriptionValue.substring(1);
            note.append(descriptionValue);
            note.append(".");
        }
    }

    private static void ifBuilderNonEmptyThenAppendSpace(StringBuilder stringBuilder) {
        ifBuilderNonEmptyThenAppend(stringBuilder, " ");
    }

    private static void ifBuilderNonEmptyThenAppend(StringBuilder stringBuilder, String toAdd) {
        if (stringBuilder.length() > 0) {
            stringBuilder.append(toAdd);
        }
    }

    private static String capitaliseFirstLetter(String value) {
        if (!nullOrEmpty(value)) {
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        } else {
            return value;
        }
    }

    private static String escape(String text) {
        return text.replaceAll("%", "%25")
                .replaceAll(";", "%3B")
                .replaceAll("=", "%3D")
                .replaceAll("&", "%26")
                .replaceAll(",", "%2C");
    }

    private static boolean isPrintable(UniProtKBFeature feature) {
        return feature.getLocation().getStart().getValue() != -1
                && feature.getLocation().getEnd().getValue() != -1;
    }
}
