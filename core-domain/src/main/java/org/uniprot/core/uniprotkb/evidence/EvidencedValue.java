package org.uniprot.core.uniprotkb.evidence;

import org.uniprot.core.Value;

public interface EvidencedValue extends Value, HasEvidences {
    String getDisplayed(String separator);
}
