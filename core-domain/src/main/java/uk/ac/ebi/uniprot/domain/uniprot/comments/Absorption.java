package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.HasEvidences;

public interface Absorption extends HasEvidences {
     int getMax();
     boolean isApproximation();
     Optional<AbsorptionNote> getNote();
  

}
