package org.uniprot.core.scorer.uniprotkb.comments;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:08:03 To change this template use File | Settings
 * | File Templates.
 */
public class DiseaseCommentScored extends CommentScoredAbstr {

    private final DiseaseComment comment;

    public DiseaseCommentScored(DiseaseComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public DiseaseCommentScored(DiseaseComment copy) {
        this(copy, null);
    }

    public double score() {
        if (hasEvidence())
            return 9.0;
        else
            return 0;
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>();
        if(comment.hasDefinedDisease()) {
            evidences.addAll(comment.getDisease().getEvidences());
        }
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }
}
