package org.uniprot.core.cv.subcell;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class SubcellLocationCategoryTest {
  @ParameterizedTest
  @EnumSource(SubcellLocationCategory.class)
  void categoryAndDisplayAreSame(SubcellLocationCategory category) {
    assertSame(category.getCategory(), category.toDisplayName());
  }

  @Test
  void location_is_CellularComponent() {
    assertEquals("Cellular component", SubcellLocationCategory.LOCATION.getCategory());
  }
}