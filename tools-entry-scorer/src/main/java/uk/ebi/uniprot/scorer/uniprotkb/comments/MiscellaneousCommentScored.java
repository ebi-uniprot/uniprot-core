package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 15:48:00 To change this template use File | Settings
 * | File Templates.
 */
public class MiscellaneousCommentScored extends CommentScoredAbstr {
    private final FreeTextComment comment;
    private static final Collection<String> IGNORED = new TreeSet<>();

    static {
        IGNORED.add("The sequence shown here is derived from an EMBL/GenBank/DDBJ third party annotation (TPA) entry");
    }

    public MiscellaneousCommentScored(FreeTextComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public MiscellaneousCommentScored(FreeTextComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;
        if (!hasScored() || !hasEvidence())
            return score;

        ScoreStatus status = getCommentScoreStatus(comment, this.getDefaultEvidenceCode());
        switch (status) {
            case EXPERIMENTAL:
                score += 3;
                break;
            case NON_EXPERIMENTAL:
                score += 1;
                break;
            default:
                break;

        }
        return score;
    }

    private boolean hasEvidence() {
        List<Evidence> evidences = new ArrayList<>();
        comment.getTexts().forEach(text -> evidences.addAll(text.getEvidences()));
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private boolean hasScored() {
        List<EvidencedValue> texts = comment.getTexts();
        if (!texts.isEmpty()) {
            for (EvidencedValue text : texts) {
                if (IGNORED.contains(text.getValue()))
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * public class MiscellaneousCommentScored extends CommentScoredAbstr { private final MiscellaneousComment comment ;
     * private static Collection<String> IGNORED = new TreeSet<String>(); static { IGNORED.add( "The sequence shown here
     * is derived from an EMBL/GenBank/DDBJ third party annotation (TPA) entry"); }; public
     * MiscellaneousCommentScored(MiscellaneousComment copy) { super(copy.getCommentType()); this.comment = copy; }
     *
     *
     * public double score() { double score = 0; if(!hasScored()) return score; ScoreStatus status =
     * getCommentScoreStatus(comment, this.getDefaultEvidenceCode()); switch (status) {
     *
     * case EXPERIMENTAL: score += 3; break; case NON_EXPERIMENTAL: score += 1; break; default: break;
     *
     *
     * } return score; } private boolean hasScored(){ List<CommentText > texts = comment.getTexts();
     * if(!texts.isEmpty()){ for(CommentText text: texts){ if(IGNORED.contains(text.getValue())) return false; } return
     * true; }else{ return !IGNORED.contains(comment.getValue()); } }
     *
     */
}
