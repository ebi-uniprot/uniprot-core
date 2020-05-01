package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.CaseRuleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.uniprot.core.xml.unirule.UniRuleConverterHelper.findRuleExceptionAnnotationType;

public class CaseTypeConverter implements Converter<CaseType, CaseRule<? extends RuleExceptionAnnotationType>> {

    private final ObjectFactory objectFactory;
    private final MainTypeConverter mainTypeConverter;
    private final AnnotationConverter annotationConverter;
    private final RuleExceptionConverter ruleExceptionConverter;
    private final ConditionSetConverter conditionSetConverter;

    public CaseTypeConverter() {
        this(new ObjectFactory());
    }

    public CaseTypeConverter(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
        this.mainTypeConverter = new MainTypeConverter(objectFactory);
        this.annotationConverter = new AnnotationConverter(objectFactory);
        this.ruleExceptionConverter = new RuleExceptionConverter(objectFactory);
        this.conditionSetConverter = new ConditionSetConverter(objectFactory);
    }

    @Override
    public CaseRule<? extends RuleExceptionAnnotationType> fromXml(CaseType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;
        // derive the type of annotation set in ruleexception, either annotation or positional feature
        UniRuleConverterHelper.REAnnotationEnumType reAnnotationEnumType = findRuleExceptionAnnotationType(xmlObj.getRuleExceptions());
        if (reAnnotationEnumType == UniRuleConverterHelper.REAnnotationEnumType.ANNOTATION) {
            Rule<Annotation> mainRule = (Rule<Annotation>) this.mainTypeConverter.fromXml(xmlObj);
            CaseRuleBuilder<Annotation> builder = new CaseRuleBuilder<>(mainRule.getConditionSets());
            builder.overallStatsExempted(xmlObj.isOverallStatsExempted());
            builder.annotationsSet(mainRule.getAnnotations());
            builder.ruleExceptionsSet(mainRule.getRuleExceptions());
            return builder.build();
        } else {
            Rule<PositionalFeature> mainRule = (Rule<PositionalFeature>) this.mainTypeConverter.fromXml(xmlObj);
            CaseRuleBuilder<PositionalFeature> builder = new CaseRuleBuilder<>(mainRule.getConditionSets());
            builder.overallStatsExempted(xmlObj.isOverallStatsExempted());
            builder.annotationsSet(mainRule.getAnnotations());
            builder.ruleExceptionsSet(mainRule.getRuleExceptions());
            return builder.build();
        }
    }

    @Override
    public CaseType toXml(CaseRule<? extends RuleExceptionAnnotationType> uniObj) {
        if (Objects.isNull(uniObj)) return null;

        CaseType caseType = this.objectFactory.createCaseType();
        caseType.setOverallStatsExempted(uniObj.isOverallStatsExempted());

        List<Annotation> annotations = uniObj.getAnnotations();
        AnnotationsType annotationsType = this.objectFactory.createAnnotationsType();
        annotationsType
                .getAnnotation()
                .addAll(
                        annotations.stream()
                                .map(this.annotationConverter::toXml)
                                .collect(Collectors.toList()));
        caseType.setAnnotations(annotationsType);

        List<ConditionSet> conditionSets = uniObj.getConditionSets();
        List<ConditionSetType> conditionSetTypes =
                conditionSets.stream()
                        .map(this.conditionSetConverter::toXml)
                        .collect(Collectors.toList());
        MainType.ConditionSets mainTypeConditionSets =
                this.objectFactory.createMainTypeConditionSets();
        mainTypeConditionSets.getConditionSet().addAll(conditionSetTypes);
        caseType.setConditionSets(mainTypeConditionSets);

        List<? extends RuleException<? extends RuleExceptionAnnotationType>> ruleExceptions = uniObj.getRuleExceptions();
        List<RuleExceptionType> ruleExceptionTypes =
                ruleExceptions.stream()
                        .map(this.ruleExceptionConverter::toXml)
                        .collect(Collectors.toList());
        RuleExceptionsType ruleExceptionsType = this.objectFactory.createRuleExceptionsType();
        ruleExceptionsType.getRuleException().addAll(ruleExceptionTypes);
        caseType.setRuleExceptions(ruleExceptionsType);

        return caseType;
    }
}
