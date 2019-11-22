package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

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
        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        annotation = updateMolecule(annotation, builder);

        for (int iii = 0; iii < annotation.length(); iii++) {
            char c = annotation.charAt(iii);
            if (c == ';') {
                String follow = annotation.substring(iii + 1);
                if (follow.startsWith(" Mass=")
                        || follow.startsWith(" Note=")
                        || follow.startsWith(" Mass_error=")
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
                        if (mWERR.length() > 0) builder.molWeightError(Float.parseFloat(mWERR));
                        continue;
                    }
                    if (token.startsWith("Mass")) {
                        String mw = token.substring(indexEq + 1, token.length());
                        builder.molWeight(Float.parseFloat(mw));
                        continue;
                    }
                    if (token.startsWith("Method")) {
                        String method = token.substring(indexEq + 1, token.length());
                        if (method.length() > 0) {
                            builder.method(MassSpectrometryMethod.toType(method));
                        }
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

                    throw new IllegalArgumentException(
                            "Unknown element "
                                    + token
                                    + " found in MASS SPECTROMETRY comment "
                                    + annotation);
                } else {
                    throw new IllegalArgumentException(
                            "Missing value at token "
                                    + token
                                    + " found in MASS SPECTROMETRY comment "
                                    + annotation);
                }
            } else {
                throw new IllegalArgumentException(
                        "Missing \"=\" in token " + token + " within line " + annotation);
            }
        }
        return builder.build();
    }

    private String updateMolecule(String annotation, MassSpectrometryCommentBuilder builder) {
        if (annotation.startsWith("[") && annotation.contains("]")) {
            int index = annotation.indexOf("]");
            String molecule = annotation.substring(1, index);
            molecule = molecule.replaceAll("\n", " ");
            builder.molecule(molecule);
            annotation = annotation.substring(index + 2).trim();
            if (annotation.startsWith("\n")) annotation = annotation.substring(1);
            return annotation;
        }
        return annotation;
    }
}
