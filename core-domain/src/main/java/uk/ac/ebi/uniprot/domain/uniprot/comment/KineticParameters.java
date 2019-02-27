package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.io.Serializable;
import java.util.List;

public interface KineticParameters extends Serializable {
    List<MaximumVelocity> getMaximumVelocities();

    List<MichaelisConstant> getMichaelisConstants();

    Note getNote();

    boolean hasMaximumVelocities();

    boolean hasMichaelisConstants();

    boolean hasNote();
}
