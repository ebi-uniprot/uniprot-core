package org.uniprot.cv.subcell;

import java.util.List;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

public interface SubcellularLocationService {
    List<SubcellularLocationEntry> getAll();

    SubcellularLocationEntry getById(String id);
}
