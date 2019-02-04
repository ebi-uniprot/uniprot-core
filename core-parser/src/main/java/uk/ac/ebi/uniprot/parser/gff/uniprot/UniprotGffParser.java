package uk.ac.ebi.uniprot.parser.gff.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author gqi
 *
 */
public class UniprotGffParser {

    public static final String GFF_HEADER = "##gff-version 3";
    public static final String ENTRY_PREFIX = "##sequence-region ";
    public static final String FEATRUE_SOURCE = "UniProtKB";
    public static final String EMPTY_COLUMN = ".";
    public static final String COLUMN_SEPARATOR = "\t";
    public static final String ATTRIBUTE_SEPARATE = ";";
    public static final String LINE_SEPARATOR = "\n";

    public static String getGFFheader() {
        return GFF_HEADER;
    }

    public static String getEntryHeader(UniProtEntry entry) {

        StringBuilder sb = new StringBuilder();
        sb.append(ENTRY_PREFIX).append(entry.getPrimaryAccession().getValue()).append(" 1 ")
                .append(entry.getSequence().getLength());
        return sb.toString();
    }

    public static String convert(UniProtEntry entry) {

        // should no tab in the end of each line
        String lines = entry.getFeatures().stream().filter(f -> isPrintable(f))
                .map(f -> convert(f, entry)).collect(Collectors.joining("\t" + LINE_SEPARATOR));

        StringBuilder data = new StringBuilder();
        data.append(getGFFheader()).append(LINE_SEPARATOR);
        data.append(getEntryHeader(entry)).append(LINE_SEPARATOR);
        data.append(lines);

        // should no tab in the end of each line
        data.append("\t");

        return data.toString();
    }

