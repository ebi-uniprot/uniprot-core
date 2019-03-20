package uk.ac.ebi.uniprot.cv.pathway;

import java.util.List;

public interface UniPathwayService {
	UniPathway getById(String id);
	UniPathway getByName(String name);
	List<UniPathway> getChildrenById(String id);
}
