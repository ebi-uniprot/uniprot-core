package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 20-Nov-2020
 */
class SubcellLocationNameMapTest {

    @Test
    void middleNameStartWithCapital() {
        String name = "Golgi apparatus, golgi stack lumen";
        String validName = SubcellLocationNameMap.instance.getLocationName(name);
        assertEquals("Golgi apparatus, Golgi stack lumen", validName);
    }

    @Test
    void normalMiddleName() {
        String name = "Cell projection, attachment organelle membrane";
        String validName = SubcellLocationNameMap.instance.getLocationName(name);
        assertEquals(name, validName);
    }
}
