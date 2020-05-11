package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.FeatureTagConditionValue;
import org.uniprot.core.unirule.impl.FeatureTagConditionValueBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.FtagConditionValue;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.Objects;

public class FtagConditionConverter
        implements Converter<FtagConditionValue, FeatureTagConditionValue> {

    private final ObjectFactory objectFactory;

    public FtagConditionConverter() {
        this(new ObjectFactory());
    }

    public FtagConditionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public FeatureTagConditionValue fromXml(FtagConditionValue xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        FeatureTagConditionValueBuilder builder =
                new FeatureTagConditionValueBuilder(xmlObj.getValue());
        builder.pattern(xmlObj.getPattern());
        return builder.build();
    }

    @Override
    public FtagConditionValue toXml(FeatureTagConditionValue uniObj) {
        if (Objects.isNull(uniObj)) return null;
        FtagConditionValue ftagConditionValue = objectFactory.createFtagConditionValue();
        ftagConditionValue.setValue(uniObj.getValue());
        ftagConditionValue.setPattern(uniObj.getPattern());
        return ftagConditionValue;
    }
}
