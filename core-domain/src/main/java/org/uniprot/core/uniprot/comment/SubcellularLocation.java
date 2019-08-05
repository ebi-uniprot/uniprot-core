package org.uniprot.core.uniprot.comment;

import java.io.Serializable;

public interface SubcellularLocation extends Serializable {

    SubcellularLocationValue getLocation();

    SubcellularLocationValue getTopology();

    SubcellularLocationValue getOrientation();

    boolean hasLocation();

    boolean hasTopology();

    boolean hasOrientation();

}
