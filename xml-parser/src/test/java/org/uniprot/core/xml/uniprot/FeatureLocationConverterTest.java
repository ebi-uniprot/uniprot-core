package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.xml.jaxb.uniprot.LocationType;

class FeatureLocationConverterTest {
    private final FeatureLocationConverter converter = new FeatureLocationConverter();

    @Test
    void testSameExact() {
        Range location = createRange(10, 10, PositionModifier.EXACT, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnsure() {
        Range location = createRange(10, 10, PositionModifier.UNSURE, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnknown() {

        Range location =
                createRange(null, null, PositionModifier.UNKNOWN, PositionModifier.UNKNOWN);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactNotSame() {
        Range location = createRange(10, 230, PositionModifier.EXACT, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnSureNotSame() {
        Range location = createRange(10, 230, PositionModifier.EXACT, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testUnSureNotSame() {
        Range location = createRange(10, 230, PositionModifier.UNSURE, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactOutsideNotSame() {
        Range location = createRange(10, 230, PositionModifier.EXACT, PositionModifier.OUTSIDE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testOutsideExactNotSame() {
        Range location = createRange(10, 230, PositionModifier.OUTSIDE, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnknowNotSame() {
        Range location = createRange(10, 230, PositionModifier.EXACT, PositionModifier.UNKNOWN);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testUnknowExactNotSame() {
        Range location = createRange(10, 230, PositionModifier.UNKNOWN, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    private Range createRange(
            Integer start, Integer end, PositionModifier pmStart, PositionModifier pmEnd) {
        return new Range(start, end, pmStart, pmEnd);
    }
}
