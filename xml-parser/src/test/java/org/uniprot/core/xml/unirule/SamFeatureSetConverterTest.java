package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
import org.uniprot.core.xml.jaxb.unirule.AnnotationsType;
import org.uniprot.core.xml.jaxb.unirule.SamFeatureSetType;

public class SamFeatureSetConverterTest extends AbstractConverterTest {

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
