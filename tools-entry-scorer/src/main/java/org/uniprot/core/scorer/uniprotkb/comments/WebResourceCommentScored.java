package org.uniprot.core.scorer.uniprotkb.comments;


import java.util.List;

import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.evidence.EvidenceType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 17:21:42 To change this template use File | Settings
 * | File Templates.
 */
public class WebResourceCommentScored extends CommentScoredAbstr {
    @SuppressWarnings("unused")
    private final WebResourceComment comment;

    public WebResourceCommentScored(WebResourceComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public WebResourceCommentScored(WebResourceComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        return 1.0;
    }
}
