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
        return comment.getInteractions().size() * 3;
    }
}
