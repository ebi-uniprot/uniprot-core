package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.impl.ConditionValueBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.Objects;

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
        if (Objects.isNull(xmlObj)) return null;

        ConditionValueBuilder builder = new ConditionValueBuilder(xmlObj.getValue());
        builder.cvId(xmlObj.getCvId());
        return builder.build();
    }

    @Override
    public ConditionValue toXml(org.uniprot.core.unirule.ConditionValue uniObj) {
        if (Objects.isNull(uniObj)) return null;

        ConditionValue conditionValue = this.objectFactory.createConditionValue();
        conditionValue.setValue(uniObj.getValue());
        conditionValue.setCvId(uniObj.getCvId());
        return conditionValue;
    }
}
