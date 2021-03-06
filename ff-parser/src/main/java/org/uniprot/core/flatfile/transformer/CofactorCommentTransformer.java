package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.impl.CofactorBuilder;
import org.uniprot.core.uniprotkb.comment.impl.CofactorCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;

public class CofactorCommentTransformer implements CommentTransformer<CofactorComment> {

    private static final String NOTE = "Note=";
    private static final String COLON = ":";
    private static final String SEMI_COLON = ";";
    private static final String EVIDENCE = "Evidence=";

    private static final CommentType COMMENT_TYPE = CommentType.COFACTOR;

    private static final String COFACTOR_NAME = "Name=";
    private static final String COFACTOR_XREF = "Xref=";

    private static final String COFACTOR_REGEX =
            "^(\\[[\\w/-]+(\\s[\\w/-]+)*\\]:)?(\\s*Name=(\\S+( \\S+)*); Xref=\\w+:\\S+;?("
                    + " Evidence=\\{.+:.+\\};)?)*(\\s*Note=.+\\.?)?";
    private static final Pattern COFACTOR_PATTERN = Pattern.compile(COFACTOR_REGEX);

    private static String extractCofactorMolecule(String molecule) {
        return molecule.substring(1, molecule.indexOf(COLON) - 1);
    }

    private static List<Cofactor> extractCofactorReferences(String xrefs) {
        List<Cofactor> cofactors = new ArrayList<>();

        try (Scanner scanner = new Scanner(xrefs)) {
            scanner.useDelimiter(COFACTOR_NAME + "|" + COFACTOR_XREF);

            while (scanner.hasNext()) {
                String name =
                        CommentTransformerHelper.stripTrailing(scanner.next().trim(), SEMI_COLON);
                String ref = scanner.next().trim();
                String evidence = null;
                if (ref.contains(EVIDENCE)) {
                    evidence = ref.substring(ref.indexOf(EVIDENCE) + EVIDENCE.length());
                    ref = ref.substring(0, ref.indexOf(EVIDENCE)).trim();
                }
                ref = CommentTransformerHelper.stripTrailing(ref, SEMI_COLON);
                int dbTypeSeperator = ref.indexOf(COLON);

                String dbType = ref.substring(0, dbTypeSeperator);
                String xref = ref.substring(dbTypeSeperator + 1, ref.length());
                CrossReference<CofactorDatabase> reference =
                        new CrossReferenceBuilder<CofactorDatabase>()
                                .database(CofactorDatabase.typeOf(dbType))
                                .id(xref)
                                .build();
                List<Evidence> evidences = new ArrayList<>();

                if (evidence != null) {
                    CommentTransformerHelper.stripEvidences(evidence, evidences);
                }
                Cofactor cofactor =
                        new CofactorBuilder()
                                .name(name)
                                .cofactorCrossReference(reference)
                                .evidencesSet(evidences)
                                .build();
                cofactors.add(cofactor);
            }
        }

        return cofactors;
    }

    @Override
    public CofactorComment transform(String annotation) {

        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public CofactorComment transform(CommentType type, String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        Matcher matcher = COFACTOR_PATTERN.matcher(annotation);
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        if (matcher.matches()) {
            int separatorIndex;

            // extract note
            if ((separatorIndex = annotation.indexOf(NOTE)) != -1) {
                String noteStr =
                        annotation.substring(separatorIndex + NOTE.length(), annotation.length());
                builder.note(
                        new NoteBuilder(
                                        CommentTransformerHelper.parseEvidencedValues(
                                                noteStr, true))
                                .build());

                // remove the note from the original annotation
                annotation = annotation.substring(0, separatorIndex).trim();
            }

            // extract cofactors
            if ((separatorIndex = annotation.indexOf(COFACTOR_NAME)) != -1) {
                List<Cofactor> cofactors =
                        extractCofactorReferences(
                                annotation.substring(separatorIndex, annotation.length()));
                builder.cofactorsSet(cofactors);
                annotation = annotation.substring(0, separatorIndex).trim();
            }

            // extract molecule
            if (annotation.trim().length() > 0) {
                builder.molecule(extractCofactorMolecule(annotation));
            }
            return builder.build();
        } else {
            throw new IllegalArgumentException(
                    "Unable to convert annotation to COFACTOR comment: " + annotation);
        }
    }
}
