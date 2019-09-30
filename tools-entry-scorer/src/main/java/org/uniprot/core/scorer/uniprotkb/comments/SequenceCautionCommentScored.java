package org.uniprot.core.scorer.uniprotkb.comments;

import java.util.List;

import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.evidence.EvidenceType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 16:04:13 To change this template
 * use File | Settings | File Templates.
 */
public class SequenceCautionCommentScored extends CommentScoredAbstr {
    @SuppressWarnings("unused")
    private final SequenceCautionComment comment;

    public SequenceCautionCommentScored(
            SequenceCautionComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public SequenceCautionCommentScored(SequenceCautionComment copy) {
        this(copy, null);
    }

    public double score() {
        return 0;
    }
}
