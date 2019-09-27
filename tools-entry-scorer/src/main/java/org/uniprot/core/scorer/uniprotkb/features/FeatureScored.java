package org.uniprot.core.scorer.uniprotkb.features;

import static org.uniprot.core.scorer.uniprotkb.Consensus.NUMBER;
import static org.uniprot.core.scorer.uniprotkb.Consensus.PRESENCE;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreStatus;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 02-Mar-2010 Time: 12:10:54 To change this template
 * use File | Settings | File Templates.
 */
public class FeatureScored implements HasScore {

    private EnumMap<FeatureType, FeatureScoredInfo> featureMap = new EnumMap<>(FeatureType.class);

    {
        featureMap.put(
                FeatureType.TRANSMEM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.INTRAMEM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                FeatureType.NP_BIND,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.TOPO_DOM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                FeatureType.SIGNAL,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.ACT_SITE,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.BINDING,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.LIPID, new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.METAL, new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.SITE, new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.TRANSIT,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.CA_BIND,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.REGION,
                new FeatureScoredInfo(6, 2, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.DNA_BIND,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.ZN_FING,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.NON_STD,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                FeatureType.VARIANT, new FeatureScoredInfo(2, 0, NUMBER, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.MUTAGEN, new FeatureScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.CARBOHYD,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.DISULFID,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.CROSSLNK,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.PROPEP,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                FeatureType.INIT_MET,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.MOD_RES,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                FeatureType.MOTIF, new FeatureScoredInfo(3, 1, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.PEPTIDE, new FeatureScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000305));
        featureMap.put(
                FeatureType.REPEAT,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.DOMAIN,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.COILED,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.COMPBIAS,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                FeatureType.CHAIN, new FeatureScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000305));
    }

    private final Feature feature;
    private boolean isSP = false;

    private final List<EvidenceType> evidenceTypes;

    public FeatureScored(Feature copy, List<EvidenceType> evidenceTypes) {
        feature = copy;
        this.evidenceTypes = evidenceTypes;
    }

    public FeatureScored(Feature copy) {
        this(copy, null);
    }

    public void setIsSwissProt(boolean b) {
        this.isSP = b;
    }

    @Override
    public double score() {
        double score = 0;
        if (ScoreUtil.hasEvidence(feature.getEvidences(), evidenceTypes)) {

            if (feature.getType() == FeatureType.COMPBIAS) {
                return 3.0;
            } else if (feature.getType() == FeatureType.VARIANT) {
                return 2.0;
            } else if (feature.getType() == FeatureType.MUTAGEN) {
                return 3.0;
            }
            FeatureScoredInfo info = featureMap.get(feature.getType());
            if (info != null) {
                ScoreStatus scoreStatus = getScoreStatus(info);
                switch (scoreStatus) {
                    case EXPERIMENTAL:
                        score += info.experimentalScore;
                        break;
                    case NON_EXPERIMENTAL:
                        score += info.similarityScore;
                        break;
                    case NO_SCORE:
                        break;
                }
            }
        }
        return score;
    }

    private ScoreStatus getScoreStatus(FeatureScoredInfo info) {
        Collection<ScoreStatus> types = ScoreUtil.getECOStatusTypes(feature);
        if (types.isEmpty()) {
            types.add(getDefaultStatusType(info));
        }
        return ScoreUtil.getScoreStatus(types);
    }

    private ScoreStatus getDefaultStatusType(FeatureScoredInfo info) {
        if (!isSP) return ScoreStatus.NO_SCORE;
        else return ScoreUtil.convert(info.defaultCode);
    }

    @Override
    public Consensus consensus() {
        FeatureScoredInfo info = featureMap.get(feature.getType());

        if (info != null) {
            return info.consensus;
        } else {
            return PRESENCE;
        }
    }

    private class FeatureScoredInfo {
        double experimentalScore;
        double similarityScore;
        Consensus consensus;
        EvidenceCode defaultCode;

        private FeatureScoredInfo(
                double experimentalScore,
                double similarityScore,
                Consensus consensus,
                EvidenceCode defaultCode) {
            this.experimentalScore = experimentalScore;
            this.similarityScore = similarityScore;
            this.consensus = consensus;
            this.defaultCode = defaultCode;
        }
    }
}
