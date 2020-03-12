package org.uniprot.core.uniprotkb;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

public interface InternalSection extends Serializable {
    List<InternalLine> getInternalLines();

    List<SourceLine> getSourceLines();

    List<EvidenceLine> getEvidenceLines();
}
