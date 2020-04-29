package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

import java.util.ArrayList;
import java.util.List;

public class PositionalFeatureConverterTest extends AbstractConverterTest {

    public static PositionalFeatureType createObject() {
        return objectCreator.createLoremIpsumObject(PositionalFeatureType.class);
    }


    public static List<PositionalFeatureType> createObjects() {
        return objectCreator.createLoremIpsumObject(PositionalFeatureTypeList.class);
    }

    public static class PositionalFeatureTypeList extends ArrayList<PositionalFeatureType> {
    }
}
