package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.impl.ConditionValueBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;

import java.util.ArrayList;
import java.util.List;

public class ConditionValueConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new ConditionValueConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        org.uniprot.core.unirule.ConditionValue uniObj = createSkinnyUniObject();
        ConditionValue xmlObj = (ConditionValue) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getValue());
        assertNull(xmlObj.getCvId());
        // convert back to uniObj- test round trip
        org.uniprot.core.unirule.ConditionValue updatedUniObj =
                (org.uniprot.core.unirule.ConditionValue) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private org.uniprot.core.unirule.ConditionValue createSkinnyUniObject() {
        ConditionValueBuilder builder = new ConditionValueBuilder("val");
        return builder.build();
    }

    public static ConditionValue createObject() {
        return objectCreator.createLoremIpsumObject(ConditionValue.class);
    }

    public static List<ConditionValue> createObjects() {
        return objectCreator.createLoremIpsumObject(ConditionValueList.class);
    }

    public static class ConditionValueList extends ArrayList<ConditionValue> {}
}
