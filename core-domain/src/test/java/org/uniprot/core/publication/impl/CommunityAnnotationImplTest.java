package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.CommunityAnnotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class CommunityAnnotationImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CommunityAnnotation obj = new CommunityAnnotationImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        assertThat(new CommunityAnnotationBuilder().build(), is(new CommunityAnnotationBuilder().build()));
    }
}
