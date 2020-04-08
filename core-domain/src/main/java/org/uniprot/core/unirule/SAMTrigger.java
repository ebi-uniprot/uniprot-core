package org.uniprot.core.unirule;

import java.io.Serializable;

import org.uniprot.core.Range;

/** @author sahmad */
public interface SAMTrigger extends Serializable {
    SAMTriggerType getSAMTriggerType();

    Range getExpectedHits();
}
