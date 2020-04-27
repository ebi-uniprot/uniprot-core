package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.impl.ConditionSetBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ConditionSetType;
import org.uniprot.core.xml.jaxb.unirule.ConditionType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

public class ConditionSetConverter implements Converter<ConditionSetType, ConditionSet> {
    private final ObjectFactory objectFactory;
    private final ConditionConverter conditionConverter;

    public ConditionSetConverter() {
        this(new ObjectFactory());
    }

    public ConditionSetConverter(ObjectFactory objectFactory) {
        this.objectFactory = new ObjectFactory();
        this.conditionConverter = new ConditionConverter(objectFactory);
    }

    @Override
    public ConditionSet fromXml(ConditionSetType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        List<Condition> conditions =
                xmlObj.getCondition().stream()
                        .map(this.conditionConverter::fromXml)
                        .collect(Collectors.toList());

        ConditionSetBuilder builder = new ConditionSetBuilder(conditions);
        return builder.build();
    }

    @Override
    public ConditionSetType toXml(ConditionSet uniObj) {
        if (Objects.isNull(uniObj)) return null;

        ConditionSetType conditionSetType = this.objectFactory.createConditionSetType();
        List<ConditionType> conditionTypes =
                uniObj.getConditions().stream()
                        .map(this.conditionConverter::toXml)
                        .collect(Collectors.toList());
        conditionSetType.getCondition().addAll(conditionTypes);
        return conditionSetType;
    }
}
