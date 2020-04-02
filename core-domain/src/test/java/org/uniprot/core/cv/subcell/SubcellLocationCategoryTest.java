package org.uniprot.core.cv.subcell;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class SubcellLocationCategoryTest {
    @ParameterizedTest
    @EnumSource(SubcellLocationCategory.class)
    void categoryAndDisplayAreSame(SubcellLocationCategory category) {
        assertSame(category.getName(), category.getDisplayName());
    }

    @Test
    void location_is_CellularComponent() {
        assertEquals("Cellular component", SubcellLocationCategory.LOCATION.getName());
    }
}
