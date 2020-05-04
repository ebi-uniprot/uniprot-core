package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.SamFeatureSet;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.impl.SamFeatureSetBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
import org.uniprot.core.xml.jaxb.unirule.AnnotationsType;
import org.uniprot.core.xml.jaxb.unirule.SamFeatureSetType;

public class SamFeatureSetConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new SamFeatureSetConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        SamFeatureSet uniObj = createSkinnyUniObject();
        SamFeatureSetType xmlObj = (SamFeatureSetType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getSamTrigger());
        assertNull(xmlObj.getConditionSet());
        assertNull(xmlObj.getAnnotations());
        // convert back to uniObj- test round trip
        SamFeatureSet updatedUniObj = (SamFeatureSet) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private SamFeatureSet createSkinnyUniObject() {
        SamTrigger st = SamTriggerConverterTest.createSkinnyUniObject();
        SamFeatureSetBuilder builder = new SamFeatureSetBuilder(st);
        return builder.build();
    }

    public static SamFeatureSetType createObject() {
        SamFeatureSetType samFeatureSetType =
                objectCreator.createLoremIpsumObject(SamFeatureSetType.class);
        update(samFeatureSetType);
        return samFeatureSetType;
    }

    private static void update(SamFeatureSetType samFeatureSetType) {
        List<AnnotationType> annotationTypes = AnnotationConverterTest.createObjects();
        AnnotationsType annotationsType = uniRuleObjectFactory.createAnnotationsType();
        annotationsType.getAnnotation().addAll(annotationTypes);
        samFeatureSetType.setAnnotations(annotationsType);
        samFeatureSetType.setConditionSet(ConditionSetConverterTest.createObject());
    }

    public static List<SamFeatureSetType> createObjects() {
        SamFeatureSetTypeList objects =
                objectCreator.createLoremIpsumObject(SamFeatureSetTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }

    public static class SamFeatureSetTypeList extends ArrayList<SamFeatureSetType> {}
}
