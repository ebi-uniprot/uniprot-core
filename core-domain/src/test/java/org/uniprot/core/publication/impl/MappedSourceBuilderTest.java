package org.uniprot.core.publication.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.publication.MappedSource;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

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
    void canAddSourceId() {
        String value = "value";
        MappedSource source = new MappedSourceBuilder().sourceIdsAdd(value).build();
        assertThat(source.getSourceIds(), contains(value));
    }

    @Test
    void canSetSourceIds() {
        Set<String> ids = new HashSet<>();
        ids.add("one");
        ids.add("two");
        MappedSource reference = new MappedSourceBuilder().sourceIdsSet(ids).build();
        assertThat(reference.getSourceIds(), is(ids));
    }

    @Test
    void canCreateViaFrom() {
        String value = "value";
        MappedSource ref = new MappedSourceBuilder().source(value).build();
        MappedSourceBuilder builder = MappedSourceBuilder.from(ref);

        assertThat(builder.build().getSource(), is(value));
    }
}
