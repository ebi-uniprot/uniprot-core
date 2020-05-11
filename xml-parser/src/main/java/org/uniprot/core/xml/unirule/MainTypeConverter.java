package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.impl.RuleBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainTypeConverter implements Converter<MainType, Rule> {

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
    public Rule fromXml(MainType xmlObj) {
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
        List<RuleException> ruleExceptions = null;

        if (Objects.nonNull(xmlObj.getRuleExceptions())) {
            List<RuleExceptionType> ruleExceptionsTypes =
                    xmlObj.getRuleExceptions().getRuleException();
            ruleExceptions =
                    ruleExceptionsTypes.stream()
                            .map(this.ruleExceptionConverter::fromXml)
                            .collect(Collectors.toList());
        }

        RuleBuilder ruleBuilder = new RuleBuilder(conditionSets);
        ruleBuilder.annotationsSet(annotations);
        ruleBuilder.ruleExceptionsSet(ruleExceptions);

        return ruleBuilder.build();
    }

    @Override
    public MainType toXml(Rule uniObj) {
        if (Objects.isNull(uniObj)) return null;

        MainType mainType = this.objectFactory.createMainType();
        AnnotationsType annotationsType = this.objectFactory.createAnnotationsType();
        List<Annotation> annotations = uniObj.getAnnotations();

        if (Utils.notNullNotEmpty(annotations)) {
            List<AnnotationType> annotationTypes =
                    annotations.stream()
                            .map(this.annotationConverter::toXml)
                            .collect(Collectors.toList());
            annotationsType.getAnnotation().addAll(annotationTypes);
            mainType.setAnnotations(annotationsType);
        }

        MainType.ConditionSets conditionsSet = this.objectFactory.createMainTypeConditionSets();
        List<ConditionSet> conditionSets = uniObj.getConditionSets();
        conditionsSet
                .getConditionSet()
                .addAll(
                        conditionSets.stream()
                                .map(this.conditionSetConverter::toXml)
                                .collect(Collectors.toList()));
        mainType.setConditionSets(conditionsSet);

        List<RuleException> ruleExceptions = uniObj.getRuleExceptions();
        if (Utils.notNullNotEmpty(ruleExceptions)) {
            RuleExceptionsType ruleExceptionsType = this.objectFactory.createRuleExceptionsType();
            ruleExceptionsType
                    .getRuleException()
                    .addAll(
                            ruleExceptions.stream()
                                    .map(this.ruleExceptionConverter::toXml)
                                    .collect(Collectors.toList()));
            mainType.setRuleExceptions(ruleExceptionsType);
        }
        return mainType;
    }
}
