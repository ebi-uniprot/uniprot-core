package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionType;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;

public class ConditionConverterTest extends AbstractConverterTest {
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
