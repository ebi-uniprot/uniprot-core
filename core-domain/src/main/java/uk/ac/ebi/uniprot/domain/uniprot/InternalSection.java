package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceLine;

import java.io.Serializable;
import java.util.List;

public interface InternalSection extends Serializable {
    List<InternalLine> getInternalLines();

    List<SourceLine> getSourceLines();

    List<EvidenceLine> getEvidenceLines();
}
