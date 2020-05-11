package org.uniprot.core.scorer.uniprotkb.comments;

import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:03:04 To change this template
 * use File | Settings | File Templates.
 */
public class CautionCommentScored extends CommentScoredAbstr {

    private static final String PROBLEM_CAUTION =
            "The sequence shown here is derived from an EMBL/GenBank/DDBJ whole genome shotgun"
                    + " (WGS) entry which is preliminary data";

    private static final String ANOTHER_PROBLEM_CAUTION =
            "The sequence shown here is derived from an Ensembl automatic analysis pipeline and"
                    + " should be considered as preliminary data";

    private final FreeTextComment comment;

    public CautionCommentScored(FreeTextComment copy, List<EvidenceDatabase> evidenceDatabases) {
        super(copy.getCommentType(), evidenceDatabases);
        this.comment = copy;
    }

    public CautionCommentScored(FreeTextComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        if (hasScored()) return 3.0;
        else return 0;
    }

    private boolean hasScored() {
        List<EvidencedValue> texts = comment.getTexts();
        if (!texts.isEmpty()) {
            for (EvidencedValue text : texts) {
                if (!hasScored(text)) return false;
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean hasScored(EvidencedValue val) {
        if (val.getValue().equals(PROBLEM_CAUTION)
                || val.getValue().equals(ANOTHER_PROBLEM_CAUTION)) return false;
        return ScoreUtil.hasEvidence(val.getEvidences(), evidenceDatabases);
    }
}
