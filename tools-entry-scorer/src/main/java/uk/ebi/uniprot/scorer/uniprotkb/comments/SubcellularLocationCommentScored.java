package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreStatus;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 16:15:13 To change this template use File | Settings
 * | File Templates.
 */
public class SubcellularLocationCommentScored extends CommentScoredAbstr {
    private final SubcellularLocationComment comment;

    public SubcellularLocationCommentScored(SubcellularLocationComment copy, List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public SubcellularLocationCommentScored(SubcellularLocationComment copy) {
        this(copy, null);
    }

    public double score() {
        double score = 0;
        for (SubcellularLocation loc : comment.getSubcellularLocations()) {
            if (!hasEvidence(loc))
                continue;
            // default to location test
            ScoreStatus status = getCommentScoreStatus(loc);
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
        }
        return score;
    }

    private boolean hasEvidence(SubcellularLocation loc) {
        List<Evidence> evidences = new ArrayList<>();
        if (nonNull(loc.getLocation())) {
            evidences.addAll(loc.getLocation().getEvidences());
        }
        if (nonNull(loc.getOrientation())) {
            evidences.addAll(loc.getOrientation().getEvidences());
        }
        if (nonNull(loc.getTopology())) {
            evidences.addAll(loc.getTopology().getEvidences());
        }
        return ScoreUtil.hasEvidence(evidences, evidenceTypes);
    }

    private Collection<ScoreStatus> getScoreStatusTypes(SubcellularLocationValue slocVal) {
        Collection<ScoreStatus> types = new HashSet<>(ScoreUtil.getECOStatusTypes(slocVal));
        if (types.isEmpty()) {
            types.addAll(ScoreUtil.getECOStatusTypes(getEvidences(comment)));
        }
        return types;
    }

    private List<Evidence> getEvidences(SubcellularLocationComment comment) {
        List<Evidence> evidences = new ArrayList<>();
        if (nonNull(comment.getNote())) {
            comment.getNote().getTexts().forEach(f -> evidences.addAll(f.getEvidences()));
        }
        comment.getSubcellularLocations().forEach(l -> {
            if (nonNull(l.getLocation())) {
                evidences.addAll(l.getLocation().getEvidences());
            }
            if (nonNull(l.getOrientation())) {
                evidences.addAll(l.getOrientation().getEvidences());
            }
            if (nonNull(l.getTopology())) {
                evidences.addAll(l.getTopology().getEvidences());
            }
        });
        return evidences;
    }

    private ScoreStatus getCommentScoreStatus(SubcellularLocation loc) {
        Collection<ScoreStatus> types = new HashSet<>();
        if (loc.getLocation() != null) {
            types.addAll(getScoreStatusTypes(loc.getLocation()));
        }
        if (loc.getOrientation() != null) {
            types.addAll(getScoreStatusTypes(loc.getOrientation()));
        }
        if (loc.getTopology() != null) {
            types.addAll(getScoreStatusTypes(loc.getTopology()));
        }
        if (types.isEmpty()) {
            types.add(ScoreUtil.convert(this.getDefaultEvidenceCode()));
        }
        return ScoreUtil.getScoreStatus(types);
    }
}
