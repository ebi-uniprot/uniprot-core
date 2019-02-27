package uk.ebi.uniprot.scorer.uniprotkb;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ScoreUtil {
    public static ScoreStatus getScoreStatus(Collection<ScoreStatus> types) {
        ScoreStatus status = ScoreStatus.NO_SCORE;
        if (types.contains(ScoreStatus.EXPERIMENTAL))
            status = ScoreStatus.EXPERIMENTAL;
        else if (types.contains(ScoreStatus.NON_EXPERIMENTAL))
            status = ScoreStatus.NON_EXPERIMENTAL;
        return status;
    }

    public static Collection<ScoreStatus> getECOStatusTypes(HasEvidences he) {
        return getECOStatusTypes(he.getEvidences());
    }

    public static Collection<ScoreStatus> getECOStatusTypes(List<Evidence> evidences) {
        Collection<ScoreStatus> types = new HashSet<>();
        for (Evidence evidence : evidences) {
            types.add(convert(evidence.getEvidenceCode()));
        }
        return types;
    }

    public static ScoreStatus convert(EvidenceCode code) {
        ScoreStatus type;
        switch (code) {
            case ECO_0000269:
                type = ScoreStatus.EXPERIMENTAL;
                break;
            case ECO_0000303:
            case ECO_0000305:
            case ECO_0000250:
            case ECO_0000255:
            case ECO_0000256:
            case ECO_0000244:
            case ECO_0000213:
                type = ScoreStatus.NON_EXPERIMENTAL;
                break;
            case ECO_0000312:
            case ECO_0000313:
                type = ScoreStatus.NO_SCORE;
                break;
            default:
                type = ScoreStatus.NO_SCORE;
                break;
        }
        return type;
    }

    public static boolean hasEvidence(List<Evidence> evidences, List<EvidenceType> evidenceTypes) {
        if ((evidenceTypes == null) || (evidenceTypes.isEmpty()))
            return true;
        return evidences.stream().map(e -> e.getSource().getDatabaseType()).anyMatch(evidenceTypes::contains);
    }
}