package org.uniprot.core.xml.unirule;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureSetType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

import java.util.ArrayList;
import java.util.List;

public class PositionalFeatureSetConverterTest extends AbstractConverterTest {

    public static PositionalFeatureSetType createObject(){
        PositionalFeatureSetType positionalFeatureSetType = objectCreator.createLoremIpsumObject(PositionalFeatureSetType.class);
        // fill the list type
        List<PositionalFeatureType> positionalFeatureTypes = PositionalFeatureConverterTest.createObjects();
        positionalFeatureSetType.getPositionalFeature().addAll(positionalFeatureTypes);
        return positionalFeatureSetType;
    }

    public static List<PositionalFeatureSetType> createObjects() {
        return objectCreator.createLoremIpsumObject(PositionalFeatureSetTypeList.class);
    }

    public static class PositionalFeatureSetTypeList extends ArrayList<PositionalFeatureSetType> {
    }
}
