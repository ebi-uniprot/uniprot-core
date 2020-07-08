package org.uniprot.core.uniprotkb.interaction.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.interaction.InteractionEntry;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created 12/05/2020
 *
 * @author Edd
 */
class InteractionEntryBuilderTest {

    private InteractionEntryBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new InteractionEntryBuilder();
    }

    @Test
    void canAddInteractionMatrix() {
        InteractionMatrixItem matrix = new InteractionMatrixItemBuilder().build();
        builder.interactionsAdd(matrix);
        assertThat(builder.build().getInteractionMatrix(), is(singletonList(matrix)));
    }

    @Test
    void canSetInteractionMatrix() {
        List<InteractionMatrixItem> matrices =
                singletonList(new InteractionMatrixItemBuilder().build());
        builder.interactionsSet(matrices);
        assertThat(builder.build().getInteractionMatrix(), is(matrices));
    }

    @Test
    void fromWorksAsExpected() {
        InteractionEntry entry =
                builder.interactionsSet(singletonList(new InteractionMatrixItemBuilder().build()))
                        .build();

        InteractionEntry fromEntry = InteractionEntryBuilder.from(entry).build();

        assertThat(fromEntry, is(entry));
    }
}
