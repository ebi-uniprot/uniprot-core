package org.uniprot.core.uniprotkb.comment;

import java.util.List;

public interface SubcellularLocationComment extends Comment, HasMolecule {

    Note getNote();

    List<SubcellularLocation> getSubcellularLocations();

    boolean hasNote();

    boolean hasSubcellularLocations();
}
