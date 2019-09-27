package org.uniprot.core.uniprot;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public interface Keyword extends EvidencedValue {
    String getId();

    KeywordCategory getCategory();
}
