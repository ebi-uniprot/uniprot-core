package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.SamFeatureSetType;

public class SamFeatureSetConverter implements Converter<SamFeatureSetType, SamFeatureSet> {

    private final ObjectFactory objectFactory;

    public SamFeatureSetConverter() {
        this(new ObjectFactory());
    }

    public SamFeatureSetConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public SamFeatureSet fromXml(SamFeatureSetType xmlObj) {
        return null;
    }

    @Override
    public SamFeatureSetType toXml(SamFeatureSet uniObj) {
        return null;
    }
}
