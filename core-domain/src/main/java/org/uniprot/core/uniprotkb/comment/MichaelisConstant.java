package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.uniprotkb.evidence.HasEvidences;
import org.uniprot.core.util.Utils;

public interface MichaelisConstant extends HasEvidences {

    MichaelisConstantUnit NORMALIZED_UNIT = MichaelisConstantUnit.NANO_MOL;

    double getConstant();

    MichaelisConstantUnit getUnit();

    String getSubstrate();

    default boolean hasConstant() {
        return getConstant() > 0.0d;
    }

    default boolean hasUnit() {
        return getUnit() != null;
    }

    default boolean hasSubstrate() {
        return Utils.notNullNotEmpty(getSubstrate());
    }
}
