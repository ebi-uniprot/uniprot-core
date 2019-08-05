package org.uniprot.core.cv.disease;

import java.util.List;

public interface DiseaseService {
	List<Disease> getAll();
	Disease getById(String id);
}
