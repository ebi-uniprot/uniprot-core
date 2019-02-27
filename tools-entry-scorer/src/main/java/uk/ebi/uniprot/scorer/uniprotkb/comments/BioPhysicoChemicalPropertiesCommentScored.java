package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Absorption;
import uk.ac.ebi.uniprot.domain.uniprot.comment.BPCPComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 01-Mar-2010 Time: 14:38:24 To change this template use File | Settings
 * | File Templates.
 */
public class BioPhysicoChemicalPropertiesCommentScored extends CommentScoredAbstr {
    private final BPCPComment comment;

    public BioPhysicoChemicalPropertiesCommentScored(BPCPComment copy,
                                                     List<EvidenceType> evidenceTypes) {
        super(copy.getCommentType(), evidenceTypes);
        this.comment = copy;
    }

    public BioPhysicoChemicalPropertiesCommentScored(BPCPComment copy) {
        this(copy, null);
    }

    @Override
    public double score() {
        double score = 0;

        if (comment != null) {

            score += hasAbsorptionProperty(comment) ? 2 : 0;

            if (comment.getKineticParameters() != null) {
                score +=
                        comment.getKineticParameters().getMaximumVelocities().stream()
                                .filter(val -> ScoreUtil.hasEvidence(val.getEvidences(), evidenceTypes))
                                .collect(Collectors.toList()).size() * 2;

                score +=
                        comment.getKineticParameters().getMichaelisConstants().stream()
                                .filter(val -> ScoreUtil.hasEvidence(val.getEvidences(), evidenceTypes))
                                .collect(Collectors.toList()).size() * 2;

            }

            score += hasPHDependenceProperty(comment) ? 2 : 0;
            score += hasRedoxPotentialProperty(comment) ? 2 : 0;
            score += hasTemperatureDependenceProperty(comment) ? 2 : 0;
        }
        return score;
    }

    private boolean hasAbsorptionProperty(BPCPComment comment) {
        Absorption absorption = comment.getAbsorption();
        if (absorption == null) {
            return false;
        } else {
            return ScoreUtil.hasEvidence(absorption.getEvidences(), evidenceTypes);
        }
    }

    private boolean hasPHDependenceProperty(BPCPComment comment) {
        if (comment.getPhDependence() == null)
            return false;
        return ScoreUtil.hasEvidence(comment.getPhDependence().getTexts().stream()
                                             .flatMap(val -> val.getEvidences().stream())
                                             .collect(Collectors.toList()), evidenceTypes);
    }

    private boolean hasRedoxPotentialProperty(BPCPComment comment) {
        if (comment.getRedoxPotential() == null)
            return false;
        return ScoreUtil.hasEvidence(comment.getRedoxPotential().getTexts().stream()
                                             .flatMap(val -> val.getEvidences().stream())
                                             .collect(Collectors.toList()), evidenceTypes);
    }

    private boolean hasTemperatureDependenceProperty(BPCPComment comment) {
        if (comment.getTemperatureDependence() == null)
            return false;
        return ScoreUtil.hasEvidence(comment.getTemperatureDependence().getTexts().stream()
                                             .flatMap(val -> val.getEvidences().stream())
                                             .collect(Collectors.toList()), evidenceTypes);
    }
}
