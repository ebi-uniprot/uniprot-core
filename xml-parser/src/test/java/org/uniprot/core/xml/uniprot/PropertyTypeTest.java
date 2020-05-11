package org.uniprot.core.xml.uniprot;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.PropertyType;

import java.util.ArrayList;
import java.util.List;

public class PropertyTypeTest extends AbstractConverterTest {

    public static PropertyType createObject() {
        return objectCreator.createLoremIpsumObject(PropertyType.class);
    }

    public static List<PropertyType> createObjects() {
        return objectCreator.createLoremIpsumObject(PropertyTypeList.class);
    }

    public static class PropertyTypeList extends ArrayList<PropertyType> {}
}
