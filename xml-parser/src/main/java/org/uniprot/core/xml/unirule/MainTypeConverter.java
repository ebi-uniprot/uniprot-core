package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.RuleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

import static org.uniprot.core.xml.unirule.UniRuleConverterHelper.*;

public class MainTypeConverter implements Converter<MainType, Rule<? extends RuleExceptionAnnotationType>> {

    private final ObjectFactory objectFactory;
    private final ConditionSetConverter conditionSetConverter;
    private final AnnotationConverter annotationConverter;
    private final RuleExceptionConverter ruleExceptionConverter;

    public MainTypeConverter() {
        this(new ObjectFactory());
    }

    public MainTypeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.conditionSetConverter = new ConditionSetConverter(objectFactory);
        this.annotationConverter = new AnnotationConverter(objectFactory);
        this.ruleExceptionConverter = new RuleExceptionConverter(objectFactory);
    }

    @Override
    public Rule<? extends RuleExceptionAnnotationType> fromXml(MainType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        List<ConditionSet> conditionSets =
                xmlObj.getConditionSets().getConditionSet().stream()
                        .map(this.conditionSetConverter::fromXml)
                        .collect(Collectors.toList());

        List<Annotation> annotations = null;
        if (Objects.nonNull(xmlObj.getAnnotations())) {
            annotations =
                    xmlObj.getAnnotations().getAnnotation().stream()
                            .map(this.annotationConverter::fromXml)
                            .collect(Collectors.toList());
        }

        if (Objects.nonNull(xmlObj.getRuleExceptions())) {

        }
        // derive the type of annotation set in ruleexception, either annotation or positional feature
        REAnnotationEnumType reAnnotationEnumType = findRuleExceptionAnnotationType(xmlObj.getRuleExceptions());
        List<RuleException<Annotation>> annotationRuleExceptions = null;
        List<RuleException<PositionalFeature>> positionalFeatureRuleExceptions = null;

        if (Objects.nonNull(xmlObj.getRuleExceptions())) {// set either Annotation
            List<RuleExceptionType> ruleExceptionsTypes =
                    xmlObj.getRuleExceptions().getRuleException();
            if (reAnnotationEnumType == REAnnotationEnumType.ANNOTATION) {
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

        if (reAnnotationEnumType == REAnnotationEnumType.ANNOTATION) {
            RuleBuilder<Annotation> ruleBuilder = new RuleBuilder<>(conditionSets);
            ruleBuilder.annotationsSet(annotations);
            ruleBuilder.ruleExceptionsSet(annotationRuleExceptions);
            return ruleBuilder.build();
        } else {
            RuleBuilder<PositionalFeature> ruleBuilder = new RuleBuilder<>(conditionSets);
            ruleBuilder.annotationsSet(annotations);
            ruleBuilder.ruleExceptionsSet(positionalFeatureRuleExceptions);
            return ruleBuilder.build();
        }
    }

    @Override
    public MainType toXml(Rule<? extends RuleExceptionAnnotationType> uniObj) {
        if (Objects.isNull(uniObj)) return null;

        MainType mainType = this.objectFactory.createMainType();
        AnnotationsType annotationsType = this.objectFactory.createAnnotationsType();
        List<Annotation> annotations = uniObj.getAnnotations();
        List<AnnotationType> annotationTypes =
                annotations.stream()
                        .filter(annotation -> annotation instanceof Annotation)
                        .map(this.annotationConverter::toXml)
                        .collect(Collectors.toList());
        annotationsType.getAnnotation().addAll(annotationTypes);
        mainType.setAnnotations(annotationsType);

        MainType.ConditionSets conditionsSet = this.objectFactory.createMainTypeConditionSets();
        List<ConditionSet> conditionSets = uniObj.getConditionSets();
        conditionsSet
                .getConditionSet()
                .addAll(
                        conditionSets.stream()
                                .map(this.conditionSetConverter::toXml)
                                .collect(Collectors.toList()));
        mainType.setConditionSets(conditionsSet);

        RuleExceptionsType ruleExceptionsType = this.objectFactory.createRuleExceptionsType();
        List<? extends RuleException<? extends RuleExceptionAnnotationType>> ruleExceptions = uniObj.getRuleExceptions();
        ruleExceptionsType
                .getRuleException()
                .addAll(
                        ruleExceptions.stream()
                                .map(this.ruleExceptionConverter::toXml)
                                .collect(Collectors.toList()));
        mainType.setRuleExceptions(ruleExceptionsType);
        return mainType;
    }
}
