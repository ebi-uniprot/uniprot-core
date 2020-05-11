package org.uniprot.core.unirule;

import org.uniprot.core.Range;

import java.io.Serializable;

/** @author sahmad */
public interface SamTrigger extends Serializable {
    SamTriggerType getSamTriggerType();

    Range getExpectedHits();
}
