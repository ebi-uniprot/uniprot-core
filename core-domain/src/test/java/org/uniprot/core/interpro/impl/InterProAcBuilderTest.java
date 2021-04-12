package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.InterProAc;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
*/

class InterProAcBuilderTest {


    @Test
    void testInterProAcBuilderValid() {
        String id = "IPR011992";
        InterProAc entryId = new InterProAcBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertTrue(entryId.isValidId());
    }
	
    @Test
    void testInterProAcBuilderInvalid() {
        String id = "IPR01192";
        InterProAc entryId = new InterProAcBuilder(id).build();
        assertEquals(id, entryId.getValue());
        assertFalse(entryId.isValidId());
    }
	
    @Test
    void assignedId_nulls_areEqual() {
    	InterProAc ur1 = new InterProAcBuilder(null).build();
    	InterProAc ur2 = new InterProAcBuilder(null).build();
        assertEquals(ur1, ur2);
    }
    @Test
    void testInterProAcBuilderFrom() {
        String id = "IPR011992";
        InterProAc entryId = new InterProAcBuilder(id).build();
        InterProAc entryId2= InterProAcBuilder.from(entryId).build();
        assertEquals(entryId, entryId2);
    }
}

