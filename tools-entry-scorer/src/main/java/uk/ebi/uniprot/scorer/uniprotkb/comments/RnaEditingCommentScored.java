package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 16:02:58 To change this template use File | Settings
 * | File Templates.
 */
public class RnaEditingCommentScored extends CommentScoredAbstr {
    private final RnaEditingComment comment;

    public RnaEditingCommentScored(RnaEditingComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public RnaEditingCommentScored(RnaEditingComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!hasEvidence())
            return score;

        ScoreStatus status = getCommentScoreStatus();
        switch (status) {
            case EXPERIMENTAL:
                score += 9;
                break;
            case NON_EXPERIMENTAL:
                score += 3;
                break;
            default:
                break;
        }
        return score;
    }

    private ScoreStatus getCommentScoreStatus() {
        Collection<ScoreStatus> types = new HashSet<>(ScoreUtil.getECOStatusTypes(getEvidences(comment)));
        if (types.isEmpty()) {
            types.add(ScoreUtil.convert(this.getDefaultEvidenceCode()));
        }
        return ScoreUtil.getScoreStatus(types);
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>(getEvidences(comment));
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private List<Evidence> getEvidences(RnaEditingComment comment) {
        List<Evidence> evidences = new ArrayList<>();
        comment.getPositions().forEach(pos -> evidences.addAll(pos.getEvidences()));
        if(comment.hasNote() && comment.getNote().hasTexts()) {
            comment.getNote().getTexts().forEach(text -> evidences.addAll(text.getEvidences()));
        }
        return evidences;
    }
}
