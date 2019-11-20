package org.uniprot.core.uniprot.comment;

import java.util.List;

public interface SubcellularLocationComment extends Comment, HasMolecule {

    Note getNote();

    List<SubcellularLocation> getSubcellularLocations();

    boolean hasNote();

    boolean hasSubcellularLocations();

}
