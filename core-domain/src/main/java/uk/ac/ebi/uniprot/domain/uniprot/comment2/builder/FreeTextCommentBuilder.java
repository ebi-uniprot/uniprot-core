package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;


import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.FreeTextCommentImpl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class FreeTextCommentBuilder implements CommentBuilder<FreeTextCommentBuilder, FreeTextComment> {
    private static final Set<CommentType> VALID_COMMENT_TYPES =
            EnumSet.of(CommentType.ALLERGEN, CommentType.BIOTECHNOLOGY,
                       CommentType.CATALYTIC_ACTIVITY,
                       CommentType.CAUTION,
                       CommentType.DEVELOPMENTAL_STAGE,
                       CommentType.DISRUPTION_PHENOTYPE,
                       CommentType.DOMAIN,
                       CommentType.ACTIVITY_REGULATION,
                       CommentType.FUNCTION,
                       CommentType.INDUCTION,
                       CommentType.INDUCTION,
                       CommentType.MISCELLANEOUS,
                       CommentType.PATHWAY,
                       CommentType.PHARMACEUTICAL,
                       CommentType.POLYMORPHISM,
                       CommentType.PTM,
                       CommentType.SIMILARITY,
                       CommentType.SUBUNIT,
                       CommentType.TISSUE_SPECIFICITY,
                       CommentType.TOXIC_DOSE
            );
    private CommentType commentType;
    private List<EvidencedValue> texts = new ArrayList<>();

    @Override
    public FreeTextComment build() {
        if (!isFreeTextCommentType(commentType)) {
            throw new IllegalArgumentException(commentType + " is not free text comment");
        }
        return new FreeTextCommentImpl(commentType, texts);
    }

    @Override
    public FreeTextCommentBuilder from(FreeTextComment instance) {
        return new FreeTextCommentBuilder()
                .commentType(instance.getCommentType())
                .texts(instance.getTexts());
    }

    public FreeTextCommentBuilder commentType(CommentType commentType) {
        this.commentType = commentType;
        return this;
    }

    public FreeTextCommentBuilder texts(List<EvidencedValue> texts) {
        this.texts.addAll(texts);
        return this;
    }

    public FreeTextCommentBuilder addText(EvidencedValue text) {
        this.texts.add(text);
        return this;
    }

    private static boolean isFreeTextCommentType(CommentType type) {
        return VALID_COMMENT_TYPES.contains(type);
    }
}
