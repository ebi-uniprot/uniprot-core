package org.uniprot.core.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class GoScored implements HasScore {
    private final List<UniProtkbCrossReference> uniProtkbCrossReferences;
    private final List<EvidenceDatabase> evidenceDatabases;

    public GoScored(
            List<UniProtkbCrossReference> uniProtkbCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtkbCrossReferences = uniProtkbCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public GoScored(List<UniProtkbCrossReference> uniProtkbCrossReferences) {
        this(uniProtkbCrossReferences, null);
    }

    @Override
    public double score() {
        double score = 0;
        for (UniProtkbCrossReference xref : uniProtkbCrossReferences) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceDatabases)) score += score(xref);
        }
        return score;
    }

    private double score(UniProtkbCrossReference xref) {

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
