package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface KineticParameters {
     List<MaximumVelocity> getMaximumVelocities();

     List<MichaelisConstant> getMichaelisConstants();

     Note getNote();

}
