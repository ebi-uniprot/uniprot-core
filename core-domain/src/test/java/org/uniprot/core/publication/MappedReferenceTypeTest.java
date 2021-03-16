package org.uniprot.core.publication;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MappedReferenceTypeTest {
    @ParameterizedTest
    @EnumSource(MappedReferenceType.class)
    void checkGetType(MappedReferenceType type) {
        assertThat(MappedReferenceType.getType(type.getIntValue()), is(type));
    }

    @Test
    void invalidIntValueCausesException() {
        assertThrows(IllegalArgumentException.class, () -> MappedReferenceType.getType(10));
    }
}
