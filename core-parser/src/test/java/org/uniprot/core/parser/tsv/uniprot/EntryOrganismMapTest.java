package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

class EntryOrganismMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryOrganismMap.FIELDS;
        List<String> expected = Arrays.asList("organism_name", "organism_id");
        assertEquals(expected, fields);
    }

    @Test
    void testGetData() {
        Organism organism =
                new OrganismBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("Human")
                        .build();
        EntryOrganismMap dl = new EntryOrganismMap(organism);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        verify("organism_name", "Homo sapiens (Human)", result);
        verify("organism_id", "9606", result);
    }

    private void verify(String field, String expected, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }
}
