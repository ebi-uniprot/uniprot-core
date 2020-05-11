package org.uniprot.core.scorer.uniprotkb.comments;

import static org.uniprot.core.scorer.uniprotkb.Consensus.*;

import java.util.EnumMap;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;

public class CommentScoredTable {
    private static EnumMap<CommentType, CommentScoredInfo> commentMap =
            new EnumMap<>(CommentType.class);

    static {
        commentMap.put(
                CommentType.ALLERGEN,
                new CommentScoredInfo(3, 1, PRESENCE, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.ALTERNATIVE_PRODUCTS,
                new CommentScoredInfo(3, 3, COMPLEX, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.BIOPHYSICOCHEMICAL_PROPERTIES,
                new CommentScoredInfo(2, 2, NUMBER, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.BIOTECHNOLOGY,
                new CommentScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.CATALYTIC_ACTIVITY,
                new CommentScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.CAUTION,
                new CommentScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000305, true));
        commentMap.put(
                CommentType.COFACTOR,
                new CommentScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.DEVELOPMENTAL_STAGE,
                new CommentScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.DISEASE,
                new CommentScoredInfo(9, 9, NUMBER, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.DISRUPTION_PHENOTYPE,
                new CommentScoredInfo(9, 9, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.DOMAIN,
                new CommentScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000305, false));
        commentMap.put(
                CommentType.ACTIVITY_REGULATION,
                new CommentScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.FUNCTION,
                new CommentScoredInfo(9, 3, NUMBER, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.INDUCTION,
                new CommentScoredInfo(3, 1, PRESENCE, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.INTERACTION,
                new CommentScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.MASS_SPECTROMETRY,
                new CommentScoredInfo(9, 9, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.MISCELLANEOUS,
                new CommentScoredInfo(3, 1, PRESENCE, EvidenceCode.ECO_0000305, false));
        commentMap.put(
                CommentType.PATHWAY,
                new CommentScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.PHARMACEUTICAL,
                new CommentScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.POLYMORPHISM,
                new CommentScoredInfo(9, 9, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.PTM,
                new CommentScoredInfo(6, 2, NUMBER, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.RNA_EDITING,
                new CommentScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.SEQUENCE_CAUTION,
                new CommentScoredInfo(0, 0, PRESENCE, EvidenceCode.ECO_0000303, true));
        commentMap.put(
                CommentType.SIMILARITY,
                new CommentScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000305, true));
        commentMap.put(
                CommentType.SUBCELLULAR_LOCATION,
                new CommentScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.SUBUNIT,
                new CommentScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269, false));
        commentMap.put(
                CommentType.TISSUE_SPECIFICITY,
                new CommentScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.TOXIC_DOSE,
                new CommentScoredInfo(9, 9, PRESENCE, EvidenceCode.ECO_0000269, true));
        commentMap.put(
                CommentType.WEBRESOURCE,
                new CommentScoredInfo(1, 1, NUMBER, EvidenceCode.ECO_0000269, true));
    }

    public static EvidenceCode getDefaultTrEMBLEvidenceCode() {
        return EvidenceCode.ECO_0000256;
    }

    public static EvidenceCode getDefaultSwissProtEvidenceCode() {
        return EvidenceCode.ECO_0000269;
    }

    public static CommentScoredInfo getCommentScoredInfo(CommentType type) {
        return commentMap.get(type);
    }

    public static class CommentScoredInfo {
        double experimentalScore;
        double similarityScore;
        Consensus consensus;
        EvidenceCode defaultCode;
        boolean dashed;

        private CommentScoredInfo(
                double experimentalScore,
                double similarityScore,
                Consensus consensus,
                EvidenceCode defaultCode,
                boolean dashed) {
            this.experimentalScore = experimentalScore;
            this.similarityScore = similarityScore;
            this.consensus = consensus;
            this.defaultCode = defaultCode;
            this.dashed = dashed;
        }
    }
}
