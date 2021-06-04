package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * @author jluo
 * @date: 20-Nov-2020
 */
class SubcellLocationNameMapTest {
    @Test
    void middleNameStartWithCapital() {
    	SubcellLocationNameMap subcellLocationNameMap =new SubcellLocationNameMapImpl();
        String name = "Golgi apparatus, golgi stack lumen";
        String validName = subcellLocationNameMap.getLocationName(name);
        assertEquals("Golgi apparatus, Golgi stack lumen", validName);
    }

    @Test
    void normalMiddleName() {
    	Map<String, String> subcellularLocationMap =Map.of("cell projection, attachment organelle membrane", 
    			"Cell projection, Attachment organelle membrane");
    	SubcellLocationNameMap subcellLocationNameMap =new SubcellLocationNameMapImpl(subcellularLocationMap);
    	
        String name = "Cell projection, attachment organelle membrane";
        String validName = subcellLocationNameMap.getLocationName(name);
        assertEquals("Cell projection, Attachment organelle membrane", validName);
    }
}
