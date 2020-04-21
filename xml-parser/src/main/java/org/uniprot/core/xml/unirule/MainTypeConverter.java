package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Rule;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.MainType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class MainTypeConverter implements Converter<MainType, Rule> {

    private final ObjectFactory objectFactory;

    public MainTypeConverter() {
        this(new ObjectFactory());
    }

    public MainTypeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public Rule fromXml(MainType xmlObj) {
        return null;
    }

    @Override
    public MainType toXml(Rule uniObj) {
        return null;
    }
}
