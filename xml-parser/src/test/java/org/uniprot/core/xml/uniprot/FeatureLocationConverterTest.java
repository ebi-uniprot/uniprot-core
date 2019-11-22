package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.feature.FeatureLocation;
import org.uniprot.core.xml.jaxb.uniprot.LocationType;

class FeatureLocationConverterTest {
    private final FeatureLocationConverter converter = new FeatureLocationConverter();

    @Test
    void testSameExact() {
        FeatureLocation location =
                createFeatureLocation(null, 10, 10, PositionModifier.EXACT, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnsure() {
        FeatureLocation location =
                createFeatureLocation(
                        "someSeq", 10, 10, PositionModifier.UNSURE, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnknown() {

        FeatureLocation location =
                createFeatureLocation(
                        null, null, null, PositionModifier.UNKNOWN, PositionModifier.UNKNOWN);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        "id", 10, 230, PositionModifier.EXACT, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnSureNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        "oneId", 10, 230, PositionModifier.EXACT, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testUnSureNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        "", 10, 230, PositionModifier.UNSURE, PositionModifier.UNSURE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactOutsideNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        "", 10, 230, PositionModifier.EXACT, PositionModifier.OUTSIDE);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testOutsideExactNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        null, 10, 230, PositionModifier.OUTSIDE, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnknowNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        null, 10, 230, PositionModifier.EXACT, PositionModifier.UNKNOWN);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    @Test
    void testUnknowExactNotSame() {
        FeatureLocation location =
                createFeatureLocation(
                        "", 10, 230, PositionModifier.UNKNOWN, PositionModifier.EXACT);

        LocationType xmlObj = converter.toXml(location);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, LocationType.class, "location"));

        Range converted = converter.fromXml(xmlObj);
        assertEquals(location, converted);
    }

    private FeatureLocation createFeatureLocation(
            String sequence,
            Integer start,
            Integer end,
            PositionModifier pmStart,
            PositionModifier pmEnd) {
        return new FeatureLocation(sequence, start, end, pmStart, pmEnd);
    }
}
