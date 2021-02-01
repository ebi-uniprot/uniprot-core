package org.uniprot.cv.go.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;

/**
 * Created 26/11/2020
 *
 * @author Edd
 */
class GOTermCacheTest {
    private static List<GeneOntologyEntry> cache;

    @BeforeAll
    static void setUp() {
        cache = GOTermCache.INSTANCE.get("src/test/resources/go");
    }

    /**
     * Note that this test only the correct wiring up and loading of the cache, not the contents
     * content test is done in {@link GOTermFileReaderTest}
     */
    @Test
    void cacheInitialisedCorrectly() {
        assertThat(cache, hasSize(12));
    }
}
