package uk.ac.ebi.uniprot.domain.uniprot.comments;

import java.util.Optional;

public interface SubcellularLocation {

     SubcellularLocationValue getLocation();

     Optional<SubcellularLocationValue> getTopology();

     Optional<SubcellularLocationValue> getOrientation();

}
