package org.uniprot.core.scorer.uniprotkb.comments;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:46:26 To change this template
 * use File | Settings | File Templates.
 */
public class MassSpectrometryCommentScored extends CommentScoredAbstr {
    private final MassSpectrometryComment comment;

    public MassSpectrometryCommentScored(
            MassSpectrometryComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public MassSpectrometryCommentScored(MassSpectrometryComment copy) {
        this(copy, null);
    }

    public double score() {
        if (hasEvidence()) return 9.0;
        else return 0;
    }

    private boolean hasEvidence() {
        return ScoreUtil.hasEvidence(new ArrayList<>(comment.getEvidences()), evidenceDatabases);
    }
}
