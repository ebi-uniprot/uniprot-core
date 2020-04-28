package org.uniprot.core.xml.uniprot;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;

public class EvidencedStringTypeConverterTest extends AbstractConverterTest {

    public static EvidencedStringType createObject(int listSize) {
        EvidencedStringType evidencedStringType = uniProtObjectFactory.createEvidencedStringType();
        String value = "value-" + random;
        List<Integer> evidences = createListOfInt(listSize);
        evidencedStringType.setValue(value);
        evidencedStringType.getEvidence().addAll(evidences);
        return evidencedStringType;
    }

    public static EvidencedStringType createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<EvidencedStringType> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
