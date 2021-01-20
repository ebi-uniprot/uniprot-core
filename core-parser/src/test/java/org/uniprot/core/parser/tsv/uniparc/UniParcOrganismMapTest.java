package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcOrganismMapTest {

    @Test
    void testAttributeValues() {
        List<Organism> taxes = getTaxonomies();
        UniParcOrganismMap organismMap = new UniParcOrganismMap(taxes);
        Map<String, String> result = organismMap.attributeValues();
        assertEquals(2, result.size());
        assertEquals("Homo sapiens (HUMAN); MOUSE", result.get("organism"));
        assertEquals("9606; 10090", result.get("organism_id"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("gene", "upi", "length");
        assertFalse(UniParcOrganismMap.contains(fields));

        fields = Arrays.asList("gene", "upi", "organism");
        assertTrue(UniParcOrganismMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("organism", "organism_id");
        assertEquals(UniParcOrganismMap.FIELDS, fields);
    }

    private List<Organism> getTaxonomies() {
        Organism taxonomy =
                new OrganismBuilder()
                        .taxonId(9606)
                        .scientificName("Homo sapiens")
                        .commonName("HUMAN")
                        .build();
        Organism taxonomy2 = new OrganismBuilder()
                .taxonId(10090)
                .scientificName("MOUSE")
                .build();
        return Arrays.asList(taxonomy, taxonomy2);
    }
}
