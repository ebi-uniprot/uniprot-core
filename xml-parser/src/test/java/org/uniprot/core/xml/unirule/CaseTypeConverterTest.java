package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.CaseRule;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.impl.CaseRuleBuilder;
import org.uniprot.core.unirule.impl.ConditionSetBuilderTest;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.CaseType;
import org.uniprot.core.xml.jaxb.unirule.MainType;

public class CaseTypeConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new CaseTypeConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        CaseRule uniObj = createSkinnyUniObject();
        CaseType xmlObj = (CaseType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getConditionSets());
        assertNull(xmlObj.getAnnotations());
        assertNull(xmlObj.getRuleExceptions());
        assertFalse(xmlObj.isOverallStatsExempted());
        // convert back to uniObj- test round trip
        CaseRule updatedUniObj = (CaseRule) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private CaseRule createSkinnyUniObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        CaseRuleBuilder builder = new CaseRuleBuilder(conditionSets);
        return builder.build();
    }

    public static CaseType createObject() {
        CaseType caseType = objectCreator.createLoremIpsumObject(CaseType.class);
        update(caseType);
        return caseType;
    }

    public static List<CaseType> createObjects() {
        List<CaseType> objects = objectCreator.createLoremIpsumObject(CaseTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }

    private static void update(CaseType caseType) {
        MainType mainType = MainTypeConverterTest.createObject();
        caseType.setRuleExceptions(mainType.getRuleExceptions());
        caseType.setAnnotations(mainType.getAnnotations());
        caseType.setConditionSets(mainType.getConditionSets());
    }

    public static class CaseTypeList extends ArrayList<CaseType> {}
}
