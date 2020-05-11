package org.uniprot.cv.disease;

import java.util.List;

import org.uniprot.core.cv.disease.DiseaseEntry;

public interface DiseaseRepo {
    List<DiseaseEntry> getAll();

    DiseaseEntry getById(String id);
}
