package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineConverter;
import org.uniprot.core.flatfile.parser.impl.oh.OhLineObject;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;

class OhLineConverterTest {
    @Test
    void test() {
        // "OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).
        OhLineObject ohO = new OhLineObject();
        OhLineObject.OhValue ohV = new OhLineObject.OhValue();
        ohV.tax_id = 9598;
        ohV.hostname = "Pan troglodytes (Chimpanzee)";
        ohO.hosts.add(ohV);
        OhLineConverter converter = new OhLineConverter();
        List<OrganismHost> ohs = converter.convert(ohO);
        assertEquals(1, ohs.size());
        OrganismHost oh = ohs.get(0);
        assertEquals(9598, oh.getTaxonId());

        assertEquals("Pan troglodytes", oh.getScientificName());
        assertEquals("Chimpanzee", oh.getCommonName());
    }
}
