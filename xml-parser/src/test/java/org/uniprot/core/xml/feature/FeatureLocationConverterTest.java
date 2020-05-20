package org.uniprot.core.xml.feature;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.xml.jaxb.feature.LocationType;

/**
 * @author lgonzales
 * @since 16/05/2020
 */
public class FeatureLocationConverterTest {

    @Test
    void testConvertSimple() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location = new FeatureLocation(10, 20);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testConvertComplete() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location = createFeatureLocation();
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testSameExact() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(null, 10, 10, PositionModifier.EXACT, PositionModifier.EXACT);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnsure() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(
                        "someSeq", 10, 10, PositionModifier.UNSURE, PositionModifier.UNSURE);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testSameUnknown() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(
                        null, null, null, PositionModifier.UNKNOWN, PositionModifier.UNKNOWN);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testExactNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation("id", 10, 230, PositionModifier.EXACT, PositionModifier.EXACT);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnSureNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(
                        "oneId", 10, 230, PositionModifier.EXACT, PositionModifier.UNSURE);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testUnSureNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation("", 10, 230, PositionModifier.UNSURE, PositionModifier.UNSURE);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testExactOutsideNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation("", 10, 230, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testOutsideExactNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(
                        null, 10, 230, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testExactUnknowNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation(
                        null, 10, 230, PositionModifier.EXACT, PositionModifier.UNKNOWN);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    @Test
    void testUnknowExactNotSame() {
        FeatureLocationConverter converter = new FeatureLocationConverter();
        FeatureLocation location =
                new FeatureLocation("", 10, 230, PositionModifier.UNKNOWN, PositionModifier.EXACT);
        LocationType xml = converter.toXml(location);
        FeatureLocation converted = converter.fromXml(xml);
        assertEquals(location, converted);
    }

    public static FeatureLocation createFeatureLocation() {
        return new FeatureLocation(
                "AAAAA", 2, 8, PositionModifier.OUTSIDE, PositionModifier.OUTSIDE);
    }
}
