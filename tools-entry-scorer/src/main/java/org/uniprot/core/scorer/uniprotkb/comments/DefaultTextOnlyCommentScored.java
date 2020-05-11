package org.uniprot.core.scorer.uniprotkb.comments;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.scorer.uniprotkb.ScoreStatus;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

public class DefaultTextOnlyCommentScored extends CommentScoredAbstr {
    private FreeTextComment comment;

    public DefaultTextOnlyCommentScored(
            FreeTextComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public DefaultTextOnlyCommentScored(FreeTextComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (hasEvidence()) {
            if (this.getCommentScoredInfo() == null) return 0;
            if (getCommentScoredInfo().dashed) return getCommentScoredInfo().experimentalScore;

            ScoreStatus status = getCommentScoreStatus(comment, this.getDefaultEvidenceCode());
            switch (status) {
                case EXPERIMENTAL:
                    score += getCommentScoredInfo().experimentalScore;
                    break;
                case NON_EXPERIMENTAL:
                    score += getCommentScoredInfo().similarityScore;
                    break;
                default:
                    break;
            }
        }
        return score;
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>();
        comment.getTexts().forEach(text -> evidences.addAll(text.getEvidences()));
        return ScoreUtil.hasEvidence(evidences, evidenceDatabases);
    }
}
