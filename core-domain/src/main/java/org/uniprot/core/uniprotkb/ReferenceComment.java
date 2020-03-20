package org.uniprot.core.uniprotkb;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

public interface ReferenceComment extends EvidencedValue {
    ReferenceCommentType getType();
}
