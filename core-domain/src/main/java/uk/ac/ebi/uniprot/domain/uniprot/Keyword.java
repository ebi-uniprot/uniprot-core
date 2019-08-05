package uk.ac.ebi.uniprot.domain.uniprot;

import org.uniprot.core.cv.keyword.KeywordCategory;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

public interface Keyword extends EvidencedValue {
    String getId();
    KeywordCategory getCategory();
}
