package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.impl.SamFeatureSetBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.SamFeatureSetType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(this.samTriggerConverter.fromXml(xmlObj.getSamTrigger()));

        if (Objects.nonNull(xmlObj.getConditionSet())) {
            List<Condition> conditions = xmlObj.getConditionSet().getCondition()
                    .stream()
                    .map(this.conditionConverter::fromXml)
                    .collect(Collectors.toList());
            builder.conditionsSet(conditions);
        }

        if (Objects.nonNull(xmlObj.getAnnotations())) {
            List<Annotation> annotations = xmlObj.getAnnotations().getAnnotation()
                    .stream()
                    .map(this.annotationConverter::fromXml)
                    .collect(Collectors.toList());
            builder.annotationsSet(annotations);
        }

        return builder.build();
    }

    @Override
    public SamFeatureSetType toXml(SamFeatureSet uniObj) {
        return null;
    }
}
