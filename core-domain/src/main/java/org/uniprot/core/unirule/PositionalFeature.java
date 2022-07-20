package org.uniprot.core.unirule;

import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;

/** @author sahmad */
public interface PositionalFeature extends RuleExceptionAnnotation {
    Range getPosition();

    String getPattern();

    boolean isInGroup();

    Ligand getLigand();

    LigandPart getLigandPart();

    String getDescription();

    String getType();
}
