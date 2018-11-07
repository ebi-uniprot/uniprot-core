package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
/**
 * 
 * @author jieluo
 * @date   18 Jan 2017
 * @time   13:19:07
 *
 */
public interface HasEvidences {
    public List<Evidence> getEvidences();
}
