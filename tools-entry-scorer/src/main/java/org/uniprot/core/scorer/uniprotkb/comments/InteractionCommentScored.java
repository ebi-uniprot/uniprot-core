package org.uniprot.core.scorer.uniprotkb.comments;

import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:43:07 To change this template
 * use File | Settings | File Templates.
 */
public class InteractionCommentScored extends CommentScoredAbstr {
    private final InteractionComment comment;

    public InteractionCommentScored(
            InteractionComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public InteractionCommentScored(InteractionComment copy) {
        this(copy, null);
    }

    public double score() {
        return (double) comment.getInteractions().size() * 3;
    }
}
