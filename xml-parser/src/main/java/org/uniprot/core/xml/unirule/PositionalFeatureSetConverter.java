package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.PositionFeatureSet;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureSetType;

public class PositionalFeatureSetConverter
        implements Converter<PositionalFeatureSetType, PositionFeatureSet> {

    private final ObjectFactory objectFactory;

    public PositionalFeatureSetConverter() {
        this(new ObjectFactory());
    }

    public PositionalFeatureSetConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public PositionFeatureSet fromXml(PositionalFeatureSetType xmlObj) {
        return null;
    }

    @Override
    public PositionalFeatureSetType toXml(PositionFeatureSet uniObj) {
        return null;
    }
}
