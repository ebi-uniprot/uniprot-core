package uk.ac.ebi.uniprot.parser.transformer;

import uk.ac.ebi.uniprot.domain.Pair;
import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.impl.PairImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequenceHelper;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.AlternativeSequenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.feature.builder.FeatureBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

final public class FeatureTransformer {
    private static final String FTID = "/FTId=";
    private static final String BRACKET_LEFT = "(";
    private static final String SEMICOLON = ";";
    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String STOP = ".";
    private static final String MISSING = "Missing";
    private static final String LINE_END = "\n";
    private static final String IN_REF = "in Ref. ";

    public Feature transform(String annotation) {
        int index = annotation.indexOf(SPACE);
        String type = annotation.substring(0, index);
        annotation = annotation.substring(index + 1).trim();
        return transform(FeatureType.typeOf(type), annotation);
    }

    public Feature transform(FeatureType featureType, String annotation) {
        if (annotation.startsWith(featureType.getDisplayName())) {
            annotation = annotation.substring(featureType.getDisplayName().length() + 1).trim();
        }
        FeatureBuilder builder = new FeatureBuilder();
        builder.type(featureType);
        int index = annotation.indexOf(FTID);
        String ftid = null;
        if (index != -1) {
            ftid = annotation.substring(index + FTID.length());
            if (ftid.endsWith(".")) {
                ftid = ftid.substring(0, ftid.length() - 1);
            }
            annotation = annotation.substring(0, index).trim();
            if (annotation.endsWith("\n")) {
                annotation.substring(0, annotation.length() - 1).trim();
            }
        }
        if (ftid != null) {
            builder.featureId(ftid);
        }
        List<Evidence> evidences = new ArrayList<>();
        String value = CommentTransformerHelper.stripEvidences(annotation, evidences);
        builder.evidences(evidences);

        index = value.indexOf(SPACE);
        String locationStart = value.substring(0, index);
        value = value.substring(index + SPACE.length());
        index = value.indexOf(SPACE);
        String locationEnd;
        if (index != -1) {
            locationEnd = value.substring(0, index);
            value = value.substring(index + SPACE.length());
        } else {
            locationEnd = value;
            value = "";
        }
        Range featureLocation = convertFeatureLocation(locationStart, locationEnd);
        builder.location(featureLocation);
        if (value.endsWith(".")) {
            value = value.substring(0, value.length() - 1);
        }
        if (!AlternativeSequenceHelper.hasAlternativeSequence(featureType)) {
            builder.description(value);
        } else {
            Pair<String, AlternativeSequence> pair = updateFeatureDescription(featureType, value);
            builder.alternativeSequence(pair.getValue());
            builder.description(pair.getKey());
        }

        return builder.build();
    }

    private Pair<String, AlternativeSequence> updateFeatureDescription(FeatureType type, String text) {
        String description = "";
        if (type == FeatureType.CONFLICT) {
            int index = text.indexOf(BRACKET_LEFT);
            if (index == -1) {
                text = text.substring(0, text.length() - 1);
            } else {
                String reportString = text.substring(index + 1, text.length() - 1).trim();
                description = reportString;
                if (reportString.startsWith(IN_REF)) {
                    reportString = reportString.substring(IN_REF.length());
                    String[] tokens = reportString.split(", ");
                    List<String> reports = new ArrayList<>();
                    for (int i = 0; i < tokens.length - 1; i++) {
                        reports.add(tokens[i]);
                    }
                    String[] tokens2 = tokens[tokens.length - 1].split(" and ");
                    for (int i = 0; i < tokens2.length; i++) {
                        reports.add(tokens2[i]);
                    }

                } else {

                }
                text = text.substring(0, index).trim();
            }
        } else if (type == FeatureType.MUTAGEN) {
            int index = text.indexOf(": ");
            if (index == -1) {
                text = text.substring(0, text.length() - 1);
            }

            String reportString = null;
            if (text.endsWith(STOP)) {
                reportString = text.substring(index + 2, text.length() - 1);
            } else
                reportString = text.substring(index + 2, text.length());
            text = text.substring(0, index).trim();
            description = reportString;
        } else if (type == FeatureType.VARIANT) {
            int index = text.indexOf(BRACKET_LEFT);
            if (index == -1) {
                text = text.substring(0, text.length());
            } else {

                String reportString = text.substring(index + 1, text.length() - 1);
                description = reportString;
                reportString = reportString.replaceAll(LINE_END, " ");
                text = text.substring(0, index);
            }
        } else if (type == FeatureType.VAR_SEQ) {
            int index = text.indexOf(BRACKET_LEFT);
            if (index == -1) {
                text = text.substring(0, text.length());
            }
            description = text.substring(index + 1, text.length() - 1);
            text = text.replaceAll("\\(in", BRACKET_LEFT);
            String reportString = text.substring(index + 1, text.length() - 1);

            reportString = reportString.replaceAll(LINE_END, "");
            reportString = reportString.replaceAll(" and ", COMMA);
            reportString = reportString.replaceAll("isoform ", "");

            text = text.substring(0, index).trim();
        }

        if (text.equalsIgnoreCase(MISSING) || text.startsWith(MISSING)) {
            return new PairImpl<>(description, new AlternativeSequenceBuilder()
                    .original("").alternatives(Collections.emptyList()).build());
        }

        int index = text.indexOf("->");
        String alternativeSequence = text.substring(index + 2).replaceAll(LINE_END, "").replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(alternativeSequence, ",:or");
        List<String> altSeq = new ArrayList<>();
        while (st.hasMoreTokens()) {
            altSeq.add(st.nextToken().trim());
        }
        String original = text.substring(0, index).trim().replaceAll("\\s*", "");

        return new PairImpl<>(description, new AlternativeSequenceBuilder().original(original).alternatives(altSeq)
                .build());
    }

    public static Range convertFeatureLocation(String locationStart, String locationEnd) {
        Position start = convertPosition(locationStart, '<');
        Position end = convertPosition(locationEnd, '>');
        return new Range(start, end);
    }

    private static Position convertPosition(String location, char outside) {
        PositionModifier modifier = PositionModifier.EXACT;
        int pos = -1;
        String locationStart = location;
        if (locationStart == null) {
            modifier = PositionModifier.UNKOWN;
        } else if (locationStart.trim().isEmpty()) {
            modifier = PositionModifier.UNKOWN;
        } else {
            locationStart = locationStart.trim();
            char c = locationStart.charAt(0);
            if (c == '?') {

                if (locationStart.length() > 1) {
                    String val = locationStart.substring(1).trim();
                    if (val.isEmpty()) {
                        modifier = PositionModifier.UNKOWN;
                    } else {
                        int value = Integer.parseInt(val);
                        if (value == -1) {
                            modifier = PositionModifier.UNKOWN;

                        } else {
                            pos = value;
                            modifier = PositionModifier.UNSURE;
                        }
                    }

                } else {
                    modifier = PositionModifier.UNKOWN;
                }
            } else if (c == outside) {
                modifier = PositionModifier.OUTSIDE;
                if (locationStart.length() > 1) {
                    String val = locationStart.substring(1);
                    pos = Integer.parseInt(val.trim());
                }
            } else {
                modifier = PositionModifier.EXACT;
                pos = Integer.parseInt(locationStart);
            }
        }
        return new Position(pos, modifier);
    }
}
