package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.impl.FreeTextImpl;

import java.io.Serializable;
import java.util.*;

public class FreeTextCommentImpl extends FreeTextImpl implements FreeTextComment, Serializable {
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
    private static final long serialVersionUID = 3533544505832084104L;
    private CommentType commentType;

    private FreeTextCommentImpl() {
        super(Collections.emptyList());
    }

    public FreeTextCommentImpl(CommentType type,
                               List<EvidencedValue> texts) {
        super(texts);
        this.commentType = type;
    }

    public static boolean isFreeTextCommentType(CommentType type) {
        return VALID_COMMENT_TYPES.contains(type);
    }

    @Override
    public CommentType getCommentType() {
        return commentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FreeTextCommentImpl that = (FreeTextCommentImpl) o;
        return commentType == that.commentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), commentType);
    }
}
