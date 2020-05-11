package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Condition;
import org.uniprot.core.unirule.ConditionSet;
import org.uniprot.core.unirule.impl.ConditionBuilderTest;
import org.uniprot.core.unirule.impl.ConditionSetBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionSetType;

import java.util.ArrayList;
import java.util.List;

public class ConditionSetConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new ConditionSetConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        ConditionSet uniObj = createSkinnyUniObject();
        ConditionSetType xmlObj = (ConditionSetType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertFalse(xmlObj.getCondition().isEmpty());
        // convert back to uniObj- test round trip
        ConditionSet updatedUniObj = (ConditionSet) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private ConditionSet createSkinnyUniObject() {
        List<Condition> conditionSets = ConditionBuilderTest.createObjects(2);
        ConditionSetBuilder builder = new ConditionSetBuilder(conditionSets);
        return builder.build();
    }

    public static ConditionSetType createObject() {
        ConditionSetType conditionSetType =
                objectCreator.createLoremIpsumObject(ConditionSetType.class);
        conditionSetType.getCondition().addAll(ConditionConverterTest.createObjects());
        return conditionSetType;
    }

    public static List<ConditionSetType> createObjects() {
        List<ConditionSetType> objects =
                objectCreator.createLoremIpsumObject(ConditionSetTypeList.class);
        objects.forEach(
                object -> object.getCondition().addAll(ConditionConverterTest.createObjects()));
        return objects;
    }

    public static class ConditionSetTypeList extends ArrayList<ConditionSetType> {}
}
