package org.uniprot.cv.chebi;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.Chebi;

public class ChebiCacheTest {
    private static final String DEFAULT_DATA_FILE = "chebi/chebi.obo";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // reflection to set the value of a field of singleton and test it
        Field dl = ChebiCache.class.getDeclaredField("defaultDataLocation");
        dl.setAccessible(true);
        dl.set(ChebiCache.INSTANCE, DEFAULT_DATA_FILE);
    }

    @Test
    void testLoadCacheFromDefaultFileAsFallback() {
        String emptyFileName = "empty.txt";
        List<Chebi> chebis = ChebiCache.INSTANCE.get(emptyFileName);
        // the chebis should come from FTP
        Assertions.assertFalse(chebis.isEmpty(), "list is empty");
        Assertions.assertEquals(3, chebis.size());
    }
}
