package org.uniprot.core.scorer.uniprotkb.comments;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.uniprot.core.scorer.uniprotkb.ScoreStatus;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorComment;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:05:15 To change this template
 * use File | Settings | File Templates.
 */
public class CofactorCommentScored extends CommentScoredAbstr {

    private final CofactorComment comment;

    public CofactorCommentScored(CofactorComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public CofactorCommentScored(CofactorComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        List<Cofactor> cofactors = comment.getCofactors();
        for (Cofactor cofactor : cofactors) {
            if (ScoreUtil.hasEvidence(cofactor.getEvidences(), evidenceDatabases)) {
                ScoreStatus status = getCommentScoreStatus(cofactor);
                switch (status) {
                    case EXPERIMENTAL:
                        score += 3;
                        break;
                    case NON_EXPERIMENTAL:
                        score += 1;
                        break;
                    default:
                        break;
                }
            }
        }
        return score;
    }

    private ScoreStatus getCommentScoreStatus(Cofactor cofactor) {
        Collection<ScoreStatus> types = new HashSet<>(ScoreUtil.getECOStatusTypes(cofactor));
        if (types.isEmpty()) {
            types.add(ScoreUtil.convert(this.getDefaultEvidenceCode()));
        }
        return ScoreUtil.getScoreStatus(types);
    }
}
