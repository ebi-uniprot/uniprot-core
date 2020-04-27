package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class ConditionValueConverter
        implements Converter<ConditionValue, org.uniprot.core.unirule.ConditionValue> {

    private final ObjectFactory objectFactory;

    public ConditionValueConverter() {
        this(new ObjectFactory());
    }

    public ConditionValueConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public org.uniprot.core.unirule.ConditionValue fromXml(ConditionValue xmlObj) {
        return null;
    }

    @Override
    public ConditionValue toXml(org.uniprot.core.unirule.ConditionValue uniObj) {
        return null;
    }
}
