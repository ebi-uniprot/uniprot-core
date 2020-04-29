package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.FtagConditionValue;

public class FtagConditionConverterTest extends AbstractConverterTest {
    public static FtagConditionValue createObject() {
        return objectCreator.createLoremIpsumObject(FtagConditionValue.class);
    }

    public static class FtagConditionValueList extends ArrayList<FtagConditionValue> {}

    public static List<FtagConditionValue> createObjects() {
        return objectCreator.createLoremIpsumObject(FtagConditionValueList.class);
    }
}
