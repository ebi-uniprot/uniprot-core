package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedPublications;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MappedPublications obj = new MappedPublicationsImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        assertThat(
                new MappedPublicationsBuilder().build(),
                is(new MappedPublicationsBuilder().build()));
    }
}
