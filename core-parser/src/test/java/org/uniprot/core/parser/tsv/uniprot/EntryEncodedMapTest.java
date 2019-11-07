package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.GeneEncodingType;
import org.uniprot.core.uniprot.GeneLocation;
import org.uniprot.core.uniprot.builder.GeneLocationBuilder;

class EntryEncodedMapTest {
    @Test
    void testFields() {
        List<String> fields = EntryEncodedMap.FIELDS;
        List<String> expected = Collections.singletonList("organelle");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataEmpty() {
        EntryEncodedMap dl = new EntryEncodedMap(null);
        assertTrue(dl.attributeValues().isEmpty());
    }

    @Test
    void testGetData() {
        List<GeneLocation> glocations = new ArrayList<>();
        glocations.add(
                new GeneLocationBuilder().geneEncodingType(GeneEncodingType.MITOCHONDRION).build());
        glocations.add(
                new GeneLocationBuilder()
                        .geneEncodingType(GeneEncodingType.PLASMID)
                        .value("mlp1")
                        .build());
        EntryEncodedMap dl = new EntryEncodedMap(glocations);
        Map<String, String> result = dl.attributeValues();
        assertEquals(1, result.size());
        String value = result.get(EntryEncodedMap.FIELDS.get(0));
        assertNotNull(value);
        assertEquals("Mitochondrion; Plasmid mlp1", value);
    }
}
