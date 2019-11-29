package org.uniprot.core.uniprot.comment.impl;

import java.util.*;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

import javax.annotation.Nonnull;

public class FreeTextCommentImpl extends FreeTextImpl implements FreeTextComment {
    private static final Set<CommentType> VALID_COMMENT_TYPES =
            EnumSet.of(
                    CommentType.ALLERGEN,
                    CommentType.BIOTECHNOLOGY,
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
                    CommentType.TOXIC_DOSE);
    private static final long serialVersionUID = -3483334429376201154L;

    private CommentType commentType;
    private String molecule;

    // no arg constructor for JSON deserialization
    FreeTextCommentImpl() {
        super(Collections.emptyList());
    }

    public FreeTextCommentImpl(CommentType type, String molecule, List<EvidencedValue> texts) {
        super(texts);
        this.commentType = type;
        this.molecule = molecule;
    }

    public static boolean isFreeTextCommentType(CommentType type) {
        return VALID_COMMENT_TYPES.contains(type);
    }

    @Override
    public @Nonnull CommentType getCommentType() {
        return commentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FreeTextCommentImpl that = (FreeTextCommentImpl) o;
        return (commentType == that.commentType) && Objects.equals(molecule, that.molecule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), commentType, molecule);
    }

    @Override
    public @Nonnull String getMolecule() {
        return molecule;
    }
}
