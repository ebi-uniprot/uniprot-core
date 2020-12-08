package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class MappedSourceImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MappedSource obj = new MappedSourceImpl();
        assertNotNull(obj);
    }

    @Test
    void twoObjectsAreTheSame() {
        assertThat(new MappedSourceBuilder().build(), is(new MappedSourceBuilder().build()));
    }
}
