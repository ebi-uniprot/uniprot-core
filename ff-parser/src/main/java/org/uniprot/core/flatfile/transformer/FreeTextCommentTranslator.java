package org.uniprot.core.flatfile.transformer;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineUtils;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.comment.builder.FreeTextCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

public class FreeTextCommentTranslator implements CommentTransformer<FreeTextComment> {
    private static final String EVIDENCE_PREFIX = "{ECO";
    private static final String EVIDENCE_POSTFIX = "}";
    private static final String COLON = ":";

    @Override
    public FreeTextComment transform(String annotation) {
        int index = annotation.indexOf(COLON);
        String type = annotation.substring(0, index);
        annotation = annotation.substring(index + 1).trim();
        CommentType commentType = CommentType.typeOf(type);
        return transform(commentType, annotation);
    }

    @Override
    public FreeTextComment transform(CommentType type, String annotation) {
        FreeTextCommentBuilder builder = new FreeTextCommentBuilder();
        buildFreeTexts(annotation, builder);
        return builder.commentType(type).build();
    }

    private void buildFreeTexts(String annotation, FreeTextCommentBuilder builder) {
        List<EvidencedValue> texts = new ArrayList<>();
        int indexPre = 0;
        int indexPost = 0;
        boolean first = true;
        do {
            indexPre = annotation.indexOf(EVIDENCE_PREFIX);
            if (indexPre == -1) break;
            indexPost = annotation.indexOf(EVIDENCE_POSTFIX, indexPre);
            if (indexPost == -1) {
                break;
            }
            String value = annotation.substring(0, indexPost + 1);
            texts.add(createCommentText(value));
            annotation = annotation.substring(indexPost + 2).trim();
        } while ((indexPost != -1) && (indexPre != -1));
        if (!annotation.isEmpty()) {
            EvidencedValue cText = createCommentText(annotation);
            if (first) {
                first = false;
                List<String> result = CcLineUtils.parseFreeText(cText.getValue());
                String newValue = result.get(1);
                String molecule = result.get(0);
                builder.molecule(molecule);
                EvidencedValueBuilder evBuilder =
                        new EvidencedValueBuilder(newValue, cText.getEvidences());

                cText = evBuilder.build();
            }
            texts.add(cText);
        }
        builder.texts(texts);
    }

    private EvidencedValue createCommentText(String value) {
        value = CommentTransformerHelper.stripTrailing(value, ".");
        List<Evidence> evidences = new ArrayList<>();
        value = CommentTransformerHelper.stripEvidences(value, evidences);
        value = CommentTransformerHelper.stripTrailing(value, ".");
        return new EvidencedValueBuilder(value, evidences).build();
    }
}
