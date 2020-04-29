package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.ArrayList;
import java.util.List;

public class MainTypeConverterTest extends AbstractConverterTest {
    public static MainType createObject() {
        MainType mainType = objectCreator.createLoremIpsumObject(MainType.class);
        // fill list types
        List<ConditionSetType> conditionSetTypes = ConditionSetConverterTest.createObjects();
        MainType.ConditionSets conditionSets = uniRuleObjectFactory.createMainTypeConditionSets();
        conditionSets.getConditionSet().addAll(conditionSetTypes);
        mainType.setConditionSets(conditionSets);

        List<AnnotationType> annotationTypes = AnnotationConverterTest.createObjects();
        AnnotationsType annotationsType = uniRuleObjectFactory.createAnnotationsType();
        annotationsType.getAnnotation().addAll(annotationTypes);
        mainType.setAnnotations(annotationsType);

        List<RuleExceptionType> ruleExceptionTypes = RuleExceptionConverterTest.createObjects();
        RuleExceptionsType ruleExceptionsType = uniRuleObjectFactory.createRuleExceptionsType();
        ruleExceptionsType.getRuleException().addAll(ruleExceptionTypes);
        mainType.setRuleExceptions(ruleExceptionsType);

        return mainType;
    }

    public static List<MainType> createObjects() {
        return objectCreator.createLoremIpsumObject(MainTypeList.class);
    }

    public static class MainTypeList extends ArrayList<MainType> {
    }
}
