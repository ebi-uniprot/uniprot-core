package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.PositionFeatureSetBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureSetType;

public class PositionalFeatureSetConverter
        implements Converter<PositionalFeatureSetType, PositionFeatureSet> {

    private final ObjectFactory objectFactory;
    private final PositionalFeatureConverter positionalFeatureConverter;
    private final AnnotationConverter annotationConverter;
    private final ConditionConverter conditionConverter;
    private final UniProtKBAccessionConverter accessionConverter;
    private final RuleExceptionConverter ruleExceptionConverter;

    public PositionalFeatureSetConverter() {
        this(new ObjectFactory());
    }

    public PositionalFeatureSetConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.positionalFeatureConverter = new PositionalFeatureConverter(objectFactory);
        this.annotationConverter = new AnnotationConverter(objectFactory);
        this.conditionConverter = new ConditionConverter(objectFactory);
        this.accessionConverter = new UniProtKBAccessionConverter(objectFactory);
        this.ruleExceptionConverter = new RuleExceptionConverter(objectFactory);
    }

    @Override
    public PositionFeatureSet fromXml(PositionalFeatureSetType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        List<PositionalFeature> positionalFeatures =
                xmlObj.getPositionalFeature().stream()
                        .map(this.positionalFeatureConverter::fromXml)
                        .collect(Collectors.toList());

        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(positionalFeatures);

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

        if (Objects.nonNull(xmlObj.getRuleExceptions())) {
            List<RuleException> ruleExceptions =
                    xmlObj.getRuleExceptions().getRuleException().stream()
                            .map(this.ruleExceptionConverter::fromXml)
                            .collect(Collectors.toList());
            builder.ruleExceptionsSet(ruleExceptions);
        }

        builder.uniProtKBAccession(this.accessionConverter.fromXml(xmlObj.getTemplate()));
        builder.alignmentSignature(xmlObj.getAlignmentSignature());
        builder.tag(xmlObj.getTag());

        return null;
    }

    @Override
    public PositionalFeatureSetType toXml(PositionFeatureSet uniObj) {
        return null;
    }
}
