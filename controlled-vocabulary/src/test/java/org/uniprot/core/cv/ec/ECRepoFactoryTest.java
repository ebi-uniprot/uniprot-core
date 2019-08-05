package org.uniprot.core.cv.ec;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.ECRepo;
import org.uniprot.core.cv.ec.ECRepoFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created 20/06/19
 *
 * @author Edd
 */
class ECRepoFactoryTest {
    @Test
    void canCreateFactory() {
        ECRepo ecRepo = ECRepoFactory.get("ec/");
        assertThat(ecRepo, is(notNullValue()));
    }

    @Test
    void loadedECsAreCorrect() {
        ECRepo ecRepo = ECRepoFactory.get("ec/");

        // prove enzyme class file was read
        final String id1 = "1.-.-.-";
        ecRepo.getEC(id1).map(ec -> {
            assertThat(ec.id(), is(id1));
            assertThat(ec.label(), is("Oxidoreductases"));
            return true;
        }).orElseThrow(IllegalStateException::new);

        final String id2 = "1.1.99.-";
        ecRepo.getEC(id2).map(ec -> {
            assertThat(ec.id(), is(id2));
            assertThat(ec.label(), is("With other acceptors"));
            return true;
        }).orElseThrow(IllegalStateException::new);

        // prove enzyme dat file was read
        final String id3 = "1.1.1.2";
        ecRepo.getEC(id3).map(ec -> {
            assertThat(ec.id(), is(id3));
            assertThat(ec.label(), is("Alcohol dehydrogenase (NADP(+))"));
            return true;
        }).orElseThrow(IllegalStateException::new);
    }
}