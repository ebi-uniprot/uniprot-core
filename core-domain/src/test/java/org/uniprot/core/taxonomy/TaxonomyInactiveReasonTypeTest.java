package org.uniprot.core.taxonomy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyInactiveReasonTypeTest {
  @Test
  void name_toDisplayName_areSame() {
    assertSame(TaxonomyInactiveReasonType.MERGED.name(), TaxonomyInactiveReasonType.MERGED.toDisplayName());
  }
}