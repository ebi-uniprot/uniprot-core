package org.uniprot.core.uniprotkb.interaction.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrix;

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
        InteractionMatrix matrix = new InteractionMatrixBuilder().build();
        builder.interactionsAdd(matrix);
        assertThat(builder.build().getInteractionMatrix(), is(singletonList(matrix)));
    }

    @Test
    void canSetInteractionMatrix() {
        List<InteractionMatrix> matrices = singletonList(new InteractionMatrixBuilder().build());
        builder.interactionsSet(matrices);
        assertThat(builder.build().getInteractionMatrix(), is(matrices));
    }
}
