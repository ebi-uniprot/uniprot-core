package org.uniprot.core.cv.chebi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChebiCacheTest {

    @Test
    void testLoadCacheFromDefaultFileAsFallback(){
        String emptyFileName = "empty.txt";
        String defaultDataFile = "chebi/chebi.obo";
        ChebiCache chebiCache = ChebiCache.INSTANCE;
        chebiCache.setDefaultDataFile(defaultDataFile);
        List<Chebi> chebis = ChebiCache.INSTANCE.get(emptyFileName);
        // the chebis should come from FTP
        Assertions.assertFalse(chebis.isEmpty(), "list is empty");
        Assertions.assertEquals(3, chebis.size());
    }
}
