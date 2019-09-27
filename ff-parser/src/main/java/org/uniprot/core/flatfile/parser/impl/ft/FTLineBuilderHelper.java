package org.uniprot.core.flatfile.parser.impl.ft;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.LINE_LENGTH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.Position;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprot.feature.*;

import com.google.common.base.Strings;

public class FTLineBuilderHelper {
    private static final String OR_TWO_SPACES = " or ";
    private static final String OR_LEFT_SPACE = " or";
    private static final String OR_RIGHT_SPACE = "or ";
    private static final String ARROW_TWO_SPACES = " -> ";
    private static final String ARROW_LEFT_SPACE = " ->";
    private static final String ARROW_RIGHT_SPACE = "-> ";
    private static final String ARROW = "->";
    private static final String COMMA = ",";
    private static final String MISSING = "Missing";
    private static final String QUESTION_MARK = "?";
    private static final String SPACE = " ";
    public static final String FT_ID = "/FTId=";
    public static final String FT_LINE_PREFIX_2 = "FT                                ";
    public static final String SPACE_LOCATION_DESCRIPTION = "       ";
    public static final String FT_LINE_PREFIX = "FT   ";

    public static StringBuilder getDescriptionString(Feature feature) {
        StringBuilder sb = new StringBuilder();
        FeatureDescription description = feature.getDescription();
        if ((description != null) && !Strings.isNullOrEmpty(description.getValue())) {
            sb.append(description.getValue());
        }
        return sb;
    }

    public static StringBuilder getFeatureId(Feature feature, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (feature.hasFeatureId()) {
            FeatureId featureId = feature.getFeatureId();
            if ((featureId != null) && featureId.isValid(feature.getType())) {
                if (includeFFMarkup) sb.append(FT_LINE_PREFIX_2);
                sb.append(FT_ID);
                sb.append(featureId.getValue());
                sb.append(".");
            }
        }

        return sb;
    }

