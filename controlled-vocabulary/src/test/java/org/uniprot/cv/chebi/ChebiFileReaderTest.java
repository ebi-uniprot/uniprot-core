package org.uniprot.cv.chebi;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.chebi.ChebiEntry;

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
        String id = "30151";
        List<ChebiEntry> chebis = reader.parseLines(asList("[Term]", "id: CHEBI:" + id));
        assertThat(chebis, hasSize(1));
        ChebiEntry chebi = chebis.get(0);
        assertThat(chebi.getId(), is(id));
        assertThat(chebi.getName(), is(nullValue()));
        assertThat(chebi.getInchiKey(), is(nullValue()));
    }

    @Test
    void extractsName() {
        String name = "aluminide(1-)";
        List<ChebiEntry> chebis = reader.parseLines(asList("[Term]", "name: " + name));
        assertThat(chebis, hasSize(1));
        ChebiEntry chebi = chebis.get(0);
        assertThat(chebi.getName(), is(name));
        assertThat(chebi.getId(), is(nullValue()));
        assertThat(chebi.getInchiKey(), is(nullValue()));
    }

    @Test
    void extractsInchikey() {
        String inchikey = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        List<ChebiEntry> chebis =
                reader.parseLines(
                        asList(
                                "[Term]",
                                "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \""
                                        + inchikey
                                        + "\" xsd:string"));
        assertThat(chebis, hasSize(1));
        ChebiEntry chebi = chebis.get(0);
        assertThat(chebi.getInchiKey(), is(inchikey));
        assertThat(chebi.getId(), is(nullValue()));
        assertThat(chebi.getName(), is(nullValue()));
    }

    @Test
    void extractsAllFields() {
        String id = "30151";
        String name = "aluminide(1-)";
        String inchikey = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        List<ChebiEntry> chebis =
                reader.parseLines(
                        asList(
                                "[Term]",
                                "id: CHEBI:" + id,
                                "name: " + name,
                                "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \""
                                        + inchikey
                                        + "\" xsd:string"));
        assertThat(chebis, hasSize(1));
        ChebiEntry chebi = chebis.get(0);
        assertThat(chebi.getId(), is(id));
        assertThat(chebi.getName(), is(name));
        assertThat(chebi.getInchiKey(), is(inchikey));
    }

    @Test
    void extractsAllFieldsForMultipleEntries() {
        String id1 = "30151";
        String name1 = "aluminide(2-)";
        String inchikey1 = "SBLSYFIUPXRQRY-UHFFFAOYSA-N";
        String id2 = "CHEBI:30152";
        String name2 = "halide anion";
        String inchikey2 = "PUZPDOWCWNUUKD-UHFFFAOYSA-M";

        List<ChebiEntry> chebis =
                reader.parseLines(
                        asList(
                                "[Term]",
                                "id: CHEBI:" + id1,
                                "name: " + name1,
                                "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \""
                                        + inchikey1
                                        + "\" xsd:string",
                                "",
                                "[Term]",
                                "id: CHEBI:" + id2,
                                "name: " + name2,
                                "property_value: http://purl.obolibrary.org/obo/chebi/inchikey \""
                                        + inchikey2
                                        + "\" xsd:string"));
        assertThat(chebis, hasSize(2));

        ChebiEntry chebi1 = chebis.get(0);
        assertThat(chebi1.getId(), is(id1));
        assertThat(chebi1.getName(), is(name1));
        assertThat(chebi1.getInchiKey(), is(inchikey1));

        ChebiEntry chebi2 = chebis.get(1);
        assertThat(chebi2.getId(), is(id2));
        assertThat(chebi2.getName(), is(name2));
        assertThat(chebi2.getInchiKey(), is(inchikey2));
    }

    @Test
    void malformedIdLineReturnsNullValue() {
        List<ChebiEntry> chebis = reader.parseLines(asList("[Term]", "id:???WWWRONG"));
        assertThat(chebis, hasSize(1));
        ChebiEntry chebi = chebis.get(0);
        assertThat(chebi.getId(), is(nullValue()));
    }
}
