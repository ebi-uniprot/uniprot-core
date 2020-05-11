package org.uniprot.core.scorer.uniprotkb.comments;

import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:08:03 To change this template
 * use File | Settings | File Templates.
 */
public class DiseaseCommentScored extends CommentScoredAbstr {

    private final DiseaseComment comment;

    public DiseaseCommentScored(DiseaseComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public DiseaseCommentScored(DiseaseComment copy) {
        this(copy, null);
    }

    public double score() {
        if (hasEvidence()) return 9.0;
        else return 0;
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>();
        if (comment.hasDefinedDisease()) {
            evidences.addAll(comment.getDisease().getEvidences());
        }
        return ScoreUtil.hasEvidence(evidences, evidenceDatabases);
    }
}
