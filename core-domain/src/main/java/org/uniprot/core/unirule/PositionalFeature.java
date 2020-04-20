package org.uniprot.core.unirule;

import java.io.Serializable;

import org.uniprot.core.Range;

/** @author sahmad */
public interface PositionalFeature extends Serializable {
    Range getPosition();

    String getPattern();

    boolean isInGroup();

    String getValue();

    String getType();
}
