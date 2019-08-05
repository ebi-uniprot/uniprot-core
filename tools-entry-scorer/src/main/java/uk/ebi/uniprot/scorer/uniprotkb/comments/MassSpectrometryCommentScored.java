package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.evidence.EvidenceType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:46:26 To change this template use File | Settings
 * | File Templates.
 */
public class MassSpectrometryCommentScored extends CommentScoredAbstr {
    private final MassSpectrometryComment comment;

    public MassSpectrometryCommentScored(MassSpectrometryComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public MassSpectrometryCommentScored(MassSpectrometryComment copy) {
        this(copy, null);
    }

    public double score() {
        if (hasEvidence())
            return 9.0;
        else
            return 0;
    }

    private boolean hasEvidence() {
        return ScoreUtil.hasEvidence(new ArrayList<>(comment.getEvidences()), evidenceTypes);
    }
}
