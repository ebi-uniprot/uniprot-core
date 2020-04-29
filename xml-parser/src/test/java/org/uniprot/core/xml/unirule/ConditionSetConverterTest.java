package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionSetType;

public class ConditionSetConverterTest extends AbstractConverterTest {
    public static ConditionSetType createObject() {
        ConditionSetType conditionSetType =
                objectCreator.createLoremIpsumObject(ConditionSetType.class);
        conditionSetType.getCondition().addAll(ConditionConverterTest.createObjects());
        return conditionSetType;
    }

    public static List<ConditionSetType> createObjects() {
        return objectCreator.createLoremIpsumObject(ConditionSetTypeList.class).stream()
                .filter(obj -> obj.getCondition().addAll(ConditionConverterTest.createObjects()))
                .collect(Collectors.toList());
    }

    public static class ConditionSetTypeList extends ArrayList<ConditionSetType> {}
}
