package org.uniprot.core.xml.uniprot;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;

import java.util.ArrayList;
import java.util.List;

public class EvidencedStringTypeConverterTest extends AbstractConverterTest {

    public static EvidencedStringType createObject() {
        EvidencedStringType evidencedStringType =
                objectCreator.createLoremIpsumObject(EvidencedStringType.class);
        // fill list
        evidencedStringType
                .getEvidence()
                .addAll(objectCreator.createLoremIpsumObject(IntegerList.class));
        return evidencedStringType;
    }

    public static List<EvidencedStringType> createObjects() {
        List<EvidencedStringType> objects =
                objectCreator.createLoremIpsumObject(EvidencedStringTypeList.class);
        objects.forEach(
                obj ->
                        obj.getEvidence()
                                .addAll(objectCreator.createLoremIpsumObject(IntegerList.class)));
        return objects;
    }

    public static class EvidencedStringTypeList extends ArrayList<EvidencedStringType> {}

    public static class IntegerList extends ArrayList<Integer> {}
}
