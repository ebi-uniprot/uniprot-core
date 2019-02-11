package uk.ebi.uniprot.scorer.uniprotkb.comments;


import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;

import java.util.List;

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
