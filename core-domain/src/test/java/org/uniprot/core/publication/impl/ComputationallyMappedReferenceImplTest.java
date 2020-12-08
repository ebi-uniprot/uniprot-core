package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.ComputationallyMappedReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class ComputationallyMappedReferenceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ComputationallyMappedReference obj = new ComputationallyMappedReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        String value = "value";
        assertThat(
                new ComputationallyMappedReferenceBuilder().annotation(value).build(),
                is(new ComputationallyMappedReferenceBuilder().annotation(value).build()));
    }
}
