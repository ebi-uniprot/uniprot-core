package org.uniprot.core.uniprot.evidence;

import java.io.Serializable;
import java.util.List;

/**
 * @author jieluo
 * @date 18 Jan 2017
 * @time 13:19:07
 */
public interface HasEvidences extends Serializable {
    List<Evidence> getEvidences();

    boolean hasEvidences();
}
