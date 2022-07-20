package org.uniprot.core.xml.unirule;

import java.util.Objects;

import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.impl.PositionalFeatureBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;
import org.uniprot.core.xml.uniprot.LigandConverter;
import org.uniprot.core.xml.uniprot.LigandPartConverter;

public class PositionalFeatureConverter
        implements Converter<PositionalFeatureType, PositionalFeature> {
    private final ObjectFactory objectFactory;
    private final org.uniprot.core.xml.jaxb.uniprot.ObjectFactory uniProtObjectFactory;
    private final LigandConverter ligandConverter;
    private final LigandPartConverter ligandPartConverter;

    public PositionalFeatureConverter() {
        this(new ObjectFactory());
    }

    public PositionalFeatureConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.uniProtObjectFactory = new org.uniprot.core.xml.jaxb.uniprot.ObjectFactory();
        this.ligandConverter = new LigandConverter(uniProtObjectFactory);
        this.ligandPartConverter = new LigandPartConverter(uniProtObjectFactory);
    }

    @Override
    public PositionalFeature fromXml(PositionalFeatureType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        RangeType xmlPosition = xmlObj.getPositionalCondition().getPosition();

        if (!isStringIntParsable(xmlPosition.getStart())
                || !isStringIntParsable(xmlPosition.getEnd())) return null;

        Range position =
                new Range(
                        Integer.parseInt(xmlPosition.getStart()),
                        Integer.parseInt(xmlPosition.getEnd()));
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder(position);
        builder.pattern(xmlObj.getPositionalCondition().getPattern());
        if (Objects.nonNull(xmlObj.getPositionalAnnotation())) {
            builder.description(xmlObj.getPositionalAnnotation().getDescription());
            if (Objects.nonNull(xmlObj.getPositionalAnnotation().getLigand())) {
                builder.ligand(
                        ligandConverter.fromXml(xmlObj.getPositionalAnnotation().getLigand()));
            }
            if (Objects.nonNull(xmlObj.getPositionalAnnotation().getLigandPart())) {
                builder.ligandPart(
                        ligandPartConverter.fromXml(
                                xmlObj.getPositionalAnnotation().getLigandPart()));
            }
            builder.description(xmlObj.getPositionalAnnotation().getDescription());
            builder.type(xmlObj.getPositionalAnnotation().getType());
        }
        builder.inGroup(xmlObj.isInGroup());
        return builder.build();
    }

    @Override
    public PositionalFeatureType toXml(PositionalFeature uniObj) {
        if (Objects.isNull(uniObj)) return null;

        PositionalFeatureType positionalFeatureType =
                this.objectFactory.createPositionalFeatureType();
        PositionalConditionType positionalConditionType =
                this.objectFactory.createPositionalConditionType();
        positionalConditionType.setPattern(uniObj.getPattern());
        RangeType position = this.objectFactory.createRangeType();
        if (Objects.nonNull(uniObj.getPosition().getStart())) {
            position.setStart(String.valueOf(uniObj.getPosition().getStart().getValue()));
        }
        if (Objects.nonNull(uniObj.getPosition().getEnd())) {
            position.setEnd(String.valueOf(uniObj.getPosition().getEnd().getValue()));
        }
        positionalConditionType.setPosition(position);
        positionalFeatureType.setPositionalCondition(positionalConditionType);
        PositionalAnnotationType positionalAnnotationType =
                this.objectFactory.createPositionalAnnotationType();
        positionalAnnotationType.setType(uniObj.getType());
        positionalAnnotationType.setDescription(uniObj.getDescription());
        if (Objects.nonNull(uniObj.getLigand())) {
            positionalAnnotationType.setLigand(ligandConverter.toXml(uniObj.getLigand()));
        }
        if (Objects.nonNull(uniObj.getLigandPart())) {
            positionalAnnotationType.setLigandPart(
                    ligandPartConverter.toXml(uniObj.getLigandPart()));
        }
        positionalFeatureType.setPositionalAnnotation(positionalAnnotationType);
        positionalFeatureType.setInGroup(uniObj.isInGroup());
        return positionalFeatureType;
    }

    private boolean isStringIntParsable(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
