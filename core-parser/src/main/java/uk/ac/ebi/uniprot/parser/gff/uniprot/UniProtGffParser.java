package uk.ac.ebi.uniprot.parser.gff.uniprot;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.parser.impl.ft.FTLineBuilderHelper;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static uk.ac.ebi.uniprot.common.Utils.nullOrEmpty;
import static uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType.*;

/**
 * @author gqi
 */
public class UniProtGffParser {
    private static final String GFF_HEADER = "##gff-version 3";
    private static final String ENTRY_PREFIX = "##sequence-region ";
    private static final String FEATURE_SOURCE = "UniProtKB";
    private static final String EMPTY_COLUMN = ".";
    private static final String COLUMN_SEPARATOR = "\t";
    private static final String ATTRIBUTE_SEPARATE = ";";
    private static final String LINE_SEPARATOR = "\n";

    public static String getGFFheader() {
        return GFF_HEADER;
    }

    public static String getEntryHeader(UniProtEntry entry) {
        return ENTRY_PREFIX + entry.getPrimaryAccession().getValue() + " 1 " +
                entry.getSequence().getLength();
    }

    public static String convert(UniProtEntry entry) {
        String lines = entry.getFeatures().stream().filter(UniProtGffParser::isPrintable)
                .map(f -> convert(f, entry)).collect(Collectors.joining("\t" + LINE_SEPARATOR));

        return getGFFheader() + LINE_SEPARATOR +
                getEntryHeader(entry) + LINE_SEPARATOR +
                lines + "\t";
    }

    public static String convert(Feature feature, UniProtEntry entry) {
        String accession = entry.getPrimaryAccession().getValue(); // 1: seqid
        String type = FeatureLabel.getLabelFromName(feature.getType().getName()); // 3: type
        int start = feature.getLocation().getStart().getValue(); // 4: start
        int end = feature.getLocation().getEnd().getValue(); // 5: end
        String score = EMPTY_COLUMN, strand = EMPTY_COLUMN, phase = EMPTY_COLUMN; // 6,7,8: empty score, strand and
        // phase
        // 9: attributes
        String attributes = getFeatureAttributes(feature);

        return String.join(COLUMN_SEPARATOR, accession, FEATURE_SOURCE, type, String.valueOf(start), String
                                   .valueOf(end), score, strand, phase,
                           attributes);
    }

    public static String getFeatureAttributes(Feature feature) {
        StringBuilder attributesBuilder = new StringBuilder();
        FeatureType featureType = feature.getType();
        String descriptionValue = capitaliseFirstLetter(feature.getDescription().getValue());

        // id
        if (feature.hasFeatureId()) {
            String featureId = feature.getFeatureId().getValue();
            if (!nullOrEmpty(featureId)) {
                appendAttributes("ID", escape(featureId), attributesBuilder);
            }
        }

        // note
        StringBuilder note = new StringBuilder();
        boolean descriptionAddedToNote = false;

        /// note - isoforms
        if (featureType.equals(VAR_SEQ)) {
            String isoforms = capitaliseFirstLetter(feature.getDescription().getValue().trim());
            if (!nullOrEmpty(isoforms)) {
                note.append(isoforms);
                note.append(".");
                descriptionAddedToNote = true;
            }
        }

        // note - variant report
        if (featureType.equals(VARIANT)) {
            String description = descriptionValue;
            description = description.replaceAll("[Ii]n dbSNP:.+$", "");
            description = description.replaceAll("; dbSNP:.+$", "");
            descriptionValue = description;
            if (!nullOrEmpty(description)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                description = description.substring(0, 1).toUpperCase() + description.substring(1);
                note.append(description);
                note.append(".");
                descriptionAddedToNote = true;
            }
        }

        // note - mutagen report
        if (featureType.equals(MUTAGEN)) {
            if (!nullOrEmpty(descriptionValue)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                note.append(descriptionValue);
                note.append(".");
                descriptionAddedToNote = true;
            }
        }

        // note - carbohyd report
        if (featureType.equals(CARBOHYD)) {
            if (!nullOrEmpty(descriptionValue)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                note.append(descriptionValue);
                note.append(".");
                descriptionAddedToNote = true;
            }
        }

        // note - description
        else if (!nullOrEmpty(descriptionValue) && !descriptionValue.startsWith("In Ref") && !descriptionAddedToNote) {
            if (note.length() > 0) {
                note.append(" ");
            }
            note.append(descriptionValue);
            note.append(".");
        }

        // note - alternativeSequence
        if (feature.hasAlternativeSequence()) {
            if (note.length() > 0) {
                note.append(" ");
            }
            FTLineBuilderHelper.addAlternativeSequence(note, feature, false);
            if (!(featureType.equals(MUTAGEN))) {
                // change all to arrows without space before and after
                replaceArrow(note);
            }
        }

        String noteStr = note.toString();

        // check any ending dot
        if (noteStr != null && noteStr.length() > 0 && noteStr.charAt(noteStr.length() - 1) == '.') {
            noteStr = noteStr.substring(0, noteStr.length() - 1);
        }

        if (noteStr.length() > 0) {
            appendAttributes("Note", escape(noteStr), attributesBuilder);
        }

        // Dbxref
        StringBuilder dbxref = new StringBuilder();

        // Dbxref - dbSNP
        gatherVariantDbXref(feature, dbxref);

        // Dbxref - PMID
        // Ontology_term
        // evidence
        gatherEvidenceAttributes(feature, dbxref, attributesBuilder);

        if (dbxref.length() > 0) {
            appendAttributes("Dbxref", dbxref.toString(), attributesBuilder);
        }

        // empty attributes
        if (attributesBuilder.length() == 0)
            attributesBuilder.append('.');

        return attributesBuilder.toString();
    }

