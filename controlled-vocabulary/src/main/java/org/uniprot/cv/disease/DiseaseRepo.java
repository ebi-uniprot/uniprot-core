package org.uniprot.cv.disease;

import org.uniprot.core.cv.disease.DiseaseEntry;

import java.util.List;

public interface DiseaseRepo {
    List<DiseaseEntry> getAll();

    DiseaseEntry getById(String id);
}
