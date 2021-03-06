package org.uniprot.cv.ec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.ECEntry;

/**
 * Created 18/03/19
 *
 * @author Edd
 */
class ECCacheTest {
    @Test
    void canLoadCacheFromFiles() {
        List<ECEntry> ecs = ECCache.INSTANCE.get("ec/");
        assertThat(ecs, hasSize(12));
    }
}