    public static StringBuilder buildExtra(Feature feature) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescriptionString(feature));
        boolean hasDescription = (sb.length() > 0);
        StringBuilder status = new StringBuilder();
        if (status.length() > 0) {
            if (hasDescription) sb.append(" (");
            if (hasDescription) sb.append(")");
        }
        return sb;
    }

    public static StringBuilder buildFeatureCommon(Feature feature, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup) sb.append(FT_LINE_PREFIX);
        sb.append(feature.getType());
        sb.append(SPACE);
        if (feature.getLocation() != null) {
            writeFeatureLocation(feature, sb, includeFFMarkup);
        }
        return sb;
    }

    private static boolean isValidPosition(Position position) {
        return (position.getValue() != null)
                && (position.getValue() > -1)
                && (position.getModifier() != PositionModifier.UNKNOWN);
    }

    private static void writeFeatureLocation(
            Feature feature, StringBuilder temp, boolean includeFFMarkup) {
        String start = getModifierString(feature.getLocation().getStart().getModifier(), false);
        if (isValidPosition(feature.getLocation().getStart())) {
            start += feature.getLocation().getStart().getValue();
        }

        String end = getModifierString(feature.getLocation().getEnd().getModifier(), true);
        if (isValidPosition(feature.getLocation().getEnd())) {
            end += feature.getLocation().getEnd().getValue();
        }

        int currentLength = temp.toString().length();
        int startLength = start.length();

        final int DISTANCE1 = 20;
        final int DISTANCE2 = 27;
        int lastBlank = DISTANCE1 - startLength - currentLength;
        if (includeFFMarkup) {
            for (int i = 0; i < lastBlank; i++) {
                temp.append(SPACE);
            }
        }
        temp.append(start);
        currentLength = temp.toString().length();
        int endLength = end.length();
        lastBlank = DISTANCE2 - endLength - currentLength;
        if (includeFFMarkup) {
            for (int i = 0; i < lastBlank; i++) {
                temp.append(SPACE);
            }
        } else {
            temp.append(SPACE);
        }
        temp.append(end);
    }

    private static String getModifierString(PositionModifier mod, boolean isEnd) {
        String value = "";
        switch (mod) {
            case EXACT:
                break;
            case UNKNOWN:
                value = QUESTION_MARK;
                break;
            case UNSURE:
                value = QUESTION_MARK;
                break;
            case OUTSIDE:
                if (isEnd) value = ">";
                else value = "<";
                break;
        }
        return value;
    }

    public static List<String> addAlternativeSequence(
            StringBuilder sb, Feature feature, boolean includeFFMarkings) {
        if (includeFFMarkings) return addAlternativeSequence(sb, feature);
        else return addAlternativeSequenceNoFFMarking(sb, feature);
    }

    private static List<String> addAlternativeSequenceNoFFMarking(
            StringBuilder sb, Feature feature) {
        List<String> lines = new ArrayList<>();
        if (feature.hasAlternativeSequence()) {
            AlternativeSequence altSeq = feature.getAlternativeSequence();

            if (hasAlternativeSequence(altSeq)) {
                String originalSequence = altSeq.getOriginalSequence();
                sb.append(originalSequence);

                if (feature.getType() == FeatureType.MUTAGEN) {
                    sb.append(ARROW);
                } else {
                    sb.append(ARROW_TWO_SPACES);
                }
                String joiner = getJoiner(feature.getType());
                sb.append(
                        altSeq.getAlternativeSequences().stream()
                                .collect(Collectors.joining(joiner)));
            } else {
                sb.append(MISSING);
            }
            if (sb.length() > 0) lines.add(sb.toString());
        }
        return lines;
    }

    private static String getJoiner(FeatureType type) {
        if (type == FeatureType.CONFLICT) {
            return OR_TWO_SPACES;
        } else {
            return COMMA;
        }
    }

    private static boolean hasAlternativeSequence(AlternativeSequence altSequence) {
        return (altSequence != null)
                && !Strings.isNullOrEmpty(altSequence.getOriginalSequence())
                && !altSequence.getAlternativeSequences().isEmpty();
    }

    private static List<String> addAlternativeSequence(StringBuilder sb, Feature feature) {
        List<String> lines = new ArrayList<>();
        if (feature.hasAlternativeSequence()) {
            AlternativeSequence altSeq = feature.getAlternativeSequence();
            sb = wrapAdd(lines, sb, SEPARATOR, FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
            if (hasAlternativeSequence(altSeq)) {
                sb = addOriginalSequence(sb, altSeq, feature.getType(), lines);

                boolean first = true;
                for (String atlternativeSequence : altSeq.getAlternativeSequences()) {
                    if (!first) {
                        sb = addJoinForAlternativeSequence(sb, feature.getType(), lines);
                    }
                    sb.append(atlternativeSequence);
                    sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
                    first = false;
                }
            } else {
                sb.append(MISSING);
            }
            if (sb.length() > 0) lines.add(sb.toString());
        }
        return lines;
    }

    private static boolean isLongerLineLength(StringBuilder sb, String val) {
        return (sb.length() + val.length()) > LINE_LENGTH;
    }

    private static boolean isEqualLineLength(StringBuilder sb, String val) {
        return (sb.length() + val.length()) == LINE_LENGTH;
    }

    private static StringBuilder addOriginalSequence(
            StringBuilder sb,
            AlternativeSequence altSequence,
            FeatureType type,
            List<String> lines) {
        String originalSequence = altSequence.getOriginalSequence();
        sb.append(originalSequence);
        sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);

        if (type == FeatureType.MUTAGEN) {
            if (isLongerLineLength(sb, ARROW)) {
                sb = addToLines(sb, lines);
            }
            sb.append(ARROW);
        } else {
            if (isLongerLineLength(sb, ARROW_RIGHT_SPACE)) {
                sb = addToLines(sb, lines);
                sb.append(ARROW_RIGHT_SPACE);
            } else if (isEqualLineLength(sb, ARROW_LEFT_SPACE)) {
                sb.append(ARROW_LEFT_SPACE);
                sb = addToLines(sb, lines);
            } else sb.append(ARROW_TWO_SPACES);
        }
        return sb;
    }

    private static StringBuilder addToLines(StringBuilder sb, List<String> lines) {
        lines.add(sb.toString());
        sb = new StringBuilder(FTLineBuilderHelper.FT_LINE_PREFIX_2);
        return sb;
    }

    private static StringBuilder addJoinForAlternativeSequence(
            StringBuilder sb, FeatureType type, List<String> lines) {
        if (type == FeatureType.CONFLICT) {
            if (isLongerLineLength(sb, OR_RIGHT_SPACE)) {
                sb = addToLines(sb, lines);
                sb = new StringBuilder(OR_RIGHT_SPACE);
            } else if (isEqualLineLength(sb, OR_LEFT_SPACE)) {
                sb.append(OR_LEFT_SPACE);
                sb = addToLines(sb, lines);
            } else sb.append(OR_TWO_SPACES);
        } else {
            if (isLongerLineLength(sb, COMMA)) {
                lines.add(sb.toString());
                sb = new StringBuilder(FTLineBuilderHelper.FT_LINE_PREFIX_2 + COMMA);
            } else sb.append(COMMA);
        }
        return sb;
    }

    private static StringBuilder wrapAdd(
            List<String> lines, StringBuilder sb, String sep, String prefix, int lineLength) {
        if (sb.length() <= lineLength) return sb;

        List<String> lines2 = FFLineWrapper.buildLines(sb.toString(), sep, prefix, lineLength);
        for (int i = 0; i < lines2.size(); i++) {
            if (i == (lines2.size() - 1)) {
                sb = new StringBuilder(lines2.get(i));
            } else {
                lines.add(lines2.get(i).trim());
            }
        }
        return sb;
    }
}
