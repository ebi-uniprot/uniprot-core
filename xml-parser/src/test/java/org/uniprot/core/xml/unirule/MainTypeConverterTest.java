package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.Rule;
import org.uniprot.core.unirule.impl.ConditionSetBuilderTest;
import org.uniprot.core.unirule.impl.RuleBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.ArrayList;
import java.util.List;

public class MainTypeConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new MainTypeConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        Rule uniObj = createSkinnyUniObject();
        MainType xmlObj = (MainType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getConditionSets());
        assertNull(xmlObj.getAnnotations());
        assertNull(xmlObj.getRuleExceptions());
        // convert back to uniObj- test round trip
        Rule updatedUniObj = (Rule) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    public static Rule createSkinnyUniObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        RuleBuilder builder = new RuleBuilder(conditionSets);
        return builder.build();
    }

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

    public static class MainTypeList extends ArrayList<MainType> {}
}
