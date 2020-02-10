package org.uniprot.cv.disease;

import java.util.List;

import org.uniprot.core.cv.disease.Disease;

public interface DiseaseService {
    List<Disease> getAll();

    Disease getById(String id);
}
