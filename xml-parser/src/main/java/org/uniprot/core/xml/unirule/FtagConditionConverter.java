package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.unirule.impl.FtagConditionValueBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.FtagConditionValue;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class FtagConditionConverter
        implements Converter<FtagConditionValue, org.uniprot.core.unirule.FtagConditionValue> {

    private final ObjectFactory objectFactory;

    public FtagConditionConverter() {
        this(new ObjectFactory());
    }

    public FtagConditionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public org.uniprot.core.unirule.FtagConditionValue fromXml(FtagConditionValue xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        FtagConditionValueBuilder builder = new FtagConditionValueBuilder(xmlObj.getValue());
        builder.pattern(xmlObj.getPattern());
        return builder.build();
    }

    @Override
    public FtagConditionValue toXml(org.uniprot.core.unirule.FtagConditionValue uniObj) {
        if (Objects.isNull(uniObj)) return null;
        FtagConditionValue ftagConditionValue = objectFactory.createFtagConditionValue();
        ftagConditionValue.setValue(uniObj.getValue());
        ftagConditionValue.setPattern(uniObj.getPattern());
        return ftagConditionValue;
    }
}
