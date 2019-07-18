package uk.ac.ebi.uniprot.cv.subcell;

import java.util.List;

public interface SubcellularLocationService {
    List<SubcellularLocationEntry> getAll();

    SubcellularLocationEntry getById(String id);
}
