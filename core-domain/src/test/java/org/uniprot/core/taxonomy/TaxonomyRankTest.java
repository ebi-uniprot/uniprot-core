package org.uniprot.core.taxonomy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyRankTest {
  @Test
  void getName_toDisplayName_areSame() {
    assertSame(TaxonomyRank.SUPERORDER.getName(), TaxonomyRank.SUPERORDER.toDisplayName());
  }
}