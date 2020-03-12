package org.uniprot.core.uniprotkb.description;

import java.io.Serializable;

public interface Flag extends Serializable {
    FlagType getType();
}
