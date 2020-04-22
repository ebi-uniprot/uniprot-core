package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class AnnotationConverter implements Converter<AnnotationType, Annotation> {
    private final ObjectFactory objectFactory;

    public AnnotationConverter() {
        this(new ObjectFactory());
    }

    public AnnotationConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public Annotation fromXml(AnnotationType xmlObj) {
        return null;
    }

    @Override
    public AnnotationType toXml(Annotation uniObj) {
        return null;
    }
}
