package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceLine;
import java.util.List;

public interface InternalSection {
    List<InternalLine> getInternalLines();

    List<SourceLine> getSourceLines();

    List<EvidenceLine> getEvidenceLines();
}
