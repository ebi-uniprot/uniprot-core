package org.uniprot.core.xml.proteome;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GenomeAnnotation;
import org.uniprot.core.proteome.impl.GenomeAnnotationBuilder;
import org.uniprot.core.xml.jaxb.proteome.GenomeAnnotationType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 14/11/2020
 */
class GenomeAnnotationConverterTest {

    private final ObjectFactory xmlFactory = new ObjectFactory();
    GenomeAnnotationConverter converter = new GenomeAnnotationConverter();

    @Test
    void testFromXml() {
        GenomeAnnotationType  genomeAnnotationType = xmlFactory.createGenomeAnnotationType();
        genomeAnnotationType.setGenomeAnnotationUrl("url value 1");
        genomeAnnotationType.setGenomeAnnotationSource("source value 1");

        GenomeAnnotation converted = converter.fromXml(genomeAnnotationType);
        assertNotNull(converted);
        assertEquals("source value 1", converted.getSource());
        assertEquals("url value 1", converted.getUrl());
    }

    @Test
    void testToXml() {
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .source("source value")
                .url("URL value")
                .build();

        GenomeAnnotationType  converted = converter.toXml(genomeAnnotation);
        assertNotNull(converted);
        assertEquals("source value", converted.getGenomeAnnotationSource());
        assertEquals("URL value", converted.getGenomeAnnotationUrl());
    }

    @Test
    void testRoundTrip() {
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder()
                .source("source value")
                .url("URL value")
                .build();

        GenomeAnnotationType  convertedType = converter.toXml(genomeAnnotation);
        assertNotNull(convertedType);
        GenomeAnnotation converted = converter.fromXml(convertedType);
        assertNotNull(converted);
        assertEquals(genomeAnnotation, converted);
    }

    @Test
    void testRoundTripEmptyGenomeAnnotation() {
        GenomeAnnotation genomeAnnotation = new GenomeAnnotationBuilder().build();

        GenomeAnnotationType  convertedType = converter.toXml(genomeAnnotation);
        assertNotNull(convertedType);
        GenomeAnnotation converted = converter.fromXml(convertedType);
        assertNotNull(converted);
        assertEquals(genomeAnnotation, converted);
    }
}