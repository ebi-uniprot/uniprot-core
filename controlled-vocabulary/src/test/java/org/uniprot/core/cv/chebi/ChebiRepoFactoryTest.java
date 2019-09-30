package org.uniprot.core.cv.chebi;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
class ChebiRepoFactoryTest {
    @Test
    void canCreateFactory() {
        ChebiRepo repo = ChebiRepoFactory.get("chebi/chebi.obo");
        assertThat(repo, is(notNullValue()));
    }

    @Test
    void loadedChebiEntitiesAreCorrect() {
        ChebiRepo repo = ChebiRepoFactory.get("chebi/chebi.obo");
        assertThat(repo, is(notNullValue()));

        Chebi chebi = repo.getById("30151");
        assertThat(chebi.getName(), is("aluminide(1-)"));
        assertThat(chebi.getInchiKey(), is("SBLSYFIUPXRQRY-UHFFFAOYSA-N"));

        chebi = repo.getById("32129");
        assertThat(chebi.getName(), is("diamminesilver(1+) fluoride"));
        assertThat(chebi.getInchiKey(), is("FJKGRAZQBBWYLG-UHFFFAOYSA-M"));

        chebi = repo.getById("36347");
        assertThat(chebi.getName(), is("nuclear particle"));
        assertThat(chebi.getInchiKey(), is(nullValue()));
    }
}
