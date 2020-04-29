package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureSetType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

public class PositionalFeatureSetConverterTest extends AbstractConverterTest {

    public static PositionalFeatureSetType createObject() {
        PositionalFeatureSetType positionalFeatureSetType =
                objectCreator.createLoremIpsumObject(PositionalFeatureSetType.class);
        update(positionalFeatureSetType);
        return positionalFeatureSetType;
    }

    public static List<PositionalFeatureSetType> createObjects() {
        PositionalFeatureSetTypeList objects =
                objectCreator.createLoremIpsumObject(PositionalFeatureSetTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }

    private static void update(PositionalFeatureSetType positionalFeatureSetType) {
        // fill the list type
        List<PositionalFeatureType> positionalFeatureTypes =
                PositionalFeatureConverterTest.createObjects();
        positionalFeatureSetType.getPositionalFeature().addAll(positionalFeatureTypes);
        positionalFeatureSetType
                .getConditionSet()
                .getCondition()
                .addAll(ConditionConverterTest.createObjects());
        positionalFeatureSetType
                .getAnnotations()
                .getAnnotation()
                .addAll(AnnotationConverterTest.createObjects());
        positionalFeatureSetType
                .getRuleExceptions()
                .getRuleException()
                .addAll(RuleExceptionConverterTest.createObjects());
    }

    public static class PositionalFeatureSetTypeList extends ArrayList<PositionalFeatureSetType> {}
}
