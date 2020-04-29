package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;

public class RuleExceptionConverterTest extends AbstractConverterTest {
    public static class RuleExceptionTypeList extends ArrayList<RuleExceptionType> {}

    public static RuleExceptionType createObject() {
        RuleExceptionType ruleExceptionType =
                objectCreator.createLoremIpsumObject(RuleExceptionType.class);
        update(ruleExceptionType);
        return ruleExceptionType;
    }

    private static void update(RuleExceptionType ruleExceptionType) {
        ruleExceptionType.setAnnotation(AnnotationConverterTest.createObject());
        // fill list type
        List<String> accessions =
                objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        ruleExceptionType.getAccession().addAll(accessions);
    }

    public static List<RuleExceptionType> createObjects() {
        RuleExceptionTypeList objects =
                objectCreator.createLoremIpsumObject(RuleExceptionTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }
}
