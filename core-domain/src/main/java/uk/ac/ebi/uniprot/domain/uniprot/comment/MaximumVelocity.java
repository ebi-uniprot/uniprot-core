package uk.ac.ebi.uniprot.domain.uniprot.comment;


import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

public interface MaximumVelocity extends HasEvidences {
    double getVelocity();

    String getEnzyme();

    String getUnit();
}
