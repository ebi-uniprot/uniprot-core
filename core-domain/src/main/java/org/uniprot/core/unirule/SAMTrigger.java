package org.uniprot.core.unirule;

import java.io.Serializable;

/** @author sahmad */
public interface SAMTrigger extends Serializable {
    SAMTriggerType getSAMTriggerType();

    String getStart();

    String getEnd();
}
