package org.uniprot.core.uniprot.evidence;


import org.uniprot.core.Value;

public interface EvidencedValue extends Value, HasEvidences {
    String getDisplayed(String separator);
}
