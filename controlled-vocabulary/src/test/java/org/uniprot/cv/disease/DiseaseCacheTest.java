package org.uniprot.cv.disease;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseEntry;

class DiseaseCacheTest {

    private static final String DEFAULT_DATA_FILE = "disease/sample_humdisease.txt";

    @Test
    void passingEmptyFileToCache_returnsEmptyList() {
        String emptyFileName = "empty.txt";
        List<DiseaseEntry> diseases = DiseaseCache.INSTANCE.get(emptyFileName);
        // the diseases should come from FTP
        Assertions.assertTrue(
                diseases.isEmpty(), "disease should be empty because passing empty file");
    }
}
