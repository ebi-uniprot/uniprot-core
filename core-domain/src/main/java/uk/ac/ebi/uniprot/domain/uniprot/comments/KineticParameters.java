package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;
import java.io.Serializable;

public interface KineticParameters extends Serializable {

    public List<MichaelisConstant> getMichaelisConstants();

    public List<MaximumVelocity> getMaximumVelocities();

    public Note getNote();
    public boolean hasNote();

}
