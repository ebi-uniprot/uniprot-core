package org.uniprot.core.uniprot;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprot.evidence.EvidenceLine;

public interface InternalSection extends Serializable {
    List<InternalLine> getInternalLines();

    List<SourceLine> getSourceLines();

    List<EvidenceLine> getEvidenceLines();
}
