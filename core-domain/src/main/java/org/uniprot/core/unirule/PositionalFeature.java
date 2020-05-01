package org.uniprot.core.unirule;

import org.uniprot.core.Range;

/** @author sahmad */
public interface PositionalFeature extends RuleExceptionAnnotationType {
    Range getPosition();

    String getPattern();

    boolean isInGroup();

    String getValue();

    String getType();
}
