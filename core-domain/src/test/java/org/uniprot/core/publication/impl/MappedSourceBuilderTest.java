package org.uniprot.core.publication.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class MappedSourceBuilderTest {
    @Test
    void canSetSource() {
        String value = "value";
        MappedSource source = new MappedSourceBuilder().name(value).build();
        assertThat(source.getName(), is(value));
    }

    @Test
    void canSetSourceId() {
        String value = "value";
        MappedSource source = new MappedSourceBuilder().id(value).build();
        assertThat(source.getId(), is(value));
    }

    @Test
    void canCreateViaFrom() {
        String value = "value";
        MappedSource ref = new MappedSourceBuilder().name(value).build();
        MappedSourceBuilder builder = MappedSourceBuilder.from(ref);

        assertThat(builder.build().getName(), is(value));
    }
}
