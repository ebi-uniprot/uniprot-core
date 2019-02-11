package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:43:07 To change this template use File | Settings
 * | File Templates.
 */
public class InteractionCommentScored extends CommentScoredAbstr {
    private final InteractionComment comment;

    public InteractionCommentScored(InteractionComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public InteractionCommentScored(InteractionComment copy) {
        this(copy, null);
    }

    public double score() {
        // TODO: 11/02/19 New model doesn't have interaction evidences ... what should we do here?
        return 0;

        // code for old model
//        double score = 0;
//        score +=
//                comment.getInteractions()
//                        .stream().filter(val -> ScoreUtil.hasEvidence(val.getEvidenceIds(), evidenceTypes))
//                        .collect(Collectors.toList()).size() * 3;
//
//        return score;
    }
}
