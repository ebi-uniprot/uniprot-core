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
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 02-Mar-2010 Time: 12:10:54 To change this template
 * use File | Settings | File Templates.
 */
public class FeatureScored implements HasScore {

    private EnumMap<UniprotKBFeatureType, FeatureScoredInfo> featureMap =
            new EnumMap<>(UniprotKBFeatureType.class);

    {
        featureMap.put(
                UniprotKBFeatureType.TRANSMEM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.INTRAMEM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                UniprotKBFeatureType.NP_BIND,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.TOPO_DOM,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                UniprotKBFeatureType.SIGNAL,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.ACT_SITE,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.BINDING,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.LIPID,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.METAL,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.SITE,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.TRANSIT,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.CA_BIND,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.REGION,
                new FeatureScoredInfo(6, 2, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.DNA_BIND,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.ZN_FING,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.NON_STD,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                UniprotKBFeatureType.VARIANT,
                new FeatureScoredInfo(2, 0, NUMBER, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.MUTAGEN,
                new FeatureScoredInfo(3, 3, NUMBER, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.CARBOHYD,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.DISULFID,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.CROSSLNK,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.PROPEP,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000305));
        featureMap.put(
                UniprotKBFeatureType.INIT_MET,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.MOD_RES,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000269));
        featureMap.put(
                UniprotKBFeatureType.MOTIF,
                new FeatureScoredInfo(3, 1, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.PEPTIDE,
                new FeatureScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000305));
        featureMap.put(
                UniprotKBFeatureType.REPEAT,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.DOMAIN,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.COILED,
                new FeatureScoredInfo(9, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.COMPBIAS,
                new FeatureScoredInfo(3, 3, PRESENCE, EvidenceCode.ECO_0000255));
        featureMap.put(
                UniprotKBFeatureType.CHAIN,
                new FeatureScoredInfo(3, 1, NUMBER, EvidenceCode.ECO_0000305));
    }

    private final UniProtKBFeature feature;
    private boolean isSP = false;

    private final List<EvidenceDatabase> evidenceDatabases;

    public FeatureScored(UniProtKBFeature copy, List<EvidenceDatabase> evidenceDatabases) {
        feature = copy;
        this.evidenceDatabases = evidenceDatabases;
    }

    public FeatureScored(UniProtKBFeature copy) {
        this(copy, null);
    }

    public void setIsSwissProt(boolean b) {
        this.isSP = b;
    }

    @Override
    public double score() {
        double score = 0;
        if (ScoreUtil.hasEvidence(feature.getEvidences(), evidenceDatabases)) {

            if (feature.getType() == UniprotKBFeatureType.COMPBIAS) {
                return 3.0;
            } else if (feature.getType() == UniprotKBFeatureType.VARIANT) {
                return 2.0;
            } else if (feature.getType() == UniprotKBFeatureType.MUTAGEN) {
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
