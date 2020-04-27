package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.impl.CaseRuleBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.*;

public class CaseTypeConverter implements Converter<CaseType, CaseRule> {

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
    public CaseRule fromXml(CaseType xmlObj) {
        if (Objects.isNull(xmlObj)) return null;

        CaseRuleBuilder<Annotation> builder =
                new CaseRuleBuilder<Annotation>(
                        this.mainTypeConverter.fromXml(xmlObj).getConditionSets());
        builder.overallStatsExempted(xmlObj.isOverallStatsExempted());
        return builder.build();
    }

    @Override
    public CaseType toXml(CaseRule uniObj) {
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

        List<RuleException> ruleExceptions = uniObj.getRuleExceptions();
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
