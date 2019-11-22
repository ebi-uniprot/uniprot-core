package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.LINE_LENGTH;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_COMA;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPS;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;

import com.google.common.base.Strings;

/**
 * @author jieluo CC -!- MASS SPECTROMETRY: Mass=514.2; Method=Electrospray; Range=51-54, |CC 71-74,
 *     91-94, 132-135, 148-151; Note=The measured mass is that of |CC RPGW-amide;
 *     Source=PubMed:10799681;
 */
public class CCMassSpecCommentLineBuilder extends CCLineBuilderAbstr<MassSpectrometryComment> {

    private static final String EVIDENCE = "Evidence=";
    private static final String METHOD = "Method=";
    private static final String MASS_ERROR = "Mass_error=";
    private static final String MASS = "Mass=";

    @Override
    protected List<String> buildCommentLines(
            MassSpectrometryComment comment,
            boolean includeFlatFileMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder("");
        if (includeFlatFileMarkings) {
            addFlatFileMarkingsIfRequired(includeFlatFileMarkings, sb);
        }
        if (includeCommentType) {
            sb.append(comment.getCommentType().toDisplayName());
            sb.append(": ");
        }
        addMolecule(comment, sb, true);
        sb.append(MASS);
        sb.append(getSigDig(comment.getMolWeight()));
        if ((comment.getMolWeightError() != null)
                && Math.abs(comment.getMolWeightError()) > 10 * Float.MIN_VALUE) {
            sb.append(SEPARATOR_SEMICOLON);
            sb.append(MASS_ERROR);

            sb.append(getSigDig(comment.getMolWeightError()));
        }
        //		if(comment.getNote().isPresent()) {
        sb.append(SEPARATOR_SEMICOLON);
        sb.append(METHOD);
        sb.append(comment.getMethod().getValue());
        sb.append(SEMICOLON);
        //	}
        if (!Strings.isNullOrEmpty(comment.getNote())) {
            sb.append(SPACE);
            sb.append(NOTE);
            sb.append(comment.getNote());
            sb.append(SEMICOLON);
        }
        if (!comment.getEvidences().isEmpty())
            if ((comment.getEvidences() != null) && (comment.getEvidences().size() > 0)) {
                sb.append(SPACE);
                sb.append(EVIDENCE);
                sb.append("{");
                for (int i = 0; i < comment.getEvidences().size(); i++) {
                    if (i > 0) sb.append(SEPARATOR_COMA);

                    sb.append(comment.getEvidences().get(i).getValue());
                }
                sb.append("}");
                sb.append(SEMICOLON);
            }
        if (includeFlatFileMarkings) {
            lines.addAll(
                    FFLineWrapper.buildLines(sb.toString(), SEPS, this.linePrefix, LINE_LENGTH));
        } else {
            lines.add(sb.toString());
        }
        return lines;
    }

    public static String getSigDig(Float number) {
        String temp = number.toString();
        if (temp.indexOf(".") > 0) {
            while (temp.endsWith("0")) {
                temp = temp.substring(0, temp.length() - 1);
            }
            if (temp.endsWith(".")) {
                temp = temp.substring(0, temp.length() - 1);
            }
        }
        return temp;
    }
}
