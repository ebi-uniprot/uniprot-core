package org.uniprot.core.uniprotkb.evidence;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.util.Utils;

/**
 * @author jieluo
 * @date 18 Jan 2017
 * @time 13:19:07
 */
public interface HasEvidences extends Serializable {
    List<Evidence> getEvidences();

    default boolean hasEvidences() {
        return Utils.notNullNotEmpty(getEvidences());
    }
}
