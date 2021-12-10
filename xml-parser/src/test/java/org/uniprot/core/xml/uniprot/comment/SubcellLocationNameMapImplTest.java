package org.uniprot.core.xml.uniprot.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubcellLocationNameMapImplTest {

    private static final String LOCATION_NAME = "Plastid";
    private static final String SUBCELL_FILE = "subcell.txt";

    @Test
    void canGetLocationName() {
        SubcellLocationNameMapImpl subcellLocationNameMap = new SubcellLocationNameMapImpl(SUBCELL_FILE);
        assertNotNull(subcellLocationNameMap);
        assertEquals(LOCATION_NAME, subcellLocationNameMap.getLocationName(LOCATION_NAME));
    }

    @Test
    void canGetLocationNameCaseInsensitive() {
        SubcellLocationNameMapImpl subcellLocationNameMap = new SubcellLocationNameMapImpl(SUBCELL_FILE);
        assertNotNull(subcellLocationNameMap);
        assertEquals(LOCATION_NAME, subcellLocationNameMap.getLocationName("plastid"));
    }
}