package org.uniprot.core.uniprot.comment;


import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface MaximumVelocity extends HasEvidences {
    double getVelocity();

    String getEnzyme();

    String getUnit();
}
