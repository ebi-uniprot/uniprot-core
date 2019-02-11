package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:05:15 To change this template use File | Settings
 * | File Templates.
 */
public class CofactorCommentScored extends CommentScoredAbstr {

    private final CofactorComment comment;

    public CofactorCommentScored(CofactorComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
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
            if (ScoreUtil.hasEvidence(cofactor.getEvidences(), evidenceTypes)) {
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
