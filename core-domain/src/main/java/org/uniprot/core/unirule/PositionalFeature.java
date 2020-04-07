package org.uniprot.core.unirule;

import java.io.Serializable;

/** @author sahmad */
public interface PositionalFeature extends Serializable {
    String getStart();

    String getEnd();

    String getPattern();

    boolean isInGroup();

    String getValue();

    String getType();
}
