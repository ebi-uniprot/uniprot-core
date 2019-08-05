package uk.ebi.uniprot.scorer.uniprotkb.xdb;

import java.util.List;

import org.uniprot.core.uniprot.evidence.EvidenceType;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

import uk.ebi.uniprot.scorer.uniprotkb.Consensus;
import uk.ebi.uniprot.scorer.uniprotkb.HasScore;
import uk.ebi.uniprot.scorer.uniprotkb.ScoreUtil;

public class EmblScored  implements HasScore {
	List<UniProtDBCrossReference> xrefs;
    private boolean isEmblSequenceDone = false;
    private boolean isEmblDone = false;
    private boolean isEmblNotAnnotatedCDSDone = false;

    private final List<EvidenceType> evidenceTypes;

    public EmblScored(List<UniProtDBCrossReference> xrefs, List<EvidenceType> evidenceTypes) {
        this.xrefs = xrefs;
        this.evidenceTypes = evidenceTypes;
    }

    public EmblScored(List<UniProtDBCrossReference> xrefs) {
        this(xrefs, null);
    }

    @Override
    public double score() {

        double score = 0;
        for (UniProtDBCrossReference xref : xrefs) {
            if (ScoreUtil.hasEvidence(xref.getEvidences(), evidenceTypes)) {
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
