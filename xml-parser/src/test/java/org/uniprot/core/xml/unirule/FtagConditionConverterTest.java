package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.FeatureTagConditionValue;
import org.uniprot.core.unirule.impl.FeatureTagConditionValueBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.FtagConditionValue;

import java.util.ArrayList;
import java.util.List;

public class FtagConditionConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new FtagConditionConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        FeatureTagConditionValue uniObj = createSkinnyUniObject();
        FtagConditionValue xmlObj = (FtagConditionValue) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getValue());
        assertNull(xmlObj.getPattern());
        // convert back to uniObj- test round trip
        FeatureTagConditionValue updatedUniObj =
                (FeatureTagConditionValue) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private FeatureTagConditionValue createSkinnyUniObject() {
        FeatureTagConditionValueBuilder builder = new FeatureTagConditionValueBuilder("val");
        return builder.build();
    }

    public static FtagConditionValue createObject() {
        return objectCreator.createLoremIpsumObject(FtagConditionValue.class);
    }

    public static class FtagConditionValueList extends ArrayList<FtagConditionValue> {}

    public static List<FtagConditionValue> createObjects() {
        return objectCreator.createLoremIpsumObject(FtagConditionValueList.class);
    }
}
