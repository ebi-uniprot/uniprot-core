package org.uniprot.core.uniprotkb;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface Keyword extends KeywordId, HasEvidences {
    KeywordCategory getCategory();
}
