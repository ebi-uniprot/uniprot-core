package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.SamTrigger;
import org.uniprot.core.unirule.impl.SamTriggerBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.SamCoiledCoilConditionType;
import org.uniprot.core.xml.jaxb.unirule.SamSignalConditionType;
import org.uniprot.core.xml.jaxb.unirule.SamTransmembraneConditionType;
import org.uniprot.core.xml.jaxb.unirule.SamTriggerType;

public class SamTriggerConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new SamTriggerConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        SamTrigger uniObj = createSkinnyUniObject();
        SamTriggerType xmlObj = (SamTriggerType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNull(xmlObj.getCoiledCoil());
        assertNull(xmlObj.getSignal());
        assertNull(xmlObj.getTransmembrane());
        // convert back to uniObj- test round trip
        SamTrigger updatedUniObj = (SamTrigger) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    public static SamTrigger createSkinnyUniObject() {
        SamTriggerBuilder builder = new SamTriggerBuilder();
        return builder.build();
    }

    public static SamTriggerType createObject() {
        SamTriggerType samTriggerType = uniRuleObjectFactory.createSamTriggerType();
        update(samTriggerType);
        return samTriggerType;
    }

    private static void update(SamTriggerType samTriggerType) {
        SamCoiledCoilConditionType coil = uniRuleObjectFactory.createSamCoiledCoilConditionType();
        coil.setExpectedHits(RangeConverterTest.createObject());
        samTriggerType.setCoiledCoil(coil);
        SamSignalConditionType signal = uniRuleObjectFactory.createSamSignalConditionType();
        signal.setExpectedHits(RangeConverterTest.createObject());
        samTriggerType.setSignal(signal);
        SamTransmembraneConditionType mem =
                uniRuleObjectFactory.createSamTransmembraneConditionType();
        mem.setExpectedHits(RangeConverterTest.createObject());
        samTriggerType.setTransmembrane(mem);
    }

    public static List<SamTriggerType> createObjects() {
        int count = ThreadLocalRandom.current().nextInt(1, 5);
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
