package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static uk.ebi.uniprot.scorer.uniprotkb.Consensus.PRESENCE;

public abstract class CommentScoredAbstr implements CommentScored {
    private boolean isSP = false;
    private final CommentScoredTable.CommentScoredInfo info;
    private static final String BY_SIMILARITY = "(By similarity)";
    private static final String PROBABLE = "(Probable)";
    private static final String POTENTIAL = "(Potential)";
    protected final List<EvidenceType> evidenceTypes;

    public CommentScoredAbstr(CommentType type, List<EvidenceType> evidenceTypes) {
        info = CommentScoredTable.getCommentScoredInfo(type);
        this.evidenceTypes = evidenceTypes;
    }

    CommentScoredTable.CommentScoredInfo getCommentScoredInfo() {
        return info;
    }

    @Override
    public void setIsSwissProt(boolean b) {
        this.isSP = b;
    }

    @Override
    public boolean isSwissProt() {
        return isSP;
    }

    @Override
    public EvidenceCode getDefaultEvidenceCode() {
        if (isSwissProt()) {
            if (getCommentScoredInfo() != null)
                return getCommentScoredInfo().defaultCode;
            else
                return CommentScoredTable.getDefaultSwissProtEvidenceCode();
        } else
            return CommentScoredTable.getDefaultTrEMBLEvidenceCode();
    }

    @Override
    public Consensus consensus() {
        if (getCommentScoredInfo() != null) {
            return getCommentScoredInfo().consensus;
        } else {
            return PRESENCE;
        }

    }

    ScoreStatus getCommentScoreStatus(TextOnlyComment comment,
            EvidenceCode defaultCode) {
        List<CommentText> texts = comment.getTexts();
        Collection<ScoreStatus> types = new HashSet<>();
        if (!texts.isEmpty()) {
            for (CommentText text : texts) {
                types.addAll(ScoreUtil.getECOStatusTypes(text));
            }
        } else {
            types.addAll(ScoreUtil.getECOStatusTypes(comment));
        }
        if (types.isEmpty() || hasNonExperimental(comment)) {
            types.add(ScoreUtil.convert(defaultCode));
        }
        return ScoreUtil.getScoreStatus(types);
    }

    /*
     * If the annotation has evidences, the default evidence is added to the already existing ones if the text of the
     * annotation contains a non-experimental qualifier ("By similarity", "Probable, "Potential") that is not at the end
     * of the text. If the resulting list of evidences contains an EXP (ECO:0000269) evidence, then the annotation is
     * considered "experimental".
     */
    private boolean hasNonExperimental(TextOnlyComment comment) {
        List<CommentText> texts = comment.getTexts();
        boolean hasNonEx = false;

        if (!texts.isEmpty()) {
            CommentText lastText = null;
            for (int i = 0; i < texts.size(); i++) {
                CommentText text = texts.get(i);
                if (hasNonExperimental(text.getValue(), i < (texts.size() - 1)))
                    hasNonEx = true;
                lastText = text;
            }
            if (isNonExperimental(lastText)) {
                hasNonEx = false;
            }
        } else {

            if (hasNonExperimental(comment.getValue(), false)
                    && !isNonExperimental(comment))
                hasNonEx = true;
        }
        return hasNonEx;
    }

    private boolean isNonExperimental(HasCommentStatus comment) {
        return (comment.getCommentStatus() == CommentStatus.BY_SIMILARITY)
                || (comment.getCommentStatus() == CommentStatus.POTENTIAL)
                || (comment.getCommentStatus() == CommentStatus.PROBABLE);
    }

    private boolean hasNonExperimental(String description, boolean ignoreEnds) {
        String val = description;
        if (val.endsWith(".")) {
            val = val.substring(0, val.length());
        }
        if (!ignoreEnds
                && (val.endsWith(BY_SIMILARITY) || val.endsWith(POTENTIAL) || val
                        .endsWith(PROBABLE)))
            return false;
        return val.contains(BY_SIMILARITY) || val.contains(POTENTIAL) || val.contains(PROBABLE);

    }

}
