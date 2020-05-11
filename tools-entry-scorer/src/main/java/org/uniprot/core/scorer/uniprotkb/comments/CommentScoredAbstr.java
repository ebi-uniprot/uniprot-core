package org.uniprot.core.scorer.uniprotkb.comments;

import static org.uniprot.core.scorer.uniprotkb.Consensus.PRESENCE;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.ScoreStatus;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class CommentScoredAbstr implements CommentScored {
    private boolean isSP = false;
    private final CommentScoredTable.CommentScoredInfo info;
    private static final String BY_SIMILARITY = "(By similarity)";
    private static final String PROBABLE = "(Probable)";
    private static final String POTENTIAL = "(Potential)";
    protected final List<EvidenceDatabase> evidenceDatabases;

    public CommentScoredAbstr(CommentType type, List<EvidenceDatabase> evidenceDatabases) {
        info = CommentScoredTable.getCommentScoredInfo(type);
        this.evidenceDatabases = evidenceDatabases;
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
            if (getCommentScoredInfo() != null) return getCommentScoredInfo().defaultCode;
            else return CommentScoredTable.getDefaultSwissProtEvidenceCode();
        } else return CommentScoredTable.getDefaultTrEMBLEvidenceCode();
    }

    @Override
    public Consensus consensus() {
        if (getCommentScoredInfo() != null) {
            return getCommentScoredInfo().consensus;
        } else {
            return PRESENCE;
        }
    }

    ScoreStatus getCommentScoreStatus(FreeTextComment comment, EvidenceCode defaultCode) {
        List<EvidencedValue> texts = comment.getTexts();
        Collection<ScoreStatus> types = new HashSet<>();
        if (!texts.isEmpty()) {
            for (EvidencedValue text : texts) {
                types.addAll(ScoreUtil.getECOStatusTypes(text));
            }
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
    private boolean hasNonExperimental(FreeTextComment comment) {
        List<EvidencedValue> texts = comment.getTexts();
        boolean hasNonEx = false;

        if (!texts.isEmpty()) {
            for (int i = 0; i < texts.size(); i++) {
                EvidencedValue text = texts.get(i);
                if (hasNonExperimental(text.getValue(), i < (texts.size() - 1))) {
                    hasNonEx = true;
                }
            }
        }

        return hasNonEx;
    }

    private boolean hasNonExperimental(String description, boolean ignoreEnds) {
        String val = description;
        if (val.endsWith(".")) {
            val = val.substring(0, val.length() - 1);
        }
        if (!ignoreEnds
                && (val.endsWith(BY_SIMILARITY)
                        || val.endsWith(POTENTIAL)
                        || val.endsWith(PROBABLE))) {
            return false;
        }
        return val.contains(BY_SIMILARITY) || val.contains(POTENTIAL) || val.contains(PROBABLE);
    }
}
