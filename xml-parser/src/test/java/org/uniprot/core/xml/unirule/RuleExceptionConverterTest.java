package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;

import java.util.ArrayList;
import java.util.List;

public class RuleExceptionConverterTest extends AbstractConverterTest {
    public static class RuleExceptionTypeList extends ArrayList<RuleExceptionType> {
    }

    public static RuleExceptionType createObject() {
        RuleExceptionType ruleExceptionType = objectCreator.createLoremIpsumObject(RuleExceptionType.class);
        ruleExceptionType.setAnnotation(AnnotationConverterTest.createObject());
        // fill list type
        List<String> accessions = objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        ruleExceptionType.getAccession().addAll(accessions);
        return ruleExceptionType;
    }

    public static List<RuleExceptionType> createObjects() {
        return objectCreator.createLoremIpsumObject(RuleExceptionTypeList.class);
    }
}
