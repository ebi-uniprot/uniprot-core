package org.uniprot.cv.common;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.cv.common.CVSystemProperties.DR_ORD_LOCATION;
import static org.uniprot.cv.common.CVSystemProperties.GAF_ECO_LOCATION;

import org.junit.jupiter.api.Test;

class CVSystemPropertiesTest {
    @Test
    void whenPropertyNotSetReturnNull_getGafEcoLocation() {
        assertNull(CVSystemProperties.getGafEcoLocation());
    }

    @Test
    void returnEmpty_whenPropertySetEmpty_getGafEcoLocation() {
        System.setProperty(GAF_ECO_LOCATION, "");
        assertNotNull(CVSystemProperties.getGafEcoLocation());
        assertTrue(CVSystemProperties.getGafEcoLocation().isEmpty());
        System.clearProperty(GAF_ECO_LOCATION);
    }

    @Test
    void returnValue_whenPropertySet_getGafEcoLocation() {
        String value = "value in Property";
        System.setProperty(GAF_ECO_LOCATION, value);
        assertNotNull(CVSystemProperties.getGafEcoLocation());
        assertFalse(CVSystemProperties.getGafEcoLocation().isEmpty());
        assertEquals(value, CVSystemProperties.getGafEcoLocation());
        System.clearProperty(GAF_ECO_LOCATION);
    }

    @Test
    void whenPropertyNotSetReturnNull_getDrOrdLocation() {
        assertNull(CVSystemProperties.getDrOrdLocation());
    }

    @Test
    void returnEmpty_whenPropertySetEmpty_getDrOrdLocation() {
        System.setProperty(DR_ORD_LOCATION, "");
        assertNotNull(CVSystemProperties.getDrOrdLocation());
        assertTrue(CVSystemProperties.getDrOrdLocation().isEmpty());
        System.clearProperty(DR_ORD_LOCATION);
    }

    @Test
    void returnValue_whenPropertySet_getDrOrdLocation() {
        String value = "value in Property";
        System.setProperty(DR_ORD_LOCATION, value);
        assertNotNull(CVSystemProperties.getDrOrdLocation());
        assertFalse(CVSystemProperties.getDrOrdLocation().isEmpty());
        assertEquals(value, CVSystemProperties.getDrOrdLocation());
        System.clearProperty(DR_ORD_LOCATION);
    }
}
