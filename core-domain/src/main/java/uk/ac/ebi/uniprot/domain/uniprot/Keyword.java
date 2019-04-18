package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.cv.keyword.KeywordCategory;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

public interface Keyword extends EvidencedValue {
    String getId();
    KeywordCategory getCategory();
}
