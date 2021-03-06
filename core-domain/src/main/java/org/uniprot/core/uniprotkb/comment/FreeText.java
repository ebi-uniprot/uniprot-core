package org.uniprot.core.uniprotkb.comment;

import java.io.Serializable;
import java.util.List;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

/**
 * @author jieluo
 * @date 18 Jan 2017
 * @time 13:21:10
 */
public interface FreeText extends Serializable {
    List<EvidencedValue> getTexts();

    boolean hasTexts();
}
