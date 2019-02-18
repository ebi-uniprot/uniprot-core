package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface MichaelisConstant extends HasEvidences {

    MichaelisConstantUnit NORMALIZED_UNIT = MichaelisConstantUnit.NANO_MOL;

    double getConstant();

    MichaelisConstantUnit getUnit();

    String getSubstrate();

}
