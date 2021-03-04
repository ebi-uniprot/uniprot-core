package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.ComputationallyMappedReference;

/**
 * Other tests covered by {@link AbstractMappedReferenceBuilderTest}.
 *
 * <p>Created 08/12/2020
 *
 * @author Edd
 */
class ComputationallyMappedReferenceBuilderTest {
    @Test
    void canAddAnnotation() {
        String annotation = "value";
        ComputationallyMappedReference reference =
                new ComputationallyMappedReferenceBuilder().annotation(annotation).build();
        assertThat(reference.getAnnotation(), is(annotation));
    }

    @Test
    void canCreateViaFrom() {
        String value = "value";
        ComputationallyMappedReference ref =
                new ComputationallyMappedReferenceBuilder().annotation(value).build();
        ComputationallyMappedReferenceBuilder builder =
                ComputationallyMappedReferenceBuilder.from(ref);

        assertThat(builder.build().getAnnotation(), is(value));
    }
}
