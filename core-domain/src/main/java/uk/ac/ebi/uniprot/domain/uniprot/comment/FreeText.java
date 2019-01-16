package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.List;

/**
 * @author jieluo
 * @date 18 Jan 2017
 * @time 13:21:10
 */
public interface FreeText {
    List<EvidencedValue> getTexts();
}
