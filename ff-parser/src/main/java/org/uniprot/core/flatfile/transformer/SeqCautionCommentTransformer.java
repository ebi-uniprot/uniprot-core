package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionType;
import org.uniprot.core.uniprotkb.comment.impl.SequenceCautionCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;

public class SeqCautionCommentTransformer implements CommentTransformer<SequenceCautionComment> {

    // Sequence=CAA57511.1; Type=Erroneous gene model prediction; Positions=421,
    // 589, 591; Note=The
    // predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205};
    private static final String SEQUENCE = "Sequence=";
    private static final String TYPE = "Type=";
    private static final String NOTE = "Note=";
    private static final String EVIDENCE = "Evidence=";
    private static final CommentType COMMENT_TYPE = CommentType.SEQUENCE_CAUTION;

    @Override
    public SequenceCautionComment transform(String annotation) {
        annotation = CommentTransformerHelper.trimCommentHeader(annotation, COMMENT_TYPE);
        return transform(COMMENT_TYPE, annotation);
    }

    @Override
    public SequenceCautionComment transform(CommentType commenType, String annotation) {
        annotation = CommentTransformerHelper.stripTrailing(annotation, ";");
        String[] tokens = annotation.split(";");
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        annotation = updateMolecule(annotation, builder);
        for (String token : tokens) {
            token = token.trim();
            if (token.startsWith(SEQUENCE)) {
                builder.sequence(token.substring(SEQUENCE.length()));
            } else if (token.startsWith(TYPE)) {
                builder.sequenceCautionType(
                        SequenceCautionType.typeOf(token.substring(TYPE.length())));
            } else if (token.startsWith(NOTE)) {
                builder.note(token.substring(NOTE.length()));
            } else if (token.startsWith(EVIDENCE)) {
                List<Evidence> evidences = new ArrayList<>();
                CommentTransformerHelper.stripEvidences(
                        token.substring(EVIDENCE.length()), evidences);
                builder.evidencesSet(evidences);
            }
        }
        return builder.build();
    }

    private String updateMolecule(String annotation, SequenceCautionCommentBuilder builder) {
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
