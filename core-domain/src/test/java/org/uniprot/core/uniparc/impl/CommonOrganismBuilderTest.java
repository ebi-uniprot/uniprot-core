package org.uniprot.core.uniparc.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.CommonOrganism;

import static org.junit.jupiter.api.Assertions.*;

class CommonOrganismBuilderTest {
    @Test
    void testTopLevel() {
        CommonOrganism common = new CommonOrganismBuilder().topLevel("toLevel1").build();
        assertEquals("toLevel1", common.getTopLevel());
    }

    @Test
    void testCommonTaxon() {
        CommonOrganism common = new CommonOrganismBuilder().commonTaxon("commonTaxon1").build();
        assertEquals("commonTaxon1", common.getCommonTaxon());
    }

    @Test
    void testFrom() {
        CommonOrganism common = new CommonOrganismBuilder().topLevel("toLevel1").commonTaxon("common1").build();
        CommonOrganism common1 = CommonOrganismBuilder.from(common).build();
        assertEquals(common, common1);
        CommonOrganism common3 = CommonOrganismBuilder.from(common).commonTaxon("common3").build();
        assertEquals("toLevel1", common3.getTopLevel());
        assertEquals("common3", common3.getCommonTaxon());
    }
}