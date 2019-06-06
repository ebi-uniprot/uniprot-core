package uk.ac.ebi.uniprot.cv.chebi;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
class ChebiServiceFactoryTest {
    @Test
    void canCreateFactory() {
        ChebiService service = ChebiServiceFactory.get("chebi/chebi.obo");
        assertThat(service, is(notNullValue()));
    }

    @Test
    void loadedChebiEntitiesAreCorrect() {
        ChebiService service = ChebiServiceFactory.get("chebi/chebi.obo");
        assertThat(service, is(notNullValue()));

        Chebi chebi = service.getChebi("CHEBI:30151");
        assertThat(chebi.name(), is("aluminide(1-)"));
        assertThat(chebi.inchiKey(), is("SBLSYFIUPXRQRY-UHFFFAOYSA-N"));

        chebi = service.getChebi("CHEBI:32129");
        assertThat(chebi.name(), is("diamminesilver(1+) fluoride"));
        assertThat(chebi.inchiKey(), is("FJKGRAZQBBWYLG-UHFFFAOYSA-M"));

        chebi = service.getChebi("CHEBI:36347");
        assertThat(chebi.name(), is("nuclear particle"));
        assertThat(chebi.inchiKey(), is(nullValue()));
    }
}