package org.uniprot.cv.disease;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.Disease;

public class DiseaseCacheTest {

    private static final String DEFAULT_DATA_FILE = "disease/sample_humdisease.txt";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // reflection to set the value of a field of singleton and test it
        Field dl = DiseaseCache.class.getDeclaredField("defaultDataLocation");
        dl.setAccessible(true);
        dl.set(DiseaseCache.INSTANCE, DEFAULT_DATA_FILE);
    }

    @Test
    void testLoadCacheFromDefaultFileAsFallback() {
        String emptyFileName = "empty.txt";
        List<Disease> diseases = DiseaseCache.INSTANCE.get(emptyFileName);
        // the diseases should come from FTP
        Assertions.assertFalse(diseases.isEmpty(), "disease list is empty");
        Assertions.assertEquals(4, diseases.size());
    }
}
