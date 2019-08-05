package org.uniprot.core.cv.ec;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.EC;
import org.uniprot.core.cv.ec.ECCache;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created 18/03/19
 *
 * @author Edd
 */
class ECCacheTest {
    @Test
    void canLoadCacheFromFiles() {
        List<EC> ecs = ECCache.INSTANCE.get("ec/");
        assertThat(ecs, hasSize(12));
    }
}