package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.CaseType;
import org.uniprot.core.xml.jaxb.unirule.MainType;

public class CaseTypeConverterTest extends AbstractConverterTest {
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