    public static String convert(Feature feature, UniProtEntry entry) {

        String accession = entry.getPrimaryAccession().getValue(); // 1: seqid
        String source = FEATRUE_SOURCE; // 2: source
        String type = FeatureLabel.getLabelFromName(feature.getType().getName()); // 3: type
        int start = feature.getFeatureLocation().getStart(); // 4: start
        int end = feature.getFeatureLocation().getEnd(); // 5: end
        String score = EMPTY_COLUMN, strand = EMPTY_COLUMN, phase = EMPTY_COLUMN; // 6,7,8: empty score, strand and
        // phase
        // 9: attributes
        String attributes = getFeatureAttributes(feature);

        return Stream
                .of(accession, source, type, String.valueOf(start), String.valueOf(end), score, strand, phase,
                        attributes)
                .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    public static String getFeatureAttributes(Feature feature) {

        StringBuilder attributesBuilder = new StringBuilder();

        // id
        if (feature instanceof HasFeatureId) {
            String featureId = ((HasFeatureId) feature).getFeatureId().getValue();
            if (!Strings.isNullOrEmpty(featureId)) {
                appendAttributes("ID", escape(featureId), attributesBuilder);
            }
        }

        // note
        StringBuilder note = new StringBuilder();

        /// note - isoforms
        if (feature instanceof VarSeqFeature) {
            String isoforms = getStringIsoformsVarSplicFeature((VarSeqFeature) feature);
            if (!Strings.isNullOrEmpty(isoforms)) {
                note.append(isoforms);
                note.append(".");
            }
        }

        // note - variant report
        if (feature instanceof VariantFeature) {
            String description = ((VariantFeature) feature).getVariantReport().getValue();
            description = description.replaceAll("in dbSNP:.+$", "");
            description = description.replaceAll("; dbSNP:.+$", "");
            if (!Strings.isNullOrEmpty(description)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                description = description.substring(0, 1).toUpperCase() + description.substring(1);
                note.append(description);
                note.append(".");
            }
        }

        // note - mutagen report
        if (feature instanceof MutagenFeature) {
            String description = ((MutagenFeature) feature).getMutagenReport().getValue();
            if (!Strings.isNullOrEmpty(description)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                note.append(description);
                note.append(".");
            }
        }

        // note - carbohyd report
        if (feature instanceof CarbohydFeature) {
            CarbohydFeature carbo = (CarbohydFeature) feature;
            StringBuilder sb = new StringBuilder();
            if (carbo.getCarbohydLinkType() != CarbohydLinkType.UNKNOWN) {
                sb.append(carbo.getCarbohydLinkType().getValue());
                sb.append(" ");
                sb.append(carbo.getLinkedSugar().getValue());
            }
            StringBuilder descr = FTLineBuilderHelper.getDescriptionString(feature);
            if ((sb.length() > 0) && (descr.length() > 0)) {
                sb.append("; ");
                sb.append(descr);
            }

            String description = sb.toString();

            if (!Strings.isNullOrEmpty(description)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                note.append(description);
                note.append(".");
            }
        }
        // note - description
        else if (feature instanceof HasFeatureDescription) {
            String description = ((HasFeatureDescription) feature).getFeatureDescription().getValue();
            if (!Strings.isNullOrEmpty(description)) {
                if (note.length() > 0) {
                    note.append(" ");
                }
                note.append(description);
                note.append(".");
            }
        }

        // note - alternativeSequence
        if (feature instanceof HasAlternativeSequence) {
            HasAlternativeSequence featurewithAlternativeSequence = (HasAlternativeSequence) feature;
            if (note.length() > 0) {
                note.append(" ");
            }
            FTLineBuilderHelper.addAlternativeSequence(note, featurewithAlternativeSequence, false);

            if (!(feature instanceof MutagenFeature)) {
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

    static void gatherEvidenceAttributes(Feature feature, StringBuilder xrefs, StringBuilder attributes) {

        List<Evidence> evidences = feature.getEvidences();

        // Do we need remove duplicate evidence code, or just repeat e.g. Q8WWI5
        String evidenceCodes = evidences.stream().map(Evidence::getEvidenceCode).filter(Objects::nonNull)
                .map(EvidenceCode::getCode).collect(Collectors.joining(","));
        if (evidenceCodes != null && !evidenceCodes.isEmpty()) {
            appendAttributes("Ontology_term", evidenceCodes, attributes);
        }

        String evidenceString = evidences.stream().map(Evidence::toString).collect(Collectors.joining(","));
        if (evidenceString != null && !evidenceString.isEmpty()) {
            appendAttributes("evidence", evidenceString, attributes);
        }

        String pubmed = evidences.stream().filter(evidence -> evidence.getSource() != null)
                .filter(evidence -> evidence.getSource().getDatabaseType().getName().equals("PubMed"))
                .map(e -> e.getAttribute().getValue()).collect(Collectors.toSet()).stream()
                // UUW pmid not in order, eg. P00550
                // pmid duplicate Q8WWI5
                .sorted()
                .map(p -> "PMID:" + escape(p)).collect(Collectors.joining(","));

        if (pubmed != null && !pubmed.isEmpty()) {
            if (xrefs.length() > 0) {
                xrefs.append(",");
            }
            xrefs.append(pubmed);
        }

    }

    static StringBuilder gatherVariantDbXref(Feature feature, StringBuilder dbxrefDbSNP) {

        if (feature instanceof VariantFeature) {
            VariantFeature variant = (VariantFeature) feature;
            String r = variant.getVariantReport().getValue();
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

    private static String escape(String text) {
        return text
                .replaceAll("%", "%25")
                .replaceAll(";", "%3B")
                .replaceAll("=", "%3D")
                .replaceAll("&", "%26")
                .replaceAll(",", "%2C");
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

    static String getStringIsoformsVarSplicFeature(VarSeqFeature feature) {

        StringBuilder temp = new StringBuilder();
        int numberReports = feature.getVarsplicIsoforms().size();
        if (numberReports == 0)
            return "";

        int count = 0;
        for (VarsplicIsoform isoform : feature.getVarsplicIsoforms()) {
            if (count == 0) {
                temp.append("In ");
            } else {
                if (count < numberReports - 1) {
                    temp.append(", ");
                } else {
                    temp.append(" and ");
                }
            }
            temp.append("isoform ");
            temp.append(isoform.getValue());
            count++;

        }
        return temp.toString();
    }

    private static boolean isPrintable(Feature feature) {

        FeatureLocation location = feature.getFeatureLocation();
        return location.isStartAvailable() && location.isEndAvailable();
    }
}
