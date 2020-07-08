package org.uniprot.core.xml.feature;

import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.feature.ObjectFactory;
import org.uniprot.core.xml.jaxb.feature.SequenceType;

/**
 * @author lgonzales
 * @since 15/05/2020
 */
public class FeatureSequenceConverter implements Converter<SequenceType, Sequence> {
    private final ObjectFactory xmlFactory;

    public FeatureSequenceConverter() {
        this(new ObjectFactory());
    }

    public FeatureSequenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Sequence fromXml(SequenceType xmlObj) {
        String sequence = xmlObj.getValue();
        return new SequenceBuilder(sequence).build();
    }

    @Override
    public SequenceType toXml(Sequence sequence) {
        SequenceType xmlObj = xmlFactory.createSequenceType();
        xmlObj.setChecksum(sequence.getCrc64());
        xmlObj.setValue(sequence.getValue());
        return xmlObj;
    }
}
