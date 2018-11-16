package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.Optional;

public interface SubcellularLocation {

     SubcellularLocationValue getLocation();

     Optional<SubcellularLocationValue> getTopology();

     Optional<SubcellularLocationValue> getOrientation();

}
