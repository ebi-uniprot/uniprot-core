package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Other tests covered by {@link AbstractMappedReferenceBuilderTest}.
 *
 * <p>Created 08/12/2020
 *
 * @author Edd
 */
class CommunityMappedReferenceBuilderTest {
    @Test
    void canAddAnnotation() {
        CommunityAnnotation annotation = new CommunityAnnotationBuilder().build();
        CommunityMappedReference reference =
                new CommunityMappedReferenceBuilder().communityAnnotation(annotation).build();
        assertThat(reference.getCommunityAnnotation(), is(annotation));
    }

    @Test
    void canCreateViaFrom() {
        String value = "value";
        CommunityAnnotation ann = new CommunityAnnotationBuilder().comment(value).build();
        CommunityMappedReference ref =
                new CommunityMappedReferenceBuilder().communityAnnotation(ann).build();

        CommunityMappedReferenceBuilder builder = CommunityMappedReferenceBuilder.from(ref);

        assertThat(builder.build().getCommunityAnnotation().getComment(), is(value));
    }
}
