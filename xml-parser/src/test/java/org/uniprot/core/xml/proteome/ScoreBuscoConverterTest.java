package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.impl.BuscoReportBuilder;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ScorePropertyType;
import org.uniprot.core.xml.jaxb.proteome.ScoreType;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class ScoreBuscoConverterTest {

    private final ObjectFactory xmlFactory = new ObjectFactory();
    ScoreBuscoConverter converter = new ScoreBuscoConverter();

    @Test
    void testFromXmlNull() {
        BuscoReport result = converter.fromXml(null);
        assertNull(result);
    }

    @Test
    void testFromXmlWrongPropNameThrowError() {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(ScoreBuscoConverter.NAME);
        ScorePropertyConverter propertyConverter = new ScorePropertyConverter();
        ScorePropertyType property = propertyConverter.createProperty("WRONG", "10");
        scoreType.getProperty().add(property);
        assertThrows(IllegalArgumentException.class, () -> converter.fromXml(scoreType));
    }

    @Test
    void testFromXml() {
        ScoreType scoreType = getBuscoScoreType(xmlFactory);

        BuscoReport result = converter.fromXml(scoreType);

        assertNotNull(result);
        assertEquals(10, result.getTotal());
        assertEquals(11, result.getMissing());
        assertEquals(12, result.getFragmented());
        assertEquals(13, result.getComplete());
        assertEquals(14, result.getCompleteSingle());
        assertEquals(15, result.getCompleteDuplicated());
        assertEquals("lineage value", result.getLineageDb());
    }

    @Test
    void testToXml() {
        BuscoReport buscoReport = createBuscoReport();
        ScoreType result = converter.toXml(buscoReport);

        assertNotNull(result);
        assertNotNull(result.getProperty());
        Map<String, String> propMap =
                result.getProperty().stream()
                        .collect(
                                Collectors.toMap(
                                        ScorePropertyType::getName, ScorePropertyType::getValue));

        assertEquals("100", propMap.get(ScoreBuscoConverter.PROPERTY_TOTAL));
        assertEquals("110", propMap.get(ScoreBuscoConverter.PROPERTY_FRAGMENTED));
        assertEquals("120", propMap.get(ScoreBuscoConverter.PROPERTY_MISSING));
        assertEquals("130", propMap.get(ScoreBuscoConverter.PROPERTY_COMPLETED));
        assertEquals("140", propMap.get(ScoreBuscoConverter.PROPERTY_COMPLETED_SINGLE));
        assertEquals("150", propMap.get(ScoreBuscoConverter.PROPERTY_COMPLETED_DUPLICATED));
        assertEquals("lineage value", propMap.get(ScoreBuscoConverter.PROPERTY_LINEAGE));
        assertEquals("240", propMap.get(ScoreBuscoConverter.PROPERTY_SCORE));
    }

    static BuscoReport createBuscoReport() {
        return new BuscoReportBuilder()
                .total(100)
                .fragmented(110)
                .missing(120)
                .complete(130)
                .completeSingle(140)
                .completeDuplicated(150)
                .lineageDb("lineage value")
                .build();
    }

    static ScoreType getBuscoScoreType(ObjectFactory xmlFactory) {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(ScoreBuscoConverter.NAME);
        ScorePropertyConverter propertyConverter = new ScorePropertyConverter();
        ScorePropertyType property =
                propertyConverter.createProperty(ScoreBuscoConverter.PROPERTY_TOTAL, "10");
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreBuscoConverter.PROPERTY_MISSING, "11");
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreBuscoConverter.PROPERTY_FRAGMENTED, "12");
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreBuscoConverter.PROPERTY_COMPLETED, "13");
        scoreType.getProperty().add(property);
        property =
                propertyConverter.createProperty(
                        ScoreBuscoConverter.PROPERTY_COMPLETED_SINGLE, "14");
        scoreType.getProperty().add(property);
        property =
                propertyConverter.createProperty(
                        ScoreBuscoConverter.PROPERTY_COMPLETED_DUPLICATED, "15");
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreBuscoConverter.PROPERTY_SCORE, "16");
        scoreType.getProperty().add(property);
        property =
                propertyConverter.createProperty(
                        ScoreBuscoConverter.PROPERTY_LINEAGE, "lineage value");
        scoreType.getProperty().add(property);
        return scoreType;
    }
}
