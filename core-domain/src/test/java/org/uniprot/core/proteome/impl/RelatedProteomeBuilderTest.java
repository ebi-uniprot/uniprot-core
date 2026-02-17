package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.ProteomeId;
import org.uniprot.core.proteome.RelatedProteome;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelatedProteomeBuilderTest {

    @Test
    void testProteomeId() {
        String id = "UP000005640";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        RelatedProteome proteome = new RelatedProteomeBuilder().proteomeId(proteomeId).build();
        assertEquals(proteomeId, proteome.getId());
    }

    @Test
    void testSimilarity() {
        Float similarity = 0.1f;
        RelatedProteome proteome = new RelatedProteomeBuilder().similarity(similarity).build();
        assertEquals(similarity, proteome.getSimilarity());
    }

    @Test
    void testTaxonomy() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        RelatedProteome proteome = new RelatedProteomeBuilder().taxonomy(taxonomy).build();
        assertEquals(taxonomy, proteome.getTaxId());
    }


    @Test
    void testFromProteome() {
        String id = "UP000004340";
        ProteomeId proteomeId = new ProteomeIdBuilder(id).build();
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        RelatedProteomeBuilder builder = new RelatedProteomeBuilder().proteomeId(proteomeId).similarity(0.2f)
                .taxonomy(taxonomy);
        RelatedProteome proteome = builder.build();
        RelatedProteome proteome1 = RelatedProteomeBuilder.from(proteome).build();
        assertEquals(proteome1, proteome);
    }
}
