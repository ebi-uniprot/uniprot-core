package org.uniprot.core.uniprot.comment;

import java.util.List;

public interface SubcellularLocationComment extends Comment {

    String getMolecule();

    Note getNote();

    List<SubcellularLocation> getSubcellularLocations();

    boolean hasMolecule();

    boolean hasNote();

    boolean hasSubcellularLocations();
}
