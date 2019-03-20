package uk.ac.ebi.uniprot.cv.go;

import java.util.List;

public interface GoService {
	GoTerm getGoTermById(String id);
	List<String> getIsAById(String id);
	List<String> getPartOfById(String id);
	List<String> getChildrenById(String id);
}
