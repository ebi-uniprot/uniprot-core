package org.uniprot.core.xml.proteome;

import org.uniprot.core.proteome.GenomeAnnotation;
import org.uniprot.core.proteome.impl.GenomeAnnotationBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.GenomeAnnotationType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

/**
 * @author lgonzales
 * @since 12/11/2020
 */
public class GenomeAnnotationConverter
        implements Converter<GenomeAnnotationType, GenomeAnnotation> {

    private final ObjectFactory xmlFactory;

    public GenomeAnnotationConverter() {
        this(new ObjectFactory());
    }

    public GenomeAnnotationConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public GenomeAnnotation fromXml(GenomeAnnotationType xmlObj) {
        return new GenomeAnnotationBuilder()
                .source(xmlObj.getGenomeAnnotationSource())
                .url(xmlObj.getGenomeAnnotationUrl())
                .build();
    }

    @Override
    public GenomeAnnotationType toXml(GenomeAnnotation uniObj) {
        GenomeAnnotationType genomeAnnotationType = xmlFactory.createGenomeAnnotationType();
        genomeAnnotationType.setGenomeAnnotationSource(uniObj.getSource());
        genomeAnnotationType.setGenomeAnnotationUrl(uniObj.getUrl());
        return genomeAnnotationType;
    }
}
