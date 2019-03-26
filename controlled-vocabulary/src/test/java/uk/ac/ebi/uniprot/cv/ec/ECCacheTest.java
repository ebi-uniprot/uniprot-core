package uk.ac.ebi.uniprot.cv.ec;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static uk.ac.ebi.uniprot.cv.ec.ECCache.FTP_LOCATION;

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
    @Disabled
    @Test
    void givenWrongFilePath_whenLoadCache_loadsCacheFromFTP() {
        List<EC> ecs = ECCache.INSTANCE.get("/this/is/wrong/");
        assertThat(ecs, is(not(emptyList())));
    }
    @Disabled
    @Test
    void canLoadCacheFromFTP() {
        List<EC> ecs = ECCache.INSTANCE.get(FTP_LOCATION);
        assertThat(ecs, is(not(emptyList())));
    }
}