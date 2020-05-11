package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.RuleException;
import org.uniprot.core.unirule.impl.RuleExceptionBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.RuleExceptionType;

import java.util.ArrayList;
import java.util.List;

public class RuleExceptionConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new RuleExceptionConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        RuleException uniObj = createSkinnyUniObject();
        RuleExceptionType xmlObj = (RuleExceptionType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getCategory());
        assertNull(xmlObj.getNote());
        assertNull(xmlObj.getAnnotation());
        assertNull(xmlObj.getPositionalFeature());
        assertTrue(xmlObj.getAccession().isEmpty());
        // convert back to uniObj- test round trip
        RuleException updatedUniObj = (RuleException) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private RuleException createSkinnyUniObject() {
        RuleExceptionBuilder builder = new RuleExceptionBuilder("cat");
        return builder.build();
    }

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
