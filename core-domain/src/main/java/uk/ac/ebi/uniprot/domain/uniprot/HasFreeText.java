package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

/**
 * 
 * @author jieluo
 * @date   18 Jan 2017
 * @time   13:21:10
 *
 */
public interface HasFreeText {
    List<EvidencedValue> getTexts();
}
