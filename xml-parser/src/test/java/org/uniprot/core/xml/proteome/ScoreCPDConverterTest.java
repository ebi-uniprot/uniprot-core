package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;
import org.uniprot.core.proteome.impl.CPDReportBuilder;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.ScorePropertyType;
import org.uniprot.core.xml.jaxb.proteome.ScoreType;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class ScoreCPDConverterTest {

    private final ObjectFactory xmlFactory = new ObjectFactory();
    ScoreCPDConverter converter = new ScoreCPDConverter();

    @Test
    void testFromXmlNull() {
        CPDReport result = converter.fromXml(null);
        assertNull(result);
    }

    @Test
    void testFromXmlWrongPropNameThrowError() {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(ScoreCPDConverter.NAME);
        ScorePropertyConverter propertyConverter = new ScorePropertyConverter();
        ScorePropertyType property = propertyConverter.createProperty("WRONG", "10");
        scoreType.getProperty().add(property);
        assertThrows(IllegalArgumentException.class, () -> converter.fromXml(scoreType));
    }

    @Test
    void testFromXml() {
        ScoreType scoreType = getCPDScoreType(xmlFactory);

        CPDReport result = converter.fromXml(scoreType);

        assertNotNull(result);
        assertEquals(10, result.getAverageCdss());
        assertEquals(11, result.getConfidence());
        assertEquals(12, result.getProteomeCount());
        assertEquals(CPDStatus.OUTLIER, result.getStatus());
        assertEquals(14, result.getStdCdss());
    }

    @Test
    void testToXml() {
        CPDReport buscoReport = createCPDReport();
        ScoreType result = converter.toXml(buscoReport);

        assertNotNull(result);
        assertNotNull(result.getProperty());
        Map<String, String> propMap =
                result.getProperty().stream()
                        .collect(
                                Collectors.toMap(
                                        ScorePropertyType::getName, ScorePropertyType::getValue));

        assertEquals("100", propMap.get(ScoreCPDConverter.PROPERTY_AVERAGE_CDS));
        assertEquals("110", propMap.get(ScoreCPDConverter.PROPERTY_CONFIDENCE));
        assertEquals("120", propMap.get(ScoreCPDConverter.PROPERTY_PROTEOME_COUNT));
        assertEquals(
                CPDStatus.CLOSE_TO_STANDARD.getDisplayName(),
                propMap.get(ScoreCPDConverter.PROPERTY_STATUS));
        assertEquals("12.3", propMap.get(ScoreCPDConverter.PROPERTY_STD_CDSS));
    }

    static ScoreType getCPDScoreType(ObjectFactory xmlFactory) {
        ScoreType scoreType = xmlFactory.createScoreType();
        scoreType.setName(ScoreCPDConverter.NAME);
        ScorePropertyConverter propertyConverter = new ScorePropertyConverter();
        ScorePropertyType property =
                propertyConverter.createProperty(ScoreCPDConverter.PROPERTY_AVERAGE_CDS, "10");
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreCPDConverter.PROPERTY_CONFIDENCE, "11");
        scoreType.getProperty().add(property);
        property =
                propertyConverter.createProperty(ScoreCPDConverter.PROPERTY_PROTEOME_COUNT, "12");
        scoreType.getProperty().add(property);
        property =
                propertyConverter.createProperty(
                        ScoreCPDConverter.PROPERTY_STATUS, CPDStatus.OUTLIER.getDisplayName());
        scoreType.getProperty().add(property);
        property = propertyConverter.createProperty(ScoreCPDConverter.PROPERTY_STD_CDSS, "14");
        scoreType.getProperty().add(property);
        return scoreType;
    }

    static CPDReport createCPDReport() {
        return new CPDReportBuilder()
                .averageCdss(100)
                .confidence(110)
                .proteomeCount(120)
                .status(CPDStatus.CLOSE_TO_STANDARD)
                .stdCdss(12.3d)
                .build();
    }
}
