package uk.ac.ebi.uniprot.parser.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;


public class MSCommentTransformer implements CommentTransformer<MassSpectrometryComment> {
  
    private static final CommentType COMMENT_TYPE = CommentType.MASS_SPECTROMETRY;

    @Override
    public MassSpectrometryComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public MassSpectrometryComment transform(CommentType type, String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");
        StringBuilder com = new StringBuilder();
        MassSpectrometryCommentBuilder builder =MassSpectrometryCommentBuilder.newInstance();
        for (int iii = 0; iii < annotation.length(); iii++) {
            char c = annotation.charAt(iii);
            if (c == ';') {
                String follow = annotation.substring(iii + 1);
                if (follow.startsWith(" Mass=") || follow.startsWith(" Note=")
                        || follow.startsWith(" Source=")
                        || follow.startsWith(" Mass_error=")
                        || follow.startsWith(" Range=")
                        || follow.startsWith(" Method=")
                        || follow.startsWith(" Evidence=")) {
                    com.append('\u1000');
                    continue;
                }
            }
            com.append(c);
        }
        annotation = com.toString();

        annotation = CommentTransformerHelper.stripTrailing(annotation, ".");
        annotation = CommentTransformerHelper.stripTrailing(annotation, ";");
        StringTokenizer st = new StringTokenizer(annotation, "\u1000");

        int indexEq;
        while (st.hasMoreTokens()) {
            String token = st.nextToken().trim();
            indexEq = token.indexOf('=');
            if (indexEq > -1) {
                if (indexEq < token.length() - 1) {

                    if (token.startsWith("Mass_error")) {
                        String mWERR = token.substring(indexEq + 1, token.length());
                        if (mWERR.length() > 0)
                        	builder.molWeightError(Double.parseDouble(mWERR));
                        continue;
                    }
                    if (token.startsWith("Mass")) {
                        String mw = token.substring(indexEq + 1, token.length());
                        builder.molWeight(Double.parseDouble(mw));
                        continue;
                    }
                    if (token.startsWith("Note")) {
                        String note = token.substring(indexEq + 1, token.length());
                        builder.note(note);
                        continue;
                    }
                    if (token.startsWith("Evidence")) {
                        String evidenceStr = token.substring(indexEq + 1, token.length());
                        if (evidenceStr.length() > 0) {
                            List<Evidence> evidences = new ArrayList<>();
                            CommentTransformerHelper.stripEvidences(evidenceStr, evidences);
                            builder.evidences(evidences);
                        
                        }
                        continue;
                    }

                    if (token.startsWith("Range")) {
                        String range = token.substring(indexEq + 1, token.length());

                        if (range.length() > 0) {
                            List<MassSpectrometryRange> ranges = getMassSpecRanges(range);
                           builder.massSpectrometryRanges(ranges);
                        }
                        continue;
                    }
                    if (token.startsWith("Method")) {
                        String method = token.substring(indexEq + 1, token.length());
                        if (method.length() > 0) {
                        	builder.massSpectrometryMethod(MassSpectrometryMethod.toType(method));
                        }
                        continue;
                    }

                    throw new IllegalArgumentException("Unknown element "
                            + token + " found in MASS SPECTROMETRY comment "
                            + annotation);
                } else {
                    throw new IllegalArgumentException(
                            "Missing value at token " + token
                                    + " found in MASS SPECTROMETRY comment "
                                    + annotation);
                }
            } else {
                throw new IllegalArgumentException("Missing \"=\" in token "
                        + token + " within line " + annotation);
            }
        }
        return builder.build();
    }

    private List<MassSpectrometryRange> getMassSpecRanges(String range) {
        StringTokenizer dashTokenizer = new StringTokenizer(range, ",");
        StringBuilder leftOvers = new StringBuilder();
        boolean openStatement = false;
        List<MassSpectrometryRange> massSpecRanges = new ArrayList<>();
     
        while (dashTokenizer.hasMoreTokens()) {
            String token = dashTokenizer.nextToken();
            String isoformId ="";
            if (openStatement) {
                String restToken = leftOvers.toString() + "," + token;
                int indexLastRightBracket = restToken.lastIndexOf(')');
                if (indexLastRightBracket > -1) {
                    isoformId =restToken.substring(0, indexLastRightBracket).trim();
                    leftOvers = new StringBuilder();
                    token = restToken.substring(indexLastRightBracket + 1);
                    openStatement = false;
                }
                if (token.length() < 1) {
                    continue;
                }
            }
            int indexDash = token.indexOf('-');
            int indexLeftBracket = token.indexOf('(');
            int indexLastRightBracket = token.lastIndexOf(')');
            int start =-1;
            int end =-1;
            if (!openStatement && indexDash > -1) {
                start =parseRangeNumber(token.substring(0, indexDash));
                if (indexLeftBracket > -1) {
                    end =parseRangeNumber(token.substring(indexDash + 1, indexLeftBracket));
                    if (indexLastRightBracket > -1) {
                        isoformId=
                                        token.substring(indexLeftBracket + 1, indexLastRightBracket).trim();
                        openStatement = false;
                    } else {
                        leftOvers.append(token.substring(indexLeftBracket + 1));
                        openStatement = true;
                    }
                } else {
                    end =parseRangeNumber(token.substring(indexDash + 1));
                }
                if (!openStatement) {
                    massSpecRanges.add (MassSpectrometryCommentBuilder.createMassSpectrometryRange(start, end, isoformId));           
                    leftOvers = new StringBuilder();
                }
            } else {
                leftOvers.append(",");
                leftOvers.append(token);
                openStatement = true;
            }
        }
        return massSpecRanges;
    }

    private int parseRangeNumber(String numberOrUnknown) {
        numberOrUnknown = numberOrUnknown.trim();
        final int numberOrUnknownLength = numberOrUnknown.length();
        if (numberOrUnknownLength < 1) {
            throw new IllegalArgumentException(
                    "Missing position in mass spectrometry range.");
        }
        return (numberOrUnknownLength == 1 && numberOrUnknown.charAt(0) == '?') ? -1
                : Integer.parseInt(numberOrUnknown);
    }
}
