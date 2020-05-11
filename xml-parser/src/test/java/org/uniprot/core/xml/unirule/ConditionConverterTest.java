package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.impl.ConditionBuilder;
import org.uniprot.core.unirule.impl.ConditionSetBuilderTest;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionType;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;

import java.util.ArrayList;
import java.util.List;

public class ConditionConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new ConditionConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        Condition uniObj = createSkinnyUniObject();
        ConditionType xmlObj = (ConditionType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getType());
        assertNull(xmlObj.getRange());
        assertNull(xmlObj.getTag());
        assertTrue(xmlObj.getValue().isEmpty());
        // convert back to uniObj- test round trip
        Condition updatedUniObj = (Condition) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private Condition createSkinnyUniObject() {
        List<ConditionSet> conditionSets = ConditionSetBuilderTest.createObjects(2);
        ConditionBuilder builder = new ConditionBuilder("type");
        return builder.build();
    }

    public static ConditionType createObject() {
        ConditionType conditionType = objectCreator.createLoremIpsumObject(ConditionType.class);
        // fill list type
        List<ConditionValue> values = ConditionValueConverterTest.createObjects();
        conditionType.getValue().addAll(values);
        return conditionType;
    }

    public static List<ConditionType> createObjects() {
        return objectCreator.createLoremIpsumObject(ConditionTypeList.class);
    }

    public static class ConditionTypeList extends ArrayList<ConditionType> {}
}
