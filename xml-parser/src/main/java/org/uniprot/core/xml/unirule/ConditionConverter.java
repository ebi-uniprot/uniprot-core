package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Condition;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class ConditionConverter implements Converter<ConditionType, Condition> {
    public ConditionConverter(ObjectFactory objectFactory) {

    }

    @Override
    public Condition fromXml(ConditionType xmlObj) {
        return null;
    }

    @Override
    public ConditionType toXml(Condition uniObj) {
        return null;
    }
}
