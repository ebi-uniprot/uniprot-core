package org.uniprot.core.xml.unirule;

import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionsType;

import java.util.List;
import java.util.Objects;

public class UniRuleConverterHelper {
    public enum REAnnotationEnumType {ANNOTATION, POSITIONAL_FEATURE}

    public static REAnnotationEnumType findRuleExceptionAnnotationType(RuleExceptionType ruleExceptionType){
        if(Objects.nonNull(ruleExceptionType) && Objects.nonNull(ruleExceptionType.getPositionalFeature())){
            return REAnnotationEnumType.POSITIONAL_FEATURE;
        } else {
            return REAnnotationEnumType.ANNOTATION; // default typ
        }
    }

    public static REAnnotationEnumType findRuleExceptionAnnotationType(List<RuleExceptionType> ruleExceptionTypes){
        // just check one item and decide the type
        if(Utils.notNullNotEmpty(ruleExceptionTypes)){
            return findRuleExceptionAnnotationType(ruleExceptionTypes.get(0));
        } else {
            return REAnnotationEnumType.ANNOTATION; // default type
        }
    }

    public static REAnnotationEnumType findRuleExceptionAnnotationType(RuleExceptionsType ruleExceptionsType){
        // just check one item and decide the type
        if(Objects.nonNull(ruleExceptionsType)){
            return findRuleExceptionAnnotationType(ruleExceptionsType.getRuleException());
        } else {
            return REAnnotationEnumType.ANNOTATION; // default type
        }
    }
}
