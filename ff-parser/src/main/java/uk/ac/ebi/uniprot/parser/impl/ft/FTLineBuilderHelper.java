package uk.ac.ebi.uniprot.parser.impl.ft;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureDescription;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.HasAlternativeSequence;
import uk.ac.ebi.uniprot.domain.feature.HasFeatureId;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;


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

    public static StringBuilder getDescriptionString( Feature feature) {
        StringBuilder sb = new StringBuilder();
        FeatureDescription description =feature.getDescription();
        if((description !=null) && !description.getValue().isEmpty()){
           sb.append(description.getValue());
        }
        return sb;
    }

    public static StringBuilder getFeatureId(Feature feature,
            boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (feature instanceof HasFeatureId) {
            HasFeatureId featureWithFeatureId = (HasFeatureId) feature;
            // System.out.println("ha entrado");
            if (featureWithFeatureId.getFeatureId() != null
                    && !featureWithFeatureId.getFeatureId().getValue().equals("")) {
                if (includeFFMarkup)
                    sb.append(FT_LINE_PREFIX_2);
                sb.append(FT_ID);
                sb.append(featureWithFeatureId.getFeatureId().getValue());
                sb.append(".");

            }
        }
        return sb;

    }

    public static StringBuilder buildExtra(Feature feature) {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescriptionString(feature));
        boolean hasDescription = (sb.length() > 0);
        StringBuilder status =new StringBuilder();
        if (status.length() > 0) {
            if (hasDescription)
                sb.append(" (");
            if (hasDescription)
                sb.append(")");
        }
        return sb;
    }


    public static StringBuilder buildFeatureCommon(Feature feature, boolean includeFFMarkup) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkup)
            sb.append(FT_LINE_PREFIX);
        sb.append(feature.getType());
        sb.append(SPACE);
        if (feature.getFeatureLocation() != null) {
            writeFeatureLocation(feature, sb, includeFFMarkup);
        }
        return sb;
    }

    private static void writeFeatureLocation(Feature feature,
            StringBuilder temp, boolean includeFFMarkup) {
        String start = getModifierString(feature.getFeatureLocation().getStartModifier(), false);
        if ((feature.getFeatureLocation().getStart() > -1)
                && (feature.getFeatureLocation().getStartModifier() != FeatureLocationModifier.UNKOWN)) {
            start += feature.getFeatureLocation().getStart();
        }
        String end = getModifierString(feature.getFeatureLocation().getEndModifier(), true);
        if ((feature.getFeatureLocation().getEnd() > -1)
                && (feature.getFeatureLocation().getEndModifier() != FeatureLocationModifier.UNKOWN)) {
            end += feature.getFeatureLocation().getEnd();
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

    private static String getModifierString(FeatureLocationModifier mod, boolean isEnd) {
        String value = "";
        switch (mod) {
            case EXACT:
                break;
            case UNKOWN:
                value = QUESTION_MARK;
                break;
            case UNSURE:
                value = QUESTION_MARK;
                break;
            case OUTSIDE_KNOWN_SEQUENCE:
                if (isEnd)
                    value = ">";
                else
                    value = "<";
                break;
        }
        return value;
    }

    public static List<String> addAlternativeSequence(StringBuilder sb,
            Feature feature,
            boolean includeFFMarkings) {
        if (includeFFMarkings)
            return addAlternativeSequence(sb, feature);
        else
            return addAlternativeSequenceNoFFMarking(sb, feature);

    }

    private static List<String> addAlternativeSequenceNoFFMarking(StringBuilder sb,
            Feature feature) {
    	   List<String> lines = new ArrayList<>();
   
    	if(feature instanceof HasAlternativeSequence) {
    		HasAlternativeSequence featureWithAlternativeSequence = (HasAlternativeSequence) feature;
        if (hasAlternativeSequence(featureWithAlternativeSequence)) {
            String originalSequence = featureWithAlternativeSequence
                    .getOriginalSequence().getValue();
            sb.append(originalSequence);

            if (feature instanceof MutagenFeature) {
                sb.append(ARROW);
            } else {
                sb.append(ARROW_TWO_SPACES);
            }
            String joiner = getJoiner(feature);
            sb.append(featureWithAlternativeSequence.getAlternativeSequences()
                    .stream().map(altSeq -> altSeq.getValue()).collect(Collectors.joining(joiner)));
        } else {
            sb.append(MISSING);
        }
        if (sb.length() > 0)
            lines.add(sb.toString());
    	}
        return lines;
        
    }

    private static String getJoiner(Feature featureWithAlternativeSequence) {
        if (featureWithAlternativeSequence instanceof ConflictFeature) {
            return OR_TWO_SPACES;
        } else {
            return COMMA;
        }
    }

    private static boolean hasAlternativeSequence(HasAlternativeSequence featureWithAlternativeSequence) {
    		
        return featureWithAlternativeSequence.getOriginalSequence() != null
                && !featureWithAlternativeSequence.getOriginalSequence()
                        .getValue().equals("")
                && (featureWithAlternativeSequence.getAlternativeSequences()
                        .size() > 0);
    }

    private static List<String> addAlternativeSequence(StringBuilder sb,
            Feature feature) {
        List<String> lines = new ArrayList<>();
     	if(feature instanceof HasAlternativeSequence) {
    		HasAlternativeSequence featureWithAlternativeSequence = (HasAlternativeSequence) feature;
        sb = wrapAdd(lines, sb, SEPARATOR, FTLineBuilderHelper.FT_LINE_PREFIX_2,
               LINE_LENGTH);
        if (hasAlternativeSequence(featureWithAlternativeSequence)) {
            sb = addOriginalSequence(sb, featureWithAlternativeSequence, lines);

            boolean first = true;
            for (FeatureSequence atlternativeSequence : featureWithAlternativeSequence
                    .getAlternativeSequences()) {
                if (!first) {
                    sb = addJoinForAlternativeSequence(sb, featureWithAlternativeSequence, lines);
                }
                sb.append(atlternativeSequence.getValue());
                sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
                first = false;
            }
        } else {
            sb.append(MISSING);
        }
        if (sb.length() > 0)
            lines.add(sb.toString());
     	}
        return lines;
    }

    private static boolean isLongerLineLength(StringBuilder sb, String val) {
        return (sb.length() + val.length()) > LINE_LENGTH;
    }

    private static boolean isEqualLineLength(StringBuilder sb, String val) {
        return (sb.length() + val.length()) == LINE_LENGTH;
    }

    private static StringBuilder addOriginalSequence(StringBuilder sb,
            HasAlternativeSequence featureWithAlternativeSequence,
            List<String> lines) {
        String originalSequence = featureWithAlternativeSequence
                .getOriginalSequence().getValue();
        sb.append(originalSequence);
        sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);

        if (featureWithAlternativeSequence instanceof MutagenFeature) {
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
            } else
                sb.append(ARROW_TWO_SPACES);
        }
        return sb;
    }

    private static StringBuilder addToLines(StringBuilder sb, List<String> lines) {
        lines.add(sb.toString());
        sb = new StringBuilder(FTLineBuilderHelper.FT_LINE_PREFIX_2);
        return sb;
    }

    private static StringBuilder addJoinForAlternativeSequence(StringBuilder sb,
            HasAlternativeSequence featureWithAlternativeSequence,
            List<String> lines) {
        if (featureWithAlternativeSequence instanceof ConflictFeature) {
            if (isLongerLineLength(sb, OR_RIGHT_SPACE)) {
                sb = addToLines(sb, lines);
                sb = new StringBuilder(OR_RIGHT_SPACE);
            } else if (isEqualLineLength(sb, OR_LEFT_SPACE)) {
                sb.append(OR_LEFT_SPACE);
                sb = addToLines(sb, lines);
            } else
                sb.append(OR_TWO_SPACES);
        } else {
            if (isLongerLineLength(sb, COMMA)) {
                lines.add(sb.toString());
                sb = new StringBuilder(FTLineBuilderHelper.FT_LINE_PREFIX_2 + COMMA);
            } else
                sb.append(COMMA);
        }
        return sb;
    }

    private static StringBuilder wrapAdd(List<String> lines, StringBuilder sb, String sep, String prefix,
            int lineLength) {
        if (sb.length() <= lineLength)
            return sb;

        List<String> lines2 = FFLineWrapper.buildLines(sb.toString(), sep, prefix, lineLength);
        for (int i = 0; i < lines2.size(); i++) {
            if (i == (lines2.size() - 1)) {
                sb = new StringBuilder(lines2.get(i));
            } else {
                lines.add(lines2.get(i));
            }
        }
        return sb;
    }

}
