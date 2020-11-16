package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GenomeAnnotation;
import org.uniprot.core.proteome.ProteomeId;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 16/11/2020
 */
class GenomeAnnotationBuilderTest {

    @Test
    void source() {
        String source = "source Value";
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .source(source)
                .build();
        assertNotNull(genomeAnnotation);
        assertEquals(source, genomeAnnotation.getSource());
    }

    @Test
    void url() {
        String url = "URL value";
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .url(url)
                .build();
        assertNotNull(genomeAnnotation);
        assertEquals(url, genomeAnnotation.getUrl());
    }

    @Test
    void from() {
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .source("source value")
                .url("URL value")
                .build();
        GenomeAnnotation fromGenome = GenomeAnnotationBuilder.from(genomeAnnotation).build();
        assertNotNull(fromGenome);
        assertEquals(genomeAnnotation, fromGenome);
    }
}