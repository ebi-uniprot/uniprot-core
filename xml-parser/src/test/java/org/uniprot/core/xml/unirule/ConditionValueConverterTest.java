package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ConditionValue;

import java.util.ArrayList;
import java.util.List;

public class ConditionValueConverterTest extends AbstractConverterTest {

    public static ConditionValue createObject(){
        return objectCreator.createLoremIpsumObject(ConditionValue.class);
    }

    public static List<ConditionValue> createObjects() {
        return objectCreator.createLoremIpsumObject(ConditionValueList.class);
    }


    public static class ConditionValueList extends ArrayList<ConditionValue> {
    }
}
