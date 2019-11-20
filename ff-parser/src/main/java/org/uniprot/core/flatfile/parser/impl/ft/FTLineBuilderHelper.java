package org.uniprot.core.flatfile.parser.impl.ft;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.DASH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.LINE_LENGTH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.LineBuilderHelper;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureDescription;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureLocation;
import org.uniprot.core.uniprot.feature.FeatureType;

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
	public static final String FT_ID = "/id=\"";
	public static final String FT_LINE_PREFIX_2 = "FT                   ";
	public static final String SPACE_LOCATION_DESCRIPTION = "       ";
	public static final String FT_LINE_PREFIX = "FT   ";
	public static final int NUMBER_OF_SPACE = 19;
	public static final String COLON = ":";
	public static final String STOP = ".";
	public static final String DOUBLE_STOP = "..";
	public static final String NOTE_PREFIX = "/note=\"";
	public static final String DOUBLE_QUOTE = "\"";
	public static final String EVIDENCE_PREFIX = "/evidence=\"";

  public static StringBuilder getDescriptionString(Feature feature) {
    StringBuilder sb = new StringBuilder();
    FeatureDescription description = feature.getDescription();
    if ((description != null) && !Strings.isNullOrEmpty(description.getValue())) {
      sb.append(description.getValue());
    }
    return sb;
  }

  public static List<String> buildNote(
      Feature feature, StringBuilder extra, boolean includeFFMarkup) {
	  String extraString = replaceDoubleQuoteWithDoubleDoubleQuote(extra.toString());
//    StringBuilder note =
//        new StringBuilder();
    boolean hasAlternativeSequence = feature.hasAlternativeSequence();
    if ((extraString.length() == 0) && !hasAlternativeSequence) return Collections.emptyList();
    StringBuilder sb = new StringBuilder();
    if (includeFFMarkup) sb.append(FT_LINE_PREFIX_2);
    sb.append(NOTE_PREFIX);
    String[] seps = {SEPARATOR, DASH};
    if (!hasAlternativeSequence) {
    	sb.append(extraString);
      sb.append(DOUBLE_QUOTE);
      if (includeFFMarkup) {
        return FFLineWrapper.buildLines(
            sb.toString(), seps, FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
      } else {
        List<String> result = new ArrayList<>();
        result.add(sb.toString());
        return result;
      }
    } else {
      return addAlternativeSequence(sb, feature, includeFFMarkup, extraString);
    }
  }

  public static List<String> buildNote(Feature feature, boolean includeFFMarkup) {
    StringBuilder note = buildExtra(feature);
    return buildNote(feature, note, includeFFMarkup);
  }

  public static StringBuilder buildEvidences(List<Evidence> evidences, boolean includeFFMarkup) {
    String evidence = LineBuilderHelper.export(evidences, false);
    if (evidence.length() == 0) return new StringBuilder();
    StringBuilder sb = new StringBuilder();
    if (includeFFMarkup) sb.append(FT_LINE_PREFIX_2);
    sb.append(EVIDENCE_PREFIX).append(evidence).append(DOUBLE_QUOTE);
    return sb;
  }

  public static StringBuilder buildFeatureId(FeatureId ftId, boolean includeFFMarkup) {
    StringBuilder sb = new StringBuilder();
    if ((ftId != null) && !Strings.isNullOrEmpty(ftId.getValue())) {
      if (includeFFMarkup) sb.append(FT_LINE_PREFIX_2);
      sb.append(FT_ID);
      sb.append(ftId.getValue());
      sb.append(DOUBLE_QUOTE);
    }
    return sb;
  }

  public static StringBuilder buildExtra(Feature feature) {
    StringBuilder sb = new StringBuilder();
    sb.append(getDescriptionString(feature));
    return sb;
  }

  

  public static StringBuilder buildFeatureHeader(Feature feature, boolean includeFFMarkup) {
    StringBuilder sb = new StringBuilder();
    if (includeFFMarkup) sb.append(FT_LINE_PREFIX);
    sb.append(feature.getType());
    if (includeFFMarkup) {
      int length = feature.getType().name().length();
      int numberOfSpace = 19 - 3 - length;
      for (int i = 0; i < numberOfSpace; i++) {
        sb.append(SPACE);
      }
    } else {
      sb.append(SPACE);
    }
    if (feature.getLocation() != null) {
      sb.append(buildFeatureLocation(feature.getLocation()));
    }
    return sb;
  }

  public static String buildFeatureLocation(FeatureLocation location) {
    StringBuilder temp = new StringBuilder();
    if (!Strings.isNullOrEmpty(location.getSequence())) {
      temp.append(location.getSequence()).append(COLON);
    }
    String start = getModifierString(location.getStart().getModifier(), false);
    if ((location.getStart().getValue() > -1)
        && (location.getStart().getModifier() != PositionModifier.UNKNOWN)) {
      start += location.getStart().getValue();
    }
    String end = getModifierString(location.getEnd().getModifier(), true);
    if ((location.getEnd().getValue() > -1) && (location.getEnd().getModifier() != PositionModifier.UNKNOWN)) {
      end += location.getEnd().getValue();
    }
    temp.append(start);
    if (!start.equals(end)) {
      temp.append(DOUBLE_STOP).append(end);
    }
    return temp.toString();
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
      StringBuilder sb,
      Feature featureWithAlternativeSequence,
      boolean includeFFMarkings, String extra) {

    if (includeFFMarkings) return addAlternativeSequence(sb, featureWithAlternativeSequence, extra);
    else return addAlternativeSequenceNoFFMarking(sb, featureWithAlternativeSequence, extra);
  }

  private static List<String> addAlternativeSequenceNoFFMarking(
      StringBuilder sb, Feature featureWithAlternativeSequence, String extra) {
    List<String> lines = new ArrayList<>();
    if (hasAlternativeSequence(featureWithAlternativeSequence)) {
      String originalSequence = featureWithAlternativeSequence.getAlternativeSequence().getOriginalSequence();
      sb.append(originalSequence);
      if(featureWithAlternativeSequence.getType() ==FeatureType.MUTAGEN) {
        sb.append(ARROW);
      } else {
        sb.append(ARROW_TWO_SPACES);
      }
      String joiner = getJoiner(featureWithAlternativeSequence);
      sb.append(
          featureWithAlternativeSequence
              .getAlternativeSequence().getAlternativeSequences()
              .stream()
              .collect(Collectors.joining(joiner)));
    } else {
      sb.append(MISSING);
    }
    if(!Strings.isNullOrEmpty(extra)) {
    	sb.append(extra);
    }
    sb.append("\"");
    lines.add(sb.toString());
    return lines;
  }

  private static String getJoiner(Feature featureWithAlternativeSequence) {
	 if(featureWithAlternativeSequence.getType() ==FeatureType.CONFLICT) {
      return OR_TWO_SPACES;
    } else {
      return COMMA;
    }
  }

  private static boolean hasAlternativeSequence(
      Feature featureWithAlternativeSequence) {
    return featureWithAlternativeSequence.getAlternativeSequence().getOriginalSequence() != null
        && !featureWithAlternativeSequence.getAlternativeSequence().getOriginalSequence().equals("")
        && !featureWithAlternativeSequence.getAlternativeSequence().getAlternativeSequences().isEmpty();
  }

  private static List<String> addAlternativeSequence(
      StringBuilder sb, Feature featureWithAlternativeSequence, String extra) {
    List<String> lines = new ArrayList<>();
    sb =
        wrapAdd(
            lines,
            sb,
            SEPARATOR,
            FTLineBuilderHelper.FT_LINE_PREFIX_2,
           LINE_LENGTH);
    if (hasAlternativeSequence(featureWithAlternativeSequence)) {
      sb = addOriginalSequence(sb, featureWithAlternativeSequence, lines);

      boolean first = true;
      for (String atlternativeSequence :
          featureWithAlternativeSequence.getAlternativeSequence().getAlternativeSequences()) {
        if (!first) {
          addJoinForAlternativeSequence(sb, featureWithAlternativeSequence, lines);
        }
        sb.append(atlternativeSequence);
        sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);
        first = false;
      }
    } else {
      sb.append(MISSING);
    }
    if(!Strings.isNullOrEmpty(extra)) {
    	sb.append(extra);
    }
    sb.append("\"");
    
    sb =
            wrapAdd(
                lines,
                sb,
                SEPARATOR,
                FTLineBuilderHelper.FT_LINE_PREFIX_2,
               LINE_LENGTH);
    if (sb.length() > 0) lines.add(sb.toString());
    return lines;
  }

  private static boolean isLongerLineLength(StringBuilder sb, String val) {
    return (sb.length() + val.length()) > LINE_LENGTH;
  }

  private static boolean isEqualLineLength(StringBuilder sb, String val) {
    return (sb.length() + val.length()) == LINE_LENGTH;
  }

  private static StringBuilder addOriginalSequence(
      StringBuilder sb, Feature featureWithAlternativeSequence, List<String> lines) {
    String originalSequence = featureWithAlternativeSequence.getAlternativeSequence().getOriginalSequence();
    sb.append(originalSequence);
    sb = wrapAdd(lines, sb, "", FTLineBuilderHelper.FT_LINE_PREFIX_2, LINE_LENGTH);

    if (featureWithAlternativeSequence.getType() ==FeatureType.MUTAGEN) {
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

  private static void addJoinForAlternativeSequence(
      StringBuilder sb, Feature featureWithAlternativeSequence, List<String> lines) {
    if (featureWithAlternativeSequence.getType() ==FeatureType.CONFLICT ) {
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
  }

  private static StringBuilder wrapAdd(
      List<String> lines, StringBuilder sb, String sep, String prefix, int lineLength) {
    if (sb.length() <= lineLength) return sb;

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

  public static String replaceDoubleQuoteWithDoubleDoubleQuote(String s) {
    return s.replaceAll("\"", "\"\"");
  }
}
