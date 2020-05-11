package org.uniprot.core.scorer.uniprotkb.xdb;

import org.uniprot.core.scorer.uniprotkb.Consensus;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.scorer.uniprotkb.ScoreUtil;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

import java.util.List;

public class EmblScored implements HasScore {
    List<UniProtKBCrossReference> uniProtKBCrossReferences;
    private boolean isEmblSequenceDone = false;
    private boolean isEmblDone = false;
    private boolean isEmblNotAnnotatedCDSDone = false;

    private final List<EvidenceDatabase> evidenceDatabases;

    public EmblScored(
            List<UniProtKBCrossReference> uniProtKBCrossReferences,
            List<EvidenceDatabase> evidenceDatabases) {
        this.uniProtKBCrossReferences = uniProtKBCrossReferences;
        this.evidenceDatabases = evidenceDatabases;
    }

    public EmblScored(List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this(uniProtKBCrossReferences, null);
    }

    @Override
    public double score() {

        double score = 0;
        for (UniProtKBCrossReference xref : uniProtKBCrossReferences) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceDatabases)) {
                String status = xref.getProperties().get(1).getValue();
                if (status.equals("NOT_ANNOTATED_CDS")) {
                    if (!isEmblNotAnnotatedCDSDone) {
                        score += 1;
                        isEmblNotAnnotatedCDSDone = true;
                    }
                } else if (status.startsWith("ALT_")) {
                    if (!isEmblSequenceDone) {
                        score += 3;
                        isEmblSequenceDone = true;
                    }
                } else if (!status.equals("JOINED"))
                    if (!isEmblDone) {
                        score += 0.1;
                        isEmblDone = true;
                    }
            }
        }

        return score;
    }

    @Override
    public Consensus consensus() {
        return Consensus.PRESENCE;
    }
}
