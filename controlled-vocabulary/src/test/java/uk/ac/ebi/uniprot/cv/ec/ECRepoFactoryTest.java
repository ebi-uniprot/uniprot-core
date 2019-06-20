package uk.ac.ebi.uniprot.cv.ec;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
        String id = "1.-.-.-";
        EC ec = ecRepo.getEC(id);
        assertThat(ec.id(), is(id));
        assertThat(ec.label(), is("Oxidoreductases"));

        id = "1.1.99.-";
        ec = ecRepo.getEC(id);
        assertThat(ec.id(), is(id));
        assertThat(ec.label(), is("With other acceptors"));

        // prove enzyme dat file was read
        id = "1.1.1.2";
        ec = ecRepo.getEC(id);
        assertThat(ec.id(), is(id));
        assertThat(ec.label(), is("Alcohol dehydrogenase (NADP(+))"));
    }
}