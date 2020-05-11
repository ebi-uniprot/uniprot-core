package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.impl.PositionalFeatureBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalConditionType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

public class PositionalFeatureConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new PositionalFeatureConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        PositionalFeature uniObj = createSkinnyUniObject();
        PositionalFeatureType xmlObj = (PositionalFeatureType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getPositionalCondition());
        assertNotNull(xmlObj.getPositionalCondition().getPosition());
        assertNotNull(xmlObj.getPositionalAnnotation());
        assertFalse(xmlObj.isInGroup());
        // convert back to uniObj- test round trip
        PositionalFeature updatedUniObj = (PositionalFeature) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    public static PositionalFeature createSkinnyUniObject() {
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range position = new Range(start, end);
        PositionalFeatureBuilder builder = new PositionalFeatureBuilder(position);
        return builder.build();
    }

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
