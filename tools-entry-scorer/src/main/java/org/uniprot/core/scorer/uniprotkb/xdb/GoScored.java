package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class GoScored implements HasScore {
    private final List<UniProtCrossReference> uniProtCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public GoScored(
            List<UniProtCrossReference> uniProtCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtCrossReferences = uniProtCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public GoScored(List<UniProtCrossReference> uniProtCrossReferences) {
        this(uniProtCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        for (UniProtCrossReference xref : uniProtCrossReferences) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceDatabases)) score += score(xref);
        }
        return score;
    }

    private double score(UniProtCrossReference xref) {

        String type = xref.getProperties().get(1).getValue();
        int index = type.indexOf(':');
        if (index != -1) type = type.substring(0, index);
        double score = 0;
        // this is the status
        switch (type) {
            case "IDA":
            case "IMP":
            case "IGI":
            case "IPI":
                score += 5;
                break;
            case "TAS":
            case "IC":
                score += 4;
                break;
            case "EXP":
            case "IEP":
            case "IGC":
            case "RCA":
            case "HDA":
            case "HMP":
            case "HGI":
                score += 3;
                break;
            case "ISS":
            case "ISO":
            case "ISA":
            case "ISM":
            case "IBA":
            case "IBD":
            case "IKR":
            case "IRD":
            case "NAS":
            case "IEA":
            case "HEP":
            case "HTP":
                score += 2;
                break;

                // all others score 0!
            default:
                score += 0;
                break;
        }

        return score;
    }

    @Override
    public Consensus consensus() {
        return Consensus.NUMBER;
    }
}
