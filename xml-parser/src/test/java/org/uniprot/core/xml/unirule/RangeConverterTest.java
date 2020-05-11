package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.RangeType;

public class RangeConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new RangeConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        Range uniObj = createSkinnyUniObject();
        RangeType xmlObj = (RangeType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(Integer.parseInt(xmlObj.getStart()));
        assertNotNull(Integer.parseInt(xmlObj.getEnd()));
        // convert back to uniObj- test round trip
        Range updatedUniObj = (Range) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private Range createSkinnyUniObject() {
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        return new Range(start, end);
    }

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
