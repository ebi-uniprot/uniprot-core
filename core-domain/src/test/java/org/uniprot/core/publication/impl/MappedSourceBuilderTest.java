package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created 08/12/2020
 *
 * @author Edd
 */
class MappedSourceBuilderTest {
    @Test
    void canSetSource() {
        String value = "value";
        MappedSource source = new MappedSourceBuilder().source(value).build();
        assertThat(source.getSource(), is(value));
    }

    @Test
    void canSetSourceId() {
        String value = "value";
        MappedSource source = new MappedSourceBuilder().sourceId(value).build();
        assertThat(source.getSourceId(), is(value));
    }

    @Test
    void canCreateViaFrom() {
        String value = "value";
        MappedSource ref = new MappedSourceBuilder().source(value).build();
        MappedSourceBuilder builder = MappedSourceBuilder.from(ref);

        assertThat(builder.build().getSource(), is(value));
    }
}
