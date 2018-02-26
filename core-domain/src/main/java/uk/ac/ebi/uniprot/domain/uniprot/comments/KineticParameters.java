package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.List;
import java.util.Optional;

public interface KineticParameters {
     List<MaximumVelocity> getMaximumVelocities();

     List<MichaelisConstant> getMichaelisConstants();

     Optional<KPNote> getNote();

}
