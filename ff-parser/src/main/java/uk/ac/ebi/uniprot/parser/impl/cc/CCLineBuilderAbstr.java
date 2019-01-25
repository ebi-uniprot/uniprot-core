package uk.ac.ebi.uniprot.parser.impl.cc;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeText;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.HasEvidences;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

public abstract class CCLineBuilderAbstr<T extends Comment> extends FFLineBuilderAbstr<T> implements
        FFLineBuilder<T> {
    private static final String CC_FF_MARK = "CC   -!- ";
    public final static String COMMENT_SPACE_INDENT = "         ";
    public final static String COMMENT_SPACE = "       ";
    public final static String CC_PREFIX_INDENT = "CC" + COMMENT_SPACE_INDENT;
    protected static final String NOTE = "Note=";
    protected static final String NAME = "Name=";
    protected static final String BRACKET_RIGHT = ")";
    protected static final String BCACKET_LEFT = " (";

    public CCLineBuilderAbstr() {
        super(LineType.CC, COMMENT_SPACE);
    }

    protected abstract List<String> buildCommentLines(T comment, boolean includeFFMarkings, boolean showEvidence);

    @Override
    public String buildString(T f) {
        List<String> lls = buildCommentLines(f, false, false);
        return FFLines.create(lls).toString();
    }

    @Override
    public String buildStringWithEvidence(T f) {
        List<String> lls = buildCommentLines(f, false, true);
        return FFLines.create(lls).toString();
    }


    @Override
    protected FFLine buildLine(T f, boolean showEvidence) {
        List<String> lls = buildCommentLines(f, true, showEvidence);
        return FFLines.create(lls);
    }


    //	protected String buildCommentStatus(HasCommentStatus comment ) {
//		String extra="";
//		if (comment.getCommentStatus() == CommentStatus.BY_SIMILARITY) {
//			extra += " (By similarity)";
//		} else if (comment.getCommentStatus() == CommentStatus.PROBABLE) {
//			extra += " (Probable)";
//		}  else if (comment.getCommentStatus() == CommentStatus.POTENTIAL) {
//			extra += " (Potential)";
//		}
//		return extra;
//	}
    protected StringBuilder addFlatFileMarkingsIfRequired(boolean includeFlatFileMarkings, StringBuilder sb) {
        if (includeFlatFileMarkings)
            sb.append(CC_FF_MARK);
        return sb;
    }

    protected StringBuilder addCommentTypeName(Comment comment, StringBuilder sb) {
        sb.append(comment.getCommentType().toDisplayName());
        sb.append(": ");
        return sb;
    }

    protected String buildStart(Comment comment, boolean includeFlatFileMarkings) {
        StringBuilder start = new StringBuilder();
        addFlatFileMarkingsIfRequired(includeFlatFileMarkings, start);
        start.append(comment.getCommentType().toDisplayName());
        start.append(":");

        return start.toString();

    }

    protected StringBuilder addEvidence(HasEvidences he, StringBuilder str, boolean showEvidence, String postfix) {
        if (!showEvidence)
            return str;
        String evStr = LineBuilderHelper.export(he.getEvidences());
        if (evStr.length() > 0) {
            str.append(evStr);
            str.append(postfix);
        }
        return str;
    }

    protected StringBuilder addEvidence(HasEvidences he, StringBuilder str, boolean showEvidence,
                                        String postfix, String postfixWitNoEvidence) {
        if (!showEvidence) {
            str.append(postfixWitNoEvidence);
            return str;
        }
        //str.append( EvidenceLine.export(he.getEvidenceIds()))
        String evStr = LineBuilderHelper.export(he.getEvidences());
        if (evStr.length() > 0) {
            str.append(evStr);
            str.append(postfix);
        } else {
            str.append(postfixWitNoEvidence);
        }
        return str;
    }

    protected List<String> addEvidences(List<String> lines, HasEvidences he, boolean showEvidence, String linePref) {
        if ((showEvidence) && (he.getEvidences().size() > 0)) {
            StringBuilder sb = new StringBuilder();
            List<String> lines2 = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                if (i == lines.size() - 1) {
                    sb.append(lines.get(i));
                } else {
                    lines2.add(lines.get(i));
                }
            }
            sb = addEvidences(sb, he, showEvidence);

            List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR, linePref);
            lines2.addAll(lls);
            return lines2;

        } else
            return lines;
    }

    protected String buildFreeText(FreeText depend, boolean showEvidence, String separator, String end) {
        boolean isfirst = true;
        StringBuilder sb = new StringBuilder();
        for (EvidencedValue val : depend.getTexts()) {
            if (!isfirst) {
                sb.append(separator);
                sb.append(SPACE);
            }
            sb.append(val.getValue());
            appendIfNot(sb, STOP);
            if (showEvidence) {
                sb.append(LineBuilderHelper.export(val.getEvidences()));
            }
            isfirst = false;
        }
        sb.append(end);
        return sb.toString();
    }

    protected boolean isValidNote(Note note) {
        return (note != null) && note.isValid();
    }
}
