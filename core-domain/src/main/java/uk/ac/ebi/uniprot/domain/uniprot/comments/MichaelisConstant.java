package uk.ac.ebi.uniprot.domain.uniprot.comments;

import uk.ac.ebi.uniprot.domain.uniprot.interfaces.HasEvidences;

public interface MichaelisConstant  extends HasEvidences {

    public static final MichaelisConstantUnit NORMALIZED_UNIT = MichaelisConstantUnit.NANO_MOL;

    public float getConstant();

    public MichaelisConstantUnit getUnit();

    public String getSubstrate();

}
