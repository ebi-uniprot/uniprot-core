package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.CommunityMappedReference;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class CommunityMappedReferenceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CommunityMappedReference obj = new CommunityMappedReferenceImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        assertThat(
                new CommunityMappedReferenceBuilder()
                        .communityAnnotation(new CommunityAnnotationBuilder().build())
                        .build(),
                is(
                        new CommunityMappedReferenceBuilder()
                                .communityAnnotation(new CommunityAnnotationBuilder().build())
                                .build()));
    }
}
