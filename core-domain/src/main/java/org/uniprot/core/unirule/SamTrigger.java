package org.uniprot.core.unirule;

import java.io.Serializable;

import org.uniprot.core.Range;

/** @author sahmad */
public interface SamTrigger extends Serializable {
    SamTriggerType getSamTriggerType();

    Range getExpectedHits();
}
