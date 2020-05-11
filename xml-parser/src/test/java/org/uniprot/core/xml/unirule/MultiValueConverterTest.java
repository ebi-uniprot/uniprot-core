package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;

public class MultiValueConverterTest extends AbstractConverterTest {
    public static MultiValueType createObject() {
        MultiValueType multiValueType = objectCreator.createLoremIpsumObject(MultiValueType.class);
        multiValueType.getValue().addAll(objectCreator.createLoremIpsumObject(StringList.class));
        return multiValueType;
    }

    public static class StringList extends ArrayList<String> {}
}
