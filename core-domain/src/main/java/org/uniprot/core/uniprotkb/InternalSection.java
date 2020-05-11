package org.uniprot.core.uniprotkb;

import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

import java.io.Serializable;
import java.util.List;

public interface InternalSection extends Serializable {
    List<InternalLine> getInternalLines();

    List<SourceLine> getSourceLines();

    List<EvidenceLine> getEvidenceLines();
}
