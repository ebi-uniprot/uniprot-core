package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalConditionType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

public class PositionalFeatureConverterTest extends AbstractConverterTest {

    public static PositionalFeatureType createObject() {
        PositionalFeatureType positionalFeatureType =
                objectCreator.createLoremIpsumObject(PositionalFeatureType.class);
        // update range values to be parseable int
        updateField(positionalFeatureType.getPositionalCondition());
        return positionalFeatureType;
    }

    public static List<PositionalFeatureType> createObjects() {
        PositionalFeatureTypeList objects =
                objectCreator.createLoremIpsumObject(PositionalFeatureTypeList.class);
        objects.forEach(object -> updateField(object.getPositionalCondition()));
        return objects;
    }

    private static void updateField(PositionalConditionType positionalCondition) {
        positionalCondition.setPosition(RangeConverterTest.createObject());
    }

    public static class PositionalFeatureTypeList extends ArrayList<PositionalFeatureType> {}
}
