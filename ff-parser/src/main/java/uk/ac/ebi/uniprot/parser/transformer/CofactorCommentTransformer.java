package uk.ac.ebi.uniprot.parser.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;


public class CofactorCommentTransformer implements CommentTransformer<CofactorComment> {

    private static final String NOTE = "Note=";
    private static final String COLON = ":";
    private static final String SEMI_COLON = ";";
    private static final String EVIDENCE = "Evidence=";
 
    private static final CommentType COMMENT_TYPE = CommentType.COFACTOR;

    private static final String COFACTOR_NAME = "Name=";
    private static final String COFACTOR_XREF = "Xref=";

    private static final String COFACTOR_REGEX =
            "^([\\w/-]+(\\s[\\w/-]+)*:)?(\\s*Name=(\\S+( \\S+)*); Xref=\\w+:\\S+;?( Evidence=\\{.+:.+\\};)?)*(\\s*Note=.+\\.?)?";
    private static final Pattern COFACTOR_PATTERN = Pattern.compile(COFACTOR_REGEX);

    private static String extractCofactorMolecule(String molecule) {
        return molecule.substring(0, molecule.indexOf(COLON));
    }

    private static List<Cofactor> extractCofactorReferences(String xrefs) {
        List<Cofactor> cofactors = new ArrayList<>();

        try (Scanner scanner = new Scanner(xrefs)) {
            scanner.useDelimiter(COFACTOR_NAME + "|" + COFACTOR_XREF);

            while (scanner.hasNext()) {
                String name = CommentTransformerHelper.stripTrailing(scanner.next().trim(), SEMI_COLON);
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
                DBCrossReference<CofactorReferenceType> reference
                =UniProtFactory.INSTANCE.createDBCrossReference(CofactorReferenceType.typeOf(dbType), xref);
                List<Evidence> evidences = new ArrayList<>();
                
                if (evidence != null) {
                    CommentTransformerHelper.stripEvidences(evidence, evidences);
                }
                Cofactor cofactor = CofactorCommentBuilder.createCofactor(name, reference, evidences);
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
        CofactorCommentBuilder builder =  CofactorCommentBuilder.newInstance();
        if (matcher.matches()) {
            int separatorIndex;

            // extract note
            if ((separatorIndex = annotation.indexOf(NOTE)) != -1) {
            	 String noteStr = annotation.substring(separatorIndex + NOTE.length(), annotation.length());
            	builder.note(CommentFactory.INSTANCE
            			.createNote(CommentTransformerHelper.parseEvidencedValues(noteStr, true)));
              

                // remove the note from the original annotation
                annotation = annotation.substring(0, separatorIndex).trim();
            }

            // extract cofactors
            if ((separatorIndex = annotation.indexOf(COFACTOR_NAME)) != -1) {
                List<Cofactor> cofactors =
                        extractCofactorReferences(annotation.substring(separatorIndex, annotation.length()));
                builder.cofactors(cofactors);
                annotation = annotation.substring(0, separatorIndex).trim();
            }

            // extract molecule
            if (annotation.trim().length() > 0) {
            	builder.molecule(extractCofactorMolecule(annotation));
               
            }
            return builder.build();
        } else {
            throw new IllegalArgumentException("Unable to convert annotation to COFACTOR comment: " + annotation);
        }
       
    }

}
