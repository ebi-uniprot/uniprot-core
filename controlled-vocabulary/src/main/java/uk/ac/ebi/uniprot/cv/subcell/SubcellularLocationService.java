package uk.ac.ebi.uniprot.cv.subcell;

import java.util.List;

public interface SubcellularLocationService {
	List<SubcellularLocation> getAll();
	SubcellularLocation getById(String id);
}
