package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface MichaelisConstant extends HasEvidences {

    public static final MichaelisConstantUnit NORMALIZED_UNIT = MichaelisConstantUnit.NANO_MOL;

    public double getConstant();

    public MichaelisConstantUnit getUnit();

    public String getSubstrate();

}
