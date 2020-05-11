package org.uniprot.core.flatfile.transformer;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import com.google.common.base.Strings;

import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.uniprotkb.feature.AlternativeSequenceHelper;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 18 Nov 2019
 */
public class FeatureTransformer {
    private static final String FTID = "/id=\"";
    private static final String NOTE = "/note=\"";
    private static final String EVIDENCE = "/evidence=\"";
    private static final String SPACE = " ";
    private static final String BRACKET_LEFT = "(";
    private static final String COMMA = ",";
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

    private Map.Entry<String, String> extractToken(String annotation, String tokenKey) {
        int index = annotation.indexOf(tokenKey);
        String token = "";
        if (index != -1) {
            token = annotation.substring(index + tokenKey.length());
            if (token.endsWith("\"")) {
                token = token.substring(0, token.length() - 1);
            }
            annotation = annotation.substring(0, index).trim();
            if (annotation.endsWith("\n")) {
                annotation = annotation.substring(0, annotation.length() - 1).trim();
            }
        }
        return new AbstractMap.SimpleEntry<>(annotation, token);
    }

    public Feature transform(FeatureType featureType, String annotation) {
        if (annotation.startsWith(featureType.name())) {
            annotation = annotation.substring(featureType.name().length() + 1).trim();
        }
        Map.Entry<String, String> entry = extractToken(annotation, FTID);
        annotation = entry.getKey();
        String ftid = entry.getValue();

        FeatureBuilder builder = new FeatureBuilder();
        builder.type(featureType);

        if (!Strings.isNullOrEmpty(ftid)) {
            builder.featureId(ftid);
        }
        entry = extractToken(annotation, EVIDENCE);
        annotation = entry.getKey();
        String evidence = entry.getValue();
        if (!Strings.isNullOrEmpty(evidence)) {
            String[] evIds = evidence.split(", ");
            builder.evidencesSet(
                    Arrays.stream(evIds)
                            .map(val -> parseEvidenceLine(val.trim()))
                            .collect(Collectors.toList()));
        }

        entry = extractToken(annotation, NOTE);
        annotation = entry.getKey();
        String note = entry.getValue();
        if (!Strings.isNullOrEmpty(note)) {
            String text = note.trim();
            if (!AlternativeSequenceHelper.hasAlternativeSequence(featureType)) {
                builder.description(text);
            } else {
                Pair<String, AlternativeSequence> result =
                        updateFeatureDescription(featureType, text);
                builder.alternativeSequence(result.getValue());
                builder.description(result.getKey());
            }
        }
        String sequence = "";
        String[] sToken = annotation.split(":");
        if (sToken.length == 2) {
            sequence = sToken[0];
            annotation = sToken[1];
        }
        String[] token = annotation.split("\\.\\.");
        String locationStart = token[0];
        String locationEnd = token[0];
        if (token.length == 2) {
            locationEnd = token[1];
        }
        builder.location(createFeatureLocation(locationStart, locationEnd, sequence));

        return builder.build();
    }

    private Pair<String, AlternativeSequence> updateFeatureDescription(
            FeatureType type, String text) {
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
            } else reportString = text.substring(index + 2, text.length());
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
            return new PairImpl<>(
                    description,
                    new AlternativeSequenceBuilder()
                            .original("")
                            .alternativeSequencesSet(Collections.emptyList())
                            .build());
        }

        int index = text.indexOf("->");
        String alternativeSequence =
                text.substring(index + 2).replaceAll(LINE_END, "").replaceAll(" ", "");
        StringTokenizer st = new StringTokenizer(alternativeSequence, ",:or");
        List<String> altSeq = new ArrayList<>();
        while (st.hasMoreTokens()) {
            altSeq.add(st.nextToken().trim());
        }
        String original = text.substring(0, index).trim().replaceAll("\\s*", "");

        return new PairImpl<>(
                description,
                new AlternativeSequenceBuilder()
                        .original(original)
                        .alternativeSequencesSet(altSeq)
                        .build());
    }

    public static FeatureLocation createFeatureLocation(
            String locationStart, String locationEnd, String sequence) {
        Position start = convertPosition(locationStart, '<');
        Position end = convertPosition(locationEnd, '>');
        return new FeatureLocation(
                sequence, start.getValue(), end.getValue(), start.getModifier(), end.getModifier());
    }

    private static Position convertPosition(String location, char outside) {
        PositionModifier modifier = PositionModifier.EXACT;
        int pos = -1;
        String locationStart = location;
        if (locationStart == null) {
            modifier = PositionModifier.UNKNOWN;
        } else if (locationStart.trim().isEmpty()) {
            modifier = PositionModifier.UNKNOWN;
        } else {
            locationStart = locationStart.trim();
            char c = locationStart.charAt(0);
            if (c == '?') {

                if (locationStart.length() > 1) {
                    String val = locationStart.substring(1).trim();
                    if (val.isEmpty()) {
                        modifier = PositionModifier.UNKNOWN;
                    } else {
                        int value = Integer.parseInt(val);
                        if (value == -1) {
                            modifier = PositionModifier.UNKNOWN;

                        } else {
                            pos = value;
                            modifier = PositionModifier.UNSURE;
                        }
                    }

                } else {
                    modifier = PositionModifier.UNKNOWN;
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
