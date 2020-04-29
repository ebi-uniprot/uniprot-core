package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalConditionType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PositionalFeatureConverterTest extends AbstractConverterTest {

    public static PositionalFeatureType createObject() {
        PositionalFeatureType positionalFeatureType = objectCreator.createLoremIpsumObject(PositionalFeatureType.class);
        // update range values to be parseable int
        updateField(positionalFeatureType.getPositionalCondition());
        return positionalFeatureType;
    }


    public static List<PositionalFeatureType> createObjects() {
        PositionalFeatureTypeList objects = objectCreator.createLoremIpsumObject(PositionalFeatureTypeList.class);
        objects.forEach(object -> updateField(object.getPositionalCondition()));
        return objects;
    }

    private static void updateField(PositionalConditionType positionalCondition) {
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        positionalCondition.getPosition().setStart(String.valueOf(start));
        positionalCondition.getPosition().setEnd(String.valueOf(end));
    }

    public static class PositionalFeatureTypeList extends ArrayList<PositionalFeatureType> {
    }
}
