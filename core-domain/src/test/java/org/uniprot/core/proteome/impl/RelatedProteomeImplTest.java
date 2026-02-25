package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.RelatedProteome;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import static org.junit.jupiter.api.Assertions.*;

public class RelatedProteomeImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        RelatedProteome obj = new RelatedProteomeImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        RelatedProteome impl = new RelatedProteomeImpl(new ProteomeIdImpl("id"), 5.8F, taxonomy);
        RelatedProteome obj = RelatedProteomeBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
