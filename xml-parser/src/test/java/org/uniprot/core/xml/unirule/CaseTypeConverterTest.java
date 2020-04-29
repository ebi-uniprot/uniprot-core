package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.CaseType;
import org.uniprot.core.xml.jaxb.unirule.MainType;

import java.util.ArrayList;
import java.util.List;

public class CaseTypeConverterTest extends AbstractConverterTest {
    public static CaseType createObject() {
        CaseType caseType = objectCreator.createLoremIpsumObject(CaseType.class);
        MainType mainType = MainTypeConverterTest.createObject();
        caseType.setRuleExceptions(mainType.getRuleExceptions());
        caseType.setAnnotations(mainType.getAnnotations());
        caseType.setConditionSets(mainType.getConditionSets());
        return caseType;
    }


    public static List<CaseType> createObjects() {
        return objectCreator.createLoremIpsumObject(CaseTypeList.class);

    }

    public static class CaseTypeList extends ArrayList<CaseType> {
    }
}
