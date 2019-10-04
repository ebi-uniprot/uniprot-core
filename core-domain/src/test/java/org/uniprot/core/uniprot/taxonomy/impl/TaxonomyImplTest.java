package org.uniprot.core.uniprot.taxonomy.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyImplTest {

  @Test
  void defaultConstWillHaveId_minus1(){
    TaxonomyImpl taxonomy = new TaxonomyImpl();
    assertEquals(-1, taxonomy.getTaxonId());
  }

}