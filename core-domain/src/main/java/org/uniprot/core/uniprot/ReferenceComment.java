package org.uniprot.core.uniprot;

import org.uniprot.core.uniprot.evidence.EvidencedValue;

public interface ReferenceComment extends EvidencedValue {
    ReferenceCommentType getType();
}
