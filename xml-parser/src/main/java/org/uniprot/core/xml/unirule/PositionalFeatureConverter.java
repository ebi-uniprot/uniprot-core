package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.impl.PositionalFeatureBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

public class PositionalFeatureConverter
        implements Converter<PositionalFeatureType, PositionalFeature> {
    private final ObjectFactory objectFactory;

    public PositionalFeatureConverter() {
        this(new ObjectFactory());
    }

    public PositionalFeatureConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public PositionalFeature fromXml(PositionalFeatureType xmlObj) {
        RangeType xmlPosition = xmlObj.getPositionalCondition().getPosition();
        Range position =
                new Range(
                        Integer.parseInt(xmlPosition.getStart()),
                        Integer.parseInt(xmlPosition.getEnd()));
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder(position);
        builder.pattern(xmlObj.getPositionalCondition().getPattern());
        if (Objects.nonNull(xmlObj.getPositionalAnnotation())) {
            builder.value(xmlObj.getPositionalAnnotation().getValue());
            builder.type(xmlObj.getPositionalAnnotation().getType());
        }
        builder.inGroup(xmlObj.isInGroup());
        return builder.build();
    }

    @Override
    public PositionalFeatureType toXml(PositionalFeature uniObj) {
        PositionalFeatureType positionalFeatureType =
                this.objectFactory.createPositionalFeatureType();
        PositionalConditionType positionalConditionType =
                this.objectFactory.createPositionalConditionType();
        positionalConditionType.setPattern(uniObj.getPattern());
        RangeType position = this.objectFactory.createRangeType();
        position.setStart(String.valueOf(uniObj.getPosition().getStart()));
        position.setEnd(String.valueOf(uniObj.getPosition().getEnd()));
        positionalConditionType.setPosition(position);
        positionalFeatureType.setPositionalCondition(positionalConditionType);
        PositionalAnnotationType positionalAnnotationType =
                this.objectFactory.createPositionalAnnotationType();
        positionalAnnotationType.setType(uniObj.getType());
        positionalAnnotationType.setValue(uniObj.getValue());
        positionalFeatureType.setPositionalAnnotation(positionalAnnotationType);
        positionalFeatureType.setInGroup(uniObj.isInGroup());
        return positionalFeatureType;
    }
}
