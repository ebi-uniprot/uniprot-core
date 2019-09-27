package org.uniprot.core.uniprot.comment;


import org.uniprot.core.uniprot.evidence.HasEvidences;
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
        return Utils.notEmpty(getSubstrate());
    }

}
