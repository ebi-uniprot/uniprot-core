package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 16:05:38 To change this template use File | Settings
 * | File Templates.
 */
public class SimilarityCommentScored extends CommentScoredAbstr {

    private final Pattern belongsRegEx = Pattern.compile("\\b[Bb]elongs\\b");
    private final FreeTextComment comment;

    public SimilarityCommentScored(FreeTextComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public SimilarityCommentScored(FreeTextComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        score += hasScored() && hasEvidence() ? 3 : 0;
        return score;
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>();
        comment.getTexts().forEach(val -> evidences.addAll(val.getEvidences()));
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private boolean hasScored() {
        List<EvidencedValue> texts = comment.getTexts();
        if (texts.isEmpty()) {
            return false;
        } else {
            for (EvidencedValue text : texts) {
                if (belongsRegEx.matcher(text.getValue()).find())
                    return true;
            }
        }
        return false;
    }
}
