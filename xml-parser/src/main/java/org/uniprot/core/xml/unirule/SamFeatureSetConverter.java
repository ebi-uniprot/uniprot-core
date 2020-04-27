package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.impl.SamFeatureSetBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

public class SamFeatureSetConverter implements Converter<SamFeatureSetType, SamFeatureSet> {

    private final ObjectFactory objectFactory;
    private final SamTriggerConverter samTriggerConverter;
    private final ConditionConverter conditionConverter;
    private final AnnotationConverter annotationConverter;

    public SamFeatureSetConverter() {
        this(new ObjectFactory());
    }

    public SamFeatureSetConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.samTriggerConverter = new SamTriggerConverter(objectFactory);
        this.conditionConverter = new ConditionConverter(objectFactory);
        this.annotationConverter = new AnnotationConverter(objectFactory);
    }

    @Override
    public SamFeatureSet fromXml(SamFeatureSetType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        SamFeatureSetBuilder builder =
                new SamFeatureSetBuilder(this.samTriggerConverter.fromXml(xmlObj.getSamTrigger()));

        if (Objects.nonNull(xmlObj.getConditionSet())) {
            List<Condition> conditions =
                    xmlObj.getConditionSet().getCondition().stream()
                            .map(this.conditionConverter::fromXml)
                            .collect(Collectors.toList());
            builder.conditionsSet(conditions);
        }

        if (Objects.nonNull(xmlObj.getAnnotations())) {
            List<Annotation> annotations =
                    xmlObj.getAnnotations().getAnnotation().stream()
                            .map(this.annotationConverter::fromXml)
                            .collect(Collectors.toList());
            builder.annotationsSet(annotations);
        }

        return builder.build();
    }

    @Override
    public SamFeatureSetType toXml(SamFeatureSet uniObj) {
        if (Objects.isNull(uniObj)) return null;

        SamFeatureSetType samFeatureSetType = this.objectFactory.createSamFeatureSetType();
        samFeatureSetType.setSamTrigger(this.samTriggerConverter.toXml(uniObj.getSamTrigger()));

        List<Condition> conditions = uniObj.getConditions();
        List<ConditionType> conditionTypes =
                conditions.stream()
                        .map(this.conditionConverter::toXml)
                        .collect(Collectors.toList());
        ConditionSetType conditionSetType = this.objectFactory.createConditionSetType();
        conditionSetType.getCondition().addAll(conditionTypes);
        samFeatureSetType.setConditionSet(conditionSetType);

        List<Annotation> annotations = uniObj.getAnnotations();
        List<AnnotationType> annotationTypes =
                annotations.stream()
                        .map(this.annotationConverter::toXml)
                        .collect(Collectors.toList());
        AnnotationsType annotationsType = this.objectFactory.createAnnotationsType();
        annotationsType.getAnnotation().addAll(annotationTypes);
        samFeatureSetType.setAnnotations(annotationsType);

        return samFeatureSetType;
    }
}
