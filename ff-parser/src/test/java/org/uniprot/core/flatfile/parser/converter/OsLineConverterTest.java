package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.os.OsLineConverter;
import org.uniprot.core.flatfile.parser.impl.os.OsLineObject;
import org.uniprot.core.uniprot.taxonomy.OrganismName;

class OsLineConverterTest {
    @Test
    void test() {
        // "OS   Solanum melongena (Eggplant) (Aubergine).
        OsLineObject osO = new OsLineObject();
        osO.setOrganismSpecies("Solanum melongena (Eggplant) (Aubergine)");
        OsLineConverter converter = new OsLineConverter();
        OrganismName org = converter.convert(osO);
        assertEquals("Solanum melongena", org.getScientificName());
        assertEquals("Eggplant", org.getCommonName());
        assertEquals("Aubergine", org.getSynonyms().get(0));
    }

    @Test
    void test2() {
        // OS   Homo sapiens (Human).
        OsLineObject osO = new OsLineObject();
        osO.setOrganismSpecies("Homo sapiens (Human)");
        OsLineConverter converter = new OsLineConverter();
        OrganismName org = converter.convert(osO);
        assertEquals("Homo sapiens", org.getScientificName());
        assertEquals("Human", org.getCommonName());
        assertEquals(0, org.getSynonyms().size());
    }

    @Test
    void testVirus() {
        // OS   Frog virus 3 (isolate Goorha) (FV-3).
        OsLineObject osO = new OsLineObject();
        osO.setOrganismSpecies("Frog virus 3 (isolate Goorha) (FV-3)");
        OsLineConverter converter = new OsLineConverter();
        OrganismName org = converter.convert(osO);
        assertEquals("Frog virus 3 (isolate Goorha)", org.getScientificName());
        assertEquals("FV-3", org.getCommonName());
        assertEquals(0, org.getSynonyms().size());
    }
}
