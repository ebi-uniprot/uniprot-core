package org.uniprot.core.uniprotkb.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxonomyImplTest {

    @Test
    void defaultConstWillHaveId_minus1() {
        TaxonomyImpl taxonomy = new TaxonomyImpl();
        assertEquals(-1, taxonomy.getTaxonId());
    }
}
