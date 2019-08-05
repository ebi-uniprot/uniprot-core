package org.uniprot.core.uniprot.description;

import java.io.Serializable;

public interface Flag extends Serializable {
    FlagType getType();
}
