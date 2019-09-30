package org.uniprot.core.cv.disease;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiseaseCacheTest {

    @Test
    void testLoadCacheFromDefaultFileAsFallback() {
        String emptyFileName = "empty.txt";
        String defaultDataFile = "disease/sample_humdisease.txt";
        DiseaseCache diseaseCache = DiseaseCache.INSTANCE;
        diseaseCache.setDefaultDataFile(defaultDataFile);
        List<Disease> diseases = diseaseCache.get(emptyFileName);
        // the diseases should come from FTP
        Assertions.assertFalse(diseases.isEmpty(), "disease list is empty");
        Assertions.assertEquals(4, diseases.size());
    }
}
