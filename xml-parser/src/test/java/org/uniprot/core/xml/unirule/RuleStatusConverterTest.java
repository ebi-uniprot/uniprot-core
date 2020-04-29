package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.xml.jaxb.unirule.RuleStatusType;

public class RuleStatusConverterTest {
    public static RuleStatusType createObject() {
        int index = ThreadLocalRandom.current().nextInt(0, RuleStatusType.values().length);
        return RuleStatusType.values()[index];
    }

    public static List<RuleStatusType> createObjects() {
        int count = ThreadLocalRandom.current().nextInt(1, 4);
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
