package org.uniprot.core.unirule;

import java.io.Serializable;

public interface PositionalFeature extends Serializable {
    String getStart();
    String getEnd();
    boolean isGroup();
    String getValue();
    String getType();
}
