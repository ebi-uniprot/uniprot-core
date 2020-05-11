package org.uniprot.cv.subcell;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

import java.util.List;

public interface SubcellularLocationRepo {
    List<SubcellularLocationEntry> getAll();

    SubcellularLocationEntry getById(String id);
}
