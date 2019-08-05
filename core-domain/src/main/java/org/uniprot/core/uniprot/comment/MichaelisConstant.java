package org.uniprot.core.uniprot.comment;


import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface MichaelisConstant extends HasEvidences {

    MichaelisConstantUnit NORMALIZED_UNIT = MichaelisConstantUnit.NANO_MOL;

    double getConstant();

    MichaelisConstantUnit getUnit();

    String getSubstrate();

}
