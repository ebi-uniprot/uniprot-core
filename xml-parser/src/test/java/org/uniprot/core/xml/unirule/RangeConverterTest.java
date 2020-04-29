package org.uniprot.core.xml.unirule;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.RangeType;

public class RangeConverterTest extends AbstractConverterTest {
    public static RangeType createObject() {
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        RangeType rangeType = uniRuleObjectFactory.createRangeType();
        rangeType.setStart(String.valueOf(start));
        rangeType.setEnd(String.valueOf(end));
        return rangeType;
    }

    public static List<RangeType> createObjects() {
        int count = ThreadLocalRandom.current().nextInt(1, 5);
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
