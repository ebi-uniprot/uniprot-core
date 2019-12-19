package org.uniprot.core.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.UniProtRelease;

class UniProtReleaseBuilderTest {
    @Test
    void canCreateWithVersionNumber() {
        String version = "3.2";
        UniProtRelease release = new UniProtReleaseBuilder().currentVersion(version).build();
        assertNotNull(release);
        assertEquals(version, release.getCurrentVersion());
    }

    @Test
    void canCreateWithNextVersion() {
        String version = "4.2";
        UniProtRelease release = new UniProtReleaseBuilder().nextVersion(version).build();
        assertNotNull(release);
        assertEquals(version, release.getNextVersion());
    }

    @Test
    void canCreateWithCurrentReleaseDate() {
        LocalDate rDate = LocalDate.now();
        UniProtRelease release = new UniProtReleaseBuilder().currentReleaseDate(rDate).build();
        assertNotNull(release);
        assertEquals(rDate, release.getCurrentReleaseDate());
    }

    @Test
    void canCreateWithNextReleaseDate() {
        LocalDate rDate = LocalDate.now();
        UniProtRelease release = new UniProtReleaseBuilder().nextReleaseDate(rDate).build();
        assertNotNull(release);
        assertEquals(rDate, release.getNextReleaseDate());
    }

    @Test
    void canCreateBuilderFromInstance() {
        UniProtRelease obj = new UniProtReleaseBuilder().build();
        UniProtReleaseBuilder builder = new UniProtReleaseBuilder().from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        UniProtRelease obj = new UniProtReleaseBuilder().build();
        UniProtRelease obj2 = new UniProtReleaseBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
