package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.Condition;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionType;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class ConditionConverter implements Converter<ConditionType, Condition> {

    private final ObjectFactory objectFactory;
    private final ConditionValueConverter conditionValueConverter;
    private final RangeConverter rangeConverter;

    public ConditionConverter() {
        this(new ObjectFactory());
    }

    public ConditionConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.conditionValueConverter = new ConditionValueConverter(objectFactory);
        this.rangeConverter = new RangeConverter(objectFactory);
    }

    @Override
    public Condition fromXml(ConditionType xmlObj) {
        return null;
    }

    @Override
    public ConditionType toXml(Condition uniObj) {
        if (Objects.isNull(uniObj)) return null;
        ConditionType conditionType = this.objectFactory.createConditionType();
        List<ConditionValue> conditionValues =
                uniObj.getConditionValues().stream()
                        .map(this.conditionValueConverter::toXml)
                        .collect(Collectors.toList());
        conditionType.getValue().addAll(conditionValues);
        conditionType.setRange(this.rangeConverter.toXml(uniObj.getRange()));

        //        uniObj.getTag()FIXME

        return null;
    }
}
