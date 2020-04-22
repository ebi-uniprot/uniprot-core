package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionSetType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class ConditionSetConverter implements Converter<ConditionSetType, ConditionSet> {
    public ConditionSetConverter(ObjectFactory objectFactory) {}

    @Override
    public ConditionSet fromXml(ConditionSetType xmlObj) {
        return null;
    }

    @Override
    public ConditionSetType toXml(ConditionSet uniObj) {
        return null;
    }
}