    private static String capitaliseFirstLetter(String value) {
        if (!nullOrEmpty(value)) {
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        } else {
            return value;
        }
    }

    static void gatherEvidenceAttributes(Feature feature, StringBuilder xrefs, StringBuilder attributes) {
        List<Evidence> evidences = feature.getEvidences();

        // Do we need remove duplicate evidence code, or just repeat e.g. Q8WWI5
        String evidenceCodes = evidences.stream()
                .map(Evidence::getEvidenceCode)
                .filter(Objects::nonNull)
                .map(EvidenceCode::getCode)
                .collect(Collectors.joining(","));
        if (!nullOrEmpty(evidenceCodes)) {
            appendAttributes("Ontology_term", evidenceCodes, attributes);
        }

        String evidenceString = evidences.stream().map(Evidence::toString).collect(Collectors.joining(","));
        if (!nullOrEmpty(evidenceString)) {
            appendAttributes("evidence", evidenceString, attributes);
        }

        String pubmed = evidences.stream()
                .filter(evidence -> evidence.getSource() != null)
                .filter(evidence -> evidence.getSource().getDatabaseType().getName().equals("PubMed"))
                .map(Evidence::getSource)
                .map(DBCrossReference::getId)
                .collect(Collectors.toSet())
                .stream()
                // UUW pmid not in order, eg. P00550
                // pmid duplicate Q8WWI5
                .sorted()
                .map(p -> "PMID:" + escape(p)).collect(Collectors.joining(","));

        if (!nullOrEmpty(pubmed)) {
            if (xrefs.length() > 0) {
                xrefs.append(",");
            }
            xrefs.append(pubmed);
        }
    }

    static StringBuilder gatherVariantDbXref(Feature feature, StringBuilder dbxrefDbSNP) {
        FeatureType featureType = feature.getType();
        if (featureType.equals(VARIANT)) {
            String r = feature.getDescription().getValue();
            Pattern pattern = Pattern.compile("(dbSNP:\\w+)");
            Matcher match = pattern.matcher(r);
            while (match.find()) {
                if (dbxrefDbSNP.length() > 0) {
                    dbxrefDbSNP.append(",");
                }
                dbxrefDbSNP.append(escape(match.group()));
            }
        }
        return dbxrefDbSNP;
    }

    static void appendAttributes(String key, String value, StringBuilder builder) {
        if (builder.length() > 0) {
            builder.append(ATTRIBUTE_SEPARATE);
        }
        builder.append(key).append('=').append(value);
    }

    static void replaceArrow(StringBuilder sb) {
        String replacement = "->";
        Pattern pattern = Pattern.compile(" -> ");
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }

    static String getStringIsoformsVarSplicFeature(Feature feature) {
//        StringBuilder result = new StringBuilder("Not implemented ");
        return capitaliseFirstLetter(feature.getDescription().getValue().trim());
//        int numberReports = feature.getVarsplicIsoforms().size();
//        if (numberReports == 0)
//            return "";
//
//        int count = 0;
//        for (VarsplicIsoform isoform : feature.getVarsplicIsoforms()) {
//            if (count == 0) {
//                result.append("In ");
//            } else {
//                if (count < numberReports - 1) {
//                    result.append(", ");
//                } else {
//                    result.append(" and ");
//                }
//            }
//            result.append("isoform ");
//            result.append(isoform.getValue());
//            count++;
//
//        }
//        return result.toString();
    }

    private static String escape(String text) {
        return text
                .replaceAll("%", "%25")
                .replaceAll(";", "%3B")
                .replaceAll("=", "%3D")
                .replaceAll("&", "%26")
                .replaceAll(",", "%2C");
    }

    private static boolean isPrintable(Feature feature) {
        return feature.getLocation().getStart().getValue() != -1 &&
                feature.getLocation().getEnd().getValue() != -1;
    }
}
