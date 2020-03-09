package org.uniprot.core.uniprot;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Keyword extends KeywordId, HasEvidences {
    KeywordCategory getCategory();
}
