package org.uniprot.cv.chebi;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.ChebiEntry;

class ChebiCacheTest {
    private static final String DEFAULT_DATA_FILE = "chebi/chebi.obo";

    @Test
    void testLoadCacheFromDefaultFileAsFallback() {
        String emptyFileName = "empty.txt";
        List<ChebiEntry> chebis = ChebiCache.INSTANCE.get(emptyFileName);
        // the chebis should come from FTP
        Assertions.assertFalse(chebis.isEmpty(), "list is empty");
        Assertions.assertEquals(3, chebis.size());
    }
}
