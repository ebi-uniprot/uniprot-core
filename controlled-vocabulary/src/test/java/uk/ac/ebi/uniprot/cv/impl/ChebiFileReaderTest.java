package uk.ac.ebi.uniprot.cv.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.chebi.Chebi;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

/**
 * Created 06/06/19
 *
 * @author Edd
 */
class ChebiFileReaderTest {
    private static ChebiFileReader reader;

    @BeforeAll
    static void setup() {
        reader = new ChebiFileReader();
    }

    @Test
    void extractsId() {
        String id = "CHEBI:30151";
        List<Chebi> chebis = reader.parseLines(asList("[Term]", "id: " + id));
        assertThat(chebis, hasSize(1));
        Chebi chebi = chebis.get(0);
        assertThat(chebi.id(), is(id));
        assertThat(chebi.name(), is(nullValue()));
        assertThat(chebi.inchiKey(), is(nullValue()));
    }

    @Test
    void extractsName() {
        String name = "aluminide(1-)";
        List<Chebi> chebis = reader.parseLines(asList("[Term]", "name: " + name));
        assertThat(chebis, hasSize(1));
        Chebi chebi = chebis.get(0);
        assertThat(chebi.name(), is(name));
        assertThat(chebi.id(), is(nullValue()));
        assertThat(chebi.inchiKey(), is(nullValue()));
    }

    @Test
    void extractsInchikey() {
        String inchikey = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        List<Chebi> chebis = reader
                .parseLines(asList("[Term]", "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \"" + inchikey + "\" xsd:string"));
        assertThat(chebis, hasSize(1));
        Chebi chebi = chebis.get(0);
        assertThat(chebi.inchiKey(), is(inchikey));
        assertThat(chebi.id(), is(nullValue()));
        assertThat(chebi.name(), is(nullValue()));
    }

    @Test
    void extractsAllFields() {
        String id = "CHEBI:30151";
        String name = "aluminide(1-)";
        String inchikey = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        List<Chebi> chebis = reader
                .parseLines(
                        asList("[Term]",
                               "id: " + id,
                               "name: " + name,
                               "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \"" + inchikey + "\" xsd:string"));
        assertThat(chebis, hasSize(1));
        Chebi chebi = chebis.get(0);
        assertThat(chebi.id(), is(id));
        assertThat(chebi.name(), is(name));
        assertThat(chebi.inchiKey(), is(inchikey));
    }

    @Test
    void extractsAllFieldsForMultipleEntries() {
        String id1 = "CHEBI:30151";
        String name1 = "aluminide(2-)";
        String inchikey1 = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        String id2 = "CHEBI:30152";
        String name2 = "halide anion";
        String inchikey2 = "PUZPDOWCWNUUKD-UHFFFAOYSA-M";

        List<Chebi> chebis = reader
                .parseLines(
                        asList("[Term]",
                               "id: " + id1,
                               "name: " + name1,
                               "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \"" + inchikey1 + "\" xsd:string",
                               "",
                               "[Term]",
                               "id: " + id2,
                               "name: " + name2,
                               "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \"" + inchikey2 + "\" xsd:string"));
        assertThat(chebis, hasSize(2));

        Chebi chebi1 = chebis.get(0);
        assertThat(chebi1.id(), is(id1));
        assertThat(chebi1.name(), is(name1));
        assertThat(chebi1.inchiKey(), is(inchikey1));

        Chebi chebi2 = chebis.get(1);
        assertThat(chebi2.id(), is(id2));
        assertThat(chebi2.name(), is(name2));
        assertThat(chebi2.inchiKey(), is(inchikey2));
    }

    @Test
    void malformedIdLineReturnsNullValue() {
        List<Chebi> chebis = reader
                .parseLines(asList("[Term]", "id:???WWWRONG"));
        assertThat(chebis, hasSize(1));
        Chebi chebi = chebis.get(0);
        assertThat(chebi.id(), is(nullValue()));
    }
}