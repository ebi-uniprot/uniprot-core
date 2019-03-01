package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class CatalyticActivityCommentScored extends CommentScoredAbstr {
    private final CatalyticActivityComment comment;

    public CatalyticActivityCommentScored(CatalyticActivityComment copy,
                                                    List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public CatalyticActivityCommentScored(CatalyticActivityComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (hasEvidence()) {
            if (this.getCommentScoredInfo() == null)
                return 0;
            if (getCommentScoredInfo().dashed)
                return getCommentScoredInfo().experimentalScore;
            ScoreStatus status = getScoreStatus(comment,
                                                this.getDefaultEvidenceCode());
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

    private ScoreStatus getScoreStatus(CatalyticActivityComment comment,
                                       EvidenceCode defaultCode) {
        Collection<ScoreStatus> types = new HashSet<>();
        for (Evidence evidence : getEvidences(comment)) {
            types.add(ScoreUtil.convert(evidence.getEvidenceCode()));
        }
        if (types.isEmpty()) {
            types.add(ScoreUtil.convert(defaultCode));
        }
        return ScoreUtil.getScoreStatus(types);
    }

    private List<Evidence> getEvidences(CatalyticActivityComment comment) {
        List<Evidence> evidences = new ArrayList<>();
        comment.getPhysiologicalReactions().forEach(c -> evidences.addAll(c.getEvidences()));
        evidences.addAll(comment.getReaction().getEvidences());
        return evidences;
    }

    private boolean hasEvidence() {
        return ScoreUtil.hasEvidence(getEvidences(comment), evidenceTypes);
    }
}
