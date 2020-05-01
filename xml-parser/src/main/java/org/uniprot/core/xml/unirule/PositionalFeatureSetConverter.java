package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.PositionFeatureSetBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.uniprot.core.xml.unirule.UniRuleConverterHelper.findRuleExceptionAnnotationType;

public class PositionalFeatureSetConverter
        implements Converter<PositionalFeatureSetType, PositionFeatureSet<? extends RuleExceptionAnnotationType>> {

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
    public PositionFeatureSet<? extends RuleExceptionAnnotationType> fromXml(PositionalFeatureSetType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        List<PositionalFeature> positionalFeatures =
                xmlObj.getPositionalFeature().stream()
                        .map(this.positionalFeatureConverter::fromXml)
                        .collect(Collectors.toList());

        // derive the type of annotation set in ruleexception, either annotation or positional feature
        UniRuleConverterHelper.REAnnotationEnumType reAnnotationEnumType = findRuleExceptionAnnotationType(xmlObj.getRuleExceptions());
        List<RuleException<Annotation>> annotationRuleExceptions = null;
        List<RuleException<PositionalFeature>> positionalFeatureRuleExceptions = null;

        if (Objects.nonNull(xmlObj.getRuleExceptions())) {// set either Annotation
            List<RuleExceptionType> ruleExceptionsTypes =
                    xmlObj.getRuleExceptions().getRuleException();
            if (reAnnotationEnumType == UniRuleConverterHelper.REAnnotationEnumType.ANNOTATION) {
                annotationRuleExceptions = ruleExceptionsTypes.stream()
                        .map(this.ruleExceptionConverter::fromXml)
                        .map(re -> (RuleException<Annotation>) re)
                        .collect(Collectors.toList());

            } else {// or PositionalFeature
                positionalFeatureRuleExceptions = ruleExceptionsTypes.stream()
                        .map(this.ruleExceptionConverter::fromXml)
                        .map(re -> (RuleException<PositionalFeature>) re)
                        .collect(Collectors.toList());
            }
        }
        if (reAnnotationEnumType == UniRuleConverterHelper.REAnnotationEnumType.ANNOTATION) {
            PositionFeatureSetBuilder<Annotation> builder = new PositionFeatureSetBuilder<>(positionalFeatures);
            builder.ruleExceptionsSet(annotationRuleExceptions);
            populateBuilder(xmlObj, builder);
            return builder.build();
        } else {
            PositionFeatureSetBuilder<PositionalFeature> builder = new PositionFeatureSetBuilder<>(positionalFeatures);
            builder.uniProtKBAccession(this.accessionConverter.fromXml(xmlObj.getTemplate()));
            builder.ruleExceptionsSet(positionalFeatureRuleExceptions);
            populateBuilder(xmlObj, builder);
            return builder.build();
        }
    }


    @Override
    public PositionalFeatureSetType toXml(PositionFeatureSet<? extends RuleExceptionAnnotationType> uniObj) {
        if (Objects.isNull(uniObj)) return null;

        PositionalFeatureSetType positionalFeatureSetType =
                this.objectFactory.createPositionalFeatureSetType();
        positionalFeatureSetType.setAlignmentSignature(uniObj.getAlignmentSignature());
        positionalFeatureSetType.setTag(uniObj.getTag());
        positionalFeatureSetType.setTemplate(
                this.accessionConverter.toXml(uniObj.getUniProtKBAccession()));

        List<Condition> conditions = uniObj.getConditions();
        List<ConditionType> conditionTypes =
                conditions.stream()
                        .map(this.conditionConverter::toXml)
                        .collect(Collectors.toList());
        ConditionSetType conditionSetType = this.objectFactory.createConditionSetType();
        conditionSetType.getCondition().addAll(conditionTypes);
        positionalFeatureSetType.setConditionSet(conditionSetType);

        List<Annotation> annotations = uniObj.getAnnotations();
        List<AnnotationType> annotationTypes =
                annotations.stream()
                        .map(this.annotationConverter::toXml)
                        .collect(Collectors.toList());
        AnnotationsType annotationsType = this.objectFactory.createAnnotationsType();
        annotationsType.getAnnotation().addAll(annotationTypes);
        positionalFeatureSetType.setAnnotations(annotationsType);

        List<PositionalFeature> positionalFeatures = uniObj.getPositionalFeatures();
        List<PositionalFeatureType> positionalFeatureTypes =
                positionalFeatures.stream()
                        .map(this.positionalFeatureConverter::toXml)
                        .collect(Collectors.toList());
        positionalFeatureSetType.getPositionalFeature().addAll(positionalFeatureTypes);

        RuleExceptionsType ruleExceptionsType = this.objectFactory.createRuleExceptionsType();
        List<? extends RuleException<? extends RuleExceptionAnnotationType>> ruleExceptions = uniObj.getRuleExceptions();
        ruleExceptionsType
                .getRuleException()
                .addAll(
                        ruleExceptions.stream()
                                .map(this.ruleExceptionConverter::toXml)
                                .collect(Collectors.toList()));
        positionalFeatureSetType.setRuleExceptions(ruleExceptionsType);

        return positionalFeatureSetType;
    }

    private void populateBuilder(PositionalFeatureSetType xmlObj, PositionFeatureSetBuilder<? extends RuleExceptionAnnotationType> builder) {
        builder.uniProtKBAccession(this.accessionConverter.fromXml(xmlObj.getTemplate()));
        builder.alignmentSignature(xmlObj.getAlignmentSignature());
        builder.tag(xmlObj.getTag());
        builder.annotationsSet(getAnnotations(xmlObj));
        builder.conditionsSet(getConditions(xmlObj));
    }


    private List<Annotation> getAnnotations(PositionalFeatureSetType xmlObj) {
        List<Annotation> annotations = null;
        if (Objects.nonNull(xmlObj.getAnnotations())) {
            annotations =
                    xmlObj.getAnnotations().getAnnotation().stream()
                            .map(this.annotationConverter::fromXml)
                            .collect(Collectors.toList());
        }
        return annotations;
    }

    private List<Condition> getConditions(PositionalFeatureSetType xmlObj) {
        List<Condition> conditions = null;
        if (Objects.nonNull(xmlObj.getConditionSet())) {
            conditions =
                    xmlObj.getConditionSet().getCondition().stream()
                            .map(this.conditionConverter::fromXml)
                            .collect(Collectors.toList());
        }

        return conditions;
    }


}
