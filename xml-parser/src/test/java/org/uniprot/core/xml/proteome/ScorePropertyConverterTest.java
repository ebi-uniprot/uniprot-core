package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.xml.jaxb.proteome.ScorePropertyType;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class ScorePropertyConverterTest {

    ScorePropertyConverter propertyConverter = new ScorePropertyConverter();

    @Test
    void createPropertyStringValue() {
        ScorePropertyType property = propertyConverter.createProperty("name", "value");
        assertNotNull(property);
        assertEquals("name", property.getName());
        assertEquals("value", property.getValue());
    }

    @Test
    void testCreatePropertyIntValue() {
        ScorePropertyType property = propertyConverter.createProperty("name", 10);
        assertNotNull(property);
        assertEquals("name", property.getName());
        assertEquals("10", property.getValue());
    }

    @Test
    void testCreatePropertyDoubleValue() {
        ScorePropertyType property = propertyConverter.createProperty("name", 15.5d);
        assertNotNull(property);
        assertEquals("name", property.getName());
        assertEquals("15.5", property.getValue());
    }
}
